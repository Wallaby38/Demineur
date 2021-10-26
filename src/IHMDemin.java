import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author jayet
 *
 */
public class IHMDemin extends JPanel implements ActionListener{
	private Demineur demineur;
	private JButton newGameButton;
	private Case [][] caseLand;
	private JPanel labelMines;
	private JPanel labelScore;
	private JTextArea chat;
	private JPanel labelChat;
	private JTextField message;
	
	private final static Color[] COLORS = {Color.blue,Color.red,Color.pink,Color.cyan,Color.darkGray,Color.gray,Color.magenta,Color.magenta};
	
	private ArrayList<String> messages;
	
	
	//private Compteur compteur;
	
	/**
	 * Constructor
	 * @param demineur
	 */
	IHMDemin(Demineur demineur) {
		
		//compteur = new Compteur();
		
	
		
		
		
		this.demineur = demineur;
		labelMines = new JPanel();
		newGameButton = new JButton("Nouvelle partie");
		this.setLayout(new BorderLayout());
		add(newGameButton,BorderLayout.SOUTH);
		
		labelScore = new JPanel();
		labelScore.setLayout(new BoxLayout(labelScore,1));
		JLabel  jlabel = new JLabel();
		jlabel.setText("Score");
		labelScore.add(jlabel);
		JLabel  jlabel2 = new JLabel();
		jlabel2.setText("Player " +demineur.getPlayer() + " : " + demineur.getScore());
		jlabel2.setForeground(COLORS[0]);
		labelScore.add(jlabel2);
		
		add(labelScore,BorderLayout.EAST);
		
		labelChat = new JPanel(new GridBagLayout());
		labelChat.setSize(new Dimension(400,400));
		GridBagConstraints c = new GridBagConstraints();
		
		
		messages = new ArrayList<String>();
		messages.add("Welcome to the french DEMINEUR!");
		
		
		chat = new JTextArea("Welcome to the french DEMINEUR!\n",5,1);
		
		//chat.setText();
		JScrollPane chatPane = new JScrollPane(chat);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 1.0;
		labelChat.add(chatPane,c);
		
		message = new JTextField("Message");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		labelChat.add(message,c);
		
		
		
		
		JButton buttonChat = new JButton("Send");
		buttonChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String m = message.getText();
				if(demineur.isOnline()) {
					demineur.sendMessage(m);
				} else {
					if(!m.equals("")) {
						messages.add(m);
						chat.append(m+"\n");
					}
				}
			}});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		labelChat.add(buttonChat,c);
		
		
		
		
		add(labelChat,BorderLayout.WEST);
		
		
		//add(new JLabel("Difficulté"),BorderLayout.NORTH);
		//add(compteur,BorderLayout.WEST);
		
		
		newGameButton.addActionListener(this);
		refreshLevelDim();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(!demineur.isOnline()) {
			demineur.newGame();
		}
		
	}
	/**
	 * refresh all the labelMines
	 */
	synchronized public void refresh() {
		
		labelMines.removeAll();
		labelMines.setLayout(new GridLayout(demineur.getChamp().getDimX(),demineur.getChamp().getDimY()));
		
		for(int i = 0; i<demineur.getChamp().getDimX();i++) {
			
			for(int j = 0; j< demineur.getChamp().getDimY();j++) {
				labelMines.add(caseLand[i][j]);
			}
		}
		this.add(labelMines,BorderLayout.CENTER);
	}
	
	/**
	 * get demineur
	 * @return
	 */
	public Demineur getDemineur() {
		return demineur;
	}
	/**
	 * refresh the level display of the level dim
	 */
	synchronized public void refreshLevelDim() {
		caseLand = new Case [demineur.getChamp().getDimX()][demineur.getChamp().getDimY()];
		
		
		for(int i = 0; i<demineur.getChamp().getDimX();i++) {
					
			for(int j = 0; j< demineur.getChamp().getDimY();j++) {
				caseLand[i][j] = new Case(i,j,this);
			}
		
		}
		refresh();
	}
	
	/**
	 * set all the player and the value of the case (x,y) and repaint it
	 * @param x
	 * @param y
	 * @param player
	 * @param value
	 */
	synchronized public void playerClickedOnCase(int x,int y, int player,int value) {
		caseLand[x][y].setPlayer(player);
		caseLand[x][y].setValue(value);
		caseLand[x][y].setDiscover(true);
		caseLand[x][y].repaint();
//		this.getDemineur().setContentPane(this.getDemineur().getIHM());
//		this.getDemineur().pack();
//		this.getDemineur().setVisible(true);
//		refresh();
	}
	
	/**
	 * update all the score with color
	 * @param score
	 * @param player
	 */
	public void updateScore(ArrayList<Integer> score,ArrayList<Integer> player) {
		if(player.size()+1 == labelScore.getComponentCount()) {
			for(int i = 0;i<score.size();i++) {
				JLabel  label = (JLabel) labelScore.getComponent(i+1);
				if(demineur.getPlayer() == player.get(i)) {
					label.setForeground(COLORS[0]);
					label.setText("Player " +player.get(i) + " : " + score.get(i) + " (*)");
				} else {
					label.setForeground(COLORS[player.get(i)]);
					label.setText("Player " +player.get(i) + " : " + score.get(i));
				}
				
			}
		} else {
		
			labelScore.removeAll();
			labelScore.add(new JLabel("Score"));
			
			for(int i = 0;i<score.size();i++) {
				JLabel label = new JLabel();
				if(demineur.getPlayer() == player.get(i)) {
					label.setForeground(COLORS[0]);
					label.setText("Player " +player.get(i) + " : " + score.get(i) + " (*)");
				} else {
					label.setForeground(COLORS[player.get(i)]);
					label.setText("Player " +player.get(i) + " : " + score.get(i));
				}
				labelScore.add(label);
			}
		}
	}
	/**
	 * get all the colors possible
	 * @return
	 */
	public Color[] getCOLORS() {
		return COLORS;
	}
	
	/**
	 * Display a message in the chat with the number of the player
	 * @param message
	 * @param player
	 */
	public void displayMessage(String message,int player) {
		messages.add(message);
		chat.append("player " + player + " : " + message +"\n");
	}
	
	/**
	 * get the number of flag set
	 * @return
	 */
	public int getNbFlag() {
		int n = 0;
		for(int x=0;x<caseLand.length;x++) {
			for(int y=0;y<caseLand[0].length;y++) {
				if(caseLand[x][y].isFlag()) {
					n++;
				}
			}
		}
		return n;
	}
	/**
	 * get if the case (x,y) is a flag
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isFlag(int x, int y) {
		return caseLand[x][y].isFlag();
	}
}
	
	


