import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author jayet
 *
 */
public class Serveur implements Runnable{
	private static int numero;
	private ArrayList<Socket> socket;
	private ServerSocket gestSock;
	private ArrayList<DataInputStream> entree;
	private ArrayList<DataOutputStream> sortie;
	private Champ champ;
	
	
	Serveur() {
		System.out.println("Démarrage du serveur") ;
		numero = 0;
		champ = new Champ(Level.MEDIUM);
		champ.placeMines();
		entree = new ArrayList<DataInputStream>();
		sortie = new ArrayList<DataOutputStream>();
		socket = new ArrayList<Socket>();
		
		try {
			gestSock=new ServerSocket(10000);
		} catch(IOException e) {
			e.printStackTrace();
		}
		while(true) {
			try {
				System.out.println("Attente d'un joueur");
				Socket s = gestSock.accept();
				socket.add(s);
				entree.add(new DataInputStream(s.getInputStream()));
				sortie.add(new DataOutputStream(s.getOutputStream()));
				// lecture d’une donnée
				
				Thread t = new Thread(this,Integer.toString(numero));
				addPlayer();
				t.start();
//				String nomJoueur = entree.readUTF() ;
//				System.out.println(nomJoueur+"connected");
//				// envoi d ’une donnée : 0 par exemple
//				sortie.writeInt(numero);
//				// un peu de ménage
//				sortie.close() ;
//				entree.close() ;
				
				
			} catch (IOException e) {e.printStackTrace( );}
			}
	}
	
		public void close() {
			try {
				
				//socket.close();
				gestSock.close() ;
			} catch(IOException e) {e.printStackTrace( );}
		}
		
		public static synchronized void addPlayer() {
			numero ++;
		}
		
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Serveur();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean run = true;
		Thread t = Thread.currentThread();
		System.out.println("Entrée run");
		
		while(run) {
			System.out.println(t.getName());
			DataInputStream dataInput = entree.get(Integer.parseInt(t.getName()));
			DataOutputStream dataOutput = sortie.get(Integer.parseInt(t.getName()));
			try {
				int cmd = dataInput.readInt() ;
				switch (cmd) {
				case 0: { //Quit					
					dataInput.close();
					dataOutput.close();
					run = false;
				}
				case 1: { //Click on a case take 2 int and return if the value of the case (-1 if mines, 0, 1,2,3 etc)
					int x = dataInput.readInt();
					int y = dataInput.readInt();
					if(champ.isMine(x,y)) {
						dataOutput.write(-1);
						//implements end of game
					} else {
						dataOutput.write(champ.calculMines(x, y));
					}
					champ.setJoueur(x, y, Integer.parseInt(t.getName()));
				}
				case 2: {
									
									
				}
				case 3: {
					
					
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + cmd);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
