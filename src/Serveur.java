import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.sun.nio.sctp.SendFailedNotification;

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
	private ArrayList<Integer> score;
	
	
	
	Serveur() {
		System.out.println("Démarrage du serveur") ;
		numero = 1;
		champ = new Champ();
		champ.placeMines();
		entree = new ArrayList<DataInputStream>();
		sortie = new ArrayList<DataOutputStream>();
		socket = new ArrayList<Socket>();
		score = new ArrayList<Integer>();
		
		
		Thread back = new Thread(this,"back");
		back.start();
		
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
				score.add(0);
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
		if(Thread.currentThread().getName() == "back") {
			back();
		} else {
			boolean run = true;
			Thread t = Thread.currentThread();
			System.out.println("Entrée run");
			if(Integer.parseInt(t.getName())==0) {
				
			} else {
				DataInputStream dataInput = entree.get(Integer.parseInt(t.getName())-1);
				DataOutputStream dataOutput = sortie.get(Integer.parseInt(t.getName())-1);
				try {
					dataOutput.writeInt(2);	
					dataOutput.writeInt(Integer.parseInt(t.getName()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(run) {
					System.out.println(t.getName());
					
					try {
						int cmd = dataInput.readInt();
						
						System.out.println("cmd "+cmd);
						switch (cmd) {
						case 0: { //Quit
							System.out.println("Deconnection du joueur");
							run = false;
							break;
						}
						case 1: { //Click on a case take 2 int and return if the value of the case (-1 if mines, 0, 1,2,3 etc)
							System.out.println("case 1");
							int x = dataInput.readInt();
							System.out.println("x " +x);
							int y = dataInput.readInt();
							System.out.println("y " +y);
	//						if(champ.isMine(x,y)) {
	//							System.out.println("end of game");
	//							sendValueAndPlayer(x,y,Integer.parseInt(t.getName()),-1);
	//							champ.setJoueur(x, y, Integer.parseInt(t.getName())-1);
	//							//implements end of game
	//						} else 
							if(champ.isClicked(x,y) == 0){
								if(champ.isMine(x, y)) {
									sendValueAndPlayer(x,y,Integer.parseInt(t.getName()),-1);
									champ.setJoueur(x, y, Integer.parseInt(t.getName())-1);
								} else {
									sendValueAndPlayer(x,y,Integer.parseInt(t.getName()),champ.calculMines(x, y));
									champ.setJoueur(x, y, Integer.parseInt(t.getName())-1);
								}
							
							}
							break;
						}
						case 2: { //get numero of player
							System.out.println("case 2");
							//dataOutput.writeInt(2);	
							//dataOutput.writeInt(Integer.parseInt(t.getName()));			
							break;			
						}
						case 3: {
							break;
							
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
		
	}
	
	synchronized public void sendValueAndPlayer(int x,int y, int player, int value) {
		for(int counter = 0; counter < sortie.size(); counter++) {
			try {
				sortie.get(counter).writeInt(1);
				sortie.get(counter).writeInt(x);
				sortie.get(counter).writeInt(y);
				sortie.get(counter).writeInt(player);
				sortie.get(counter).writeInt(value);
				System.out.println("x " + x +" y "+ y +" player " + player + " value " +value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	public void back() {
//		while(true) {
//		try {
//			wait(50);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//			for(int counter = 0; counter < sortie.size(); counter++) {
//				try {
//					sortie.get(counter).writeInt(1);
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		
	}

	
	public void resetPartie() {
		for(int counter = 0; counter < sortie.size(); counter++) {
			try {
				sortie.get(counter).write(3);
				sortie.get(counter).write(champ.getLevel().ordinal());
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
