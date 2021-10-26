import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;

/**
 * 
 * @author Quentin JAYET
 * @version 1.0
 *
 */
public class Demineur extends JFrame implements Runnable{
	
	private Champ champ ;
	private IHMDemin ihm;
	private Socket sock;
	private boolean online;
	private DataOutputStream out;
	private DataInputStream in;
	private int player;
	private Thread t;
	private int score;
	private final JFrame popup;
	
	
	
	
	/**
	 * constructor
	 */
	Demineur() {
		popup = new JFrame();
		champ = new Champ(Level.EASY);
		this.champ.placeMines();
		ihm = new IHMDemin(this);
		online = false;
		player = 1;
		score = 0;
		
		
		setTitle("Demineur");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(ihm);
		JMenuBar menuBar = new JMenuBar();
		JMenu menuPartie = new JMenu("Partie");
		JMenuItem mQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
		mQuitter.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_Q,ActionEvent.ALT_MASK));
		mQuitter.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		          System.exit(0);
		      }});
		
		JMenuItem mNewPartie = new JMenuItem("Nouvelle Partie");
		mNewPartie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				newGame();
			}
		});
		menuPartie.add(mNewPartie); 
		menuPartie.add(mQuitter);
		JMenu menuDifficulte = new JMenu("Difficulté");
		JMenuItem mEasy = new JMenuItem("EASY");
		mEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				newGame(Level.EASY);
			}
		});
		
		JMenuItem mMedium = new JMenuItem("MEDIUM");
		mMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				newGame(Level.MEDIUM);
			}
		});
		JMenuItem mHard = new JMenuItem("HARD");
		mHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				newGame(Level.HARD);
			}
		});
		
        

		
		menuDifficulte.add(mEasy);
		menuDifficulte.add(mMedium);
		menuDifficulte.add(mHard);
		JMenu menuNetwork = new JMenu("Network");
		JMenuItem mConnect = new JMenuItem("Connect to host");
		mConnect.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String ip = JOptionPane.showInputDialog(popup,
                        "What the IP?", null);
                System.out.println(ip);
                connectToServer(ip);
            }
		});
		
		JMenuItem mQuit = new JMenuItem("Quit online");
		mQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				quitServer();
			}
		});
		
		menuNetwork.add(mConnect);
		menuNetwork.add(mQuit);
		
		
		menuBar.add(menuPartie) ;
		menuBar.add(menuDifficulte);
		menuBar.add(menuNetwork);
		
		this.setJMenuBar(menuBar) ;
		pack();
		setVisible(true);
		champ.affText2();
	}
	
	/**
	 * main function
	 * @param args not used
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Demineur();
		
		
	}
	/**
	 * get the champ
	 * @return
	 */
	public Champ getChamp() {
		return this.champ;
	}
	
	/**
	 * create a new game with the previous level
	 */
	public void newGame() {
		newGame(champ.getLevel());
	}
	
	/**
	 * create a new game with the level
	 * @param level
	 */
	public void newGame(Level level) {
		champ.setLevel(level);
		champ.placeMines();
		ihm.refreshLevelDim();
		ihm.refresh();
		score = 0;
		setContentPane(ihm);
		pack();
		setVisible(true);
	}
	/**
	 * connect to the server
	 */
	public void connectToServer(String ip) {
		System.out.println("try to connect to serv");
		try {
			sock = new Socket(ip,10000);
			out =new DataOutputStream(sock.getOutputStream());
			in = new DataInputStream(sock.getInputStream());
			online = true;
			Thread t = new Thread(this);
			t.start();
			setPlayerFromServer();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Quit the server
	 */
	public void quitServer() {
		System.out.println("try to disconnect to serv");
		try {
			out.writeInt(1);
//			in.close();
//			out.close();
//			sock.close();
			online = false;
			player = 1;
			t.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the boolean that represent the offline/online status
	 * @return
	 */
	public boolean isOnline() {
		return online;
	}
	
	/**
	 * send to the server that the player clicked on a case
	 * @param x
	 * @param y
	 */
	public void clickOnCase(int x, int y) {
		try {
			out.writeInt(1);
			out.writeInt(x);
			out.writeInt(y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void clickOnCaseOffline(int x, int y) {
		if(champ.isMine(x, y)) {
			//end of game
			 JOptionPane.showMessageDialog(popup,
                     "You lost with " + score + " points","End of game", 1);
			 newGame();
		} else {
			score ++;
			ArrayList<Integer> scores = new ArrayList<Integer>();
			ArrayList<Integer> players = new ArrayList<Integer>();
			scores.add(score);
			players.add(1);
			ihm.updateScore(scores, players);
			System.out.println(isWin());
		}
	}
	/**
	 * get the value of the case x y offline
	 * @param x
	 * @param y
	 * @return -1(mines) or number of mines around
	 */
	
	public int getValueOffline(int x,int y) {
		if(champ.isMine(x,y)) {
			return(-1);
			//implements end of game
		} else {
			return(champ.calculMines(x, y));
		}
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(online) {
				int cmd = in.readInt();
				
				System.out.println("cmd "+cmd);
				switch (cmd) {
					case 0: { //
						
					}
					case 1: { //A player click on a case
						int x = in.readInt();
						int y = in.readInt();
						int player = in.readInt();
						int value = in.readInt();
						System.out.println("x " + x +" y "+ y +" player " + player + " value " +value);
						this.ihm.playerClickedOnCase(x,y,player,value);
						break;
						
					}
					case 2: { //get player number
						player = in.readInt();
						System.out.println("player " +player);
						break;
					}
					case 3: { //reset
						int level = in.readInt();
						newGame(Level.values()[level]);
						break;
					}
					case 4: {//get scores
						int nbPlayer = in.readInt();
						System.out.print("nb player = " + nbPlayer);
						ArrayList<Integer> scores = new ArrayList<Integer>();
						ArrayList<Integer> players = new ArrayList<Integer>();
						for(int i=0; i<nbPlayer;i++) {
							players.add(i+1);
							int s = in.readInt();
							System.out.print(", score = " + s);
							scores.add(s);
						}
						ihm.updateScore(scores, players);
						break;
					}
					case 5: { //get message
						String message = in.readUTF();
						int player = in.readInt();
						System.out.println(message);
						ihm.displayMessage(message,player);
						break;
					}
					
					case 6:{ //end of game bomb discover
						int x = in.readInt();
						int y = in.readInt();
						int s = in.readInt();
						ihm.playerClickedOnCase(x, y, y, player);
						JOptionPane.showMessageDialog(popup,
				                "You lost with : " + s +" point" ,"End of game", 1);
						break;
						
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * get the number of the player
	 * @return the number of the player
	 */
	public int getPlayer() {
		return player;
	}
	
	/**
	 * ask to the server the number of the player
	 */
	public void setPlayerFromServer() {
		try {
			out.writeInt(2);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	/**
	 * get the ihm
	 * @return ihm
	 */
	public IHMDemin getIHM() {
		return ihm;
	}
	/**
	 * get score of the player offline
	 * @return score offline
	 */
	public int getScore() {
		return score;
	}
	/**
	 * set score of the player
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * send the m string to the server with the number of the player
	 * @param m
	 */
	public void sendMessage(String m) {
		try {
			out.writeInt(3);
			out.writeUTF(m);
			out.writeInt(this.player);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWin() {
		int n = 0;
		for(int x=0;x<champ.getDimX();x++) {
			for(int y=0;y<champ.getDimY();y++) {
				if(champ.isMine(x, y) && ihm.isFlag(x, y)) {
					n++;
				}
			}
		}
		System.out.println(n);
		if(n == champ.getNbMines()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void win() {
		JOptionPane.showMessageDialog(popup,
                "You win","End of game", 1);
		 newGame();
	}
	
}
