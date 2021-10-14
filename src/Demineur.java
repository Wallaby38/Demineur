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
public class Demineur extends JFrame{
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
	
	
	Demineur() {
		champ = new Champ(Level.EASY);
		this.champ.placeMines();
		ihm = new IHMDemin(this);
		online = false;
		setTitle("hello");
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
		champ.placeMines();
		ihm.refresh();
		setContentPane(ihm);
		pack();
		setVisible(true);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void quitServer() {
		System.out.println("try to disconnect to serv");
		try {
			in.close();
			out.close();
			sock.close();
			online = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
