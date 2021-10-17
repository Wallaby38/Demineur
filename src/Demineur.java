import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * 
 * @author Quentin JAYET
 * @version 1.0
 *
 */
public class Demineur extends JFrame implements Runnable{
	/**
	 * 
	 * @param args not used
	 */
	private Champ champ ;
	private IHMDemin ihm;
	private Socket sock;
	private boolean online;
	private DataOutputStream out;
	private DataInputStream in;
	private int player;
	private Thread t;
	private int score;
	private int nbPlayer;
	
	Demineur() {
		champ = new Champ(Level.EASY);
		this.champ.placeMines();
		ihm = new IHMDemin(this);
		online = false;
		player = 0;
		score = 0;
		nbPlayer = 1;
		
		
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
		mConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				connectToServer();
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
		//champ.affText2();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Demineur();
		
		
	}
	
	public Champ getChamp() {
		return this.champ;
	}
	
	public void newGame() {
		newGame(champ.getLevel());
	}
	public void newGame(Level level) {
		champ.setLevel(level);
		champ.placeMines();
		ihm.refreshLevelDim();
		ihm.refresh();
		setContentPane(ihm);
		pack();
		setVisible(true);
	}
	
	public void connectToServer() {
		System.out.println("try to connect to serv");
		try {
			sock = new Socket("localhost",10000);
			out =new DataOutputStream(sock.getOutputStream());
			in = new DataInputStream(sock.getInputStream());
			online = true;
			Thread t = new Thread(this);
			t.start();
			//setPlayerFromServer();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void quitServer() {
		System.out.println("try to disconnect to serv");
		try {
			out.writeInt(1);
//			in.close();
//			out.close();
//			sock.close();
			online = false;
			player = 0;
			t.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOnline() {
		return online;
	}
	
	
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
	
	public int getValueOffline(int x,int y) {
		if(champ.isMine(x,y)) {
			return(-1);
			//implements end of game
		} else {
			return(champ.calculMines(x, y));
		}
	}


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
					case 2: { //
						player = in.readInt();				
						System.out.println("player " +player);
						break;
					}
					case 3: { //reset
						int level = in.readInt();
						newGame(Level.values()[level]);
						break;
					}
					case 4: {
						
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void setPlayerFromServer() {
		try {
			out.writeInt(2);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public IHMDemin getIHM() {
		return ihm;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
