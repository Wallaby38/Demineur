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
		numero = 1;
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
		DataInputStream dataInput = entree.get(Integer.parseInt(t.getName())-1);
		DataOutputStream dataOutput = sortie.get(Integer.parseInt(t.getName())-1);
		while(run) {
			System.out.println(t.getName());
			
			try {
				int cmd = dataInput.readInt();
				
				System.out.println("cmd "+cmd);
				switch (cmd) {
				case 0: { //Quit
					System.out.println("Deconnection du joueur");
					run = false;
				}
				case 1: { //Click on a case take 2 int and return if the value of the case (-1 if mines, 0, 1,2,3 etc)
					System.out.println("case 1");
					int x = dataInput.readInt();
					System.out.println("x " +x);
					int y = dataInput.readInt();
					System.out.println("y " +y);
					if(champ.isMine(x,y)) {
						System.out.println("Test");
						dataOutput.writeInt(-1);
						//implements end of game
					} else if(champ.isClicked(x,y) == 0){
						dataOutput.writeInt(champ.calculMines(x, y));
						champ.setJoueur(x, y, Integer.parseInt(t.getName())-1);
						//notifyall
					} else {
						dataOutput.writeInt(-2);
						//do nothing
					}
					
				}
				case 2: {
									
									
				}
				case 3: {
					
					
				}
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				run = false;
				e.printStackTrace();
			}
			if(run == false) {
				System.out.print("coucou");
				try {
					dataInput.close();
					dataOutput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
