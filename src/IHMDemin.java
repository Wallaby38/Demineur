import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
	
	private final static Color[] COLORS = {Color.blue,Color.red,Color.pink,Color.cyan,Color.darkGray,Color.gray,Color.magenta,Color.magenta};
	
	
	//private Compteur compteur;
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
		
		add(labelScore,BorderLayout.WEST);
		
		//add(new JLabel("Difficulté"),BorderLayout.NORTH);
		//add(compteur,BorderLayout.WEST);
		
		
		newGameButton.addActionListener(this);
		refreshLevelDim();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		demineur.newGame();
		
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
	
	public Demineur getDemineur() {
		return demineur;
	}
	
	synchronized public void refreshLevelDim() {
		caseLand = new Case [demineur.getChamp().getDimX()][demineur.getChamp().getDimY()];
		
		
		for(int i = 0; i<demineur.getChamp().getDimX();i++) {
					
			for(int j = 0; j< demineur.getChamp().getDimY();j++) {
				caseLand[i][j] = new Case(i,j,this);
			}
		
		}
		refresh();
	}
	
	
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
	
	
	public void updateScore(ArrayList<Integer> score,ArrayList<Integer> player) {
		if(player.size()+1 == labelScore.getComponentCount()) {
			for(int i = 0;i<score.size();i++) {
				JLabel  label = (JLabel) labelScore.getComponent(i+1);
				label.setText("Player " +player.get(i) + " : " + score.get(i));
				if(demineur.getPlayer() == player.get(i)) {
					label.setForeground(COLORS[0]);
				} else {
					label.setForeground(COLORS[player.get(i)]);
				}
				
			}
		} else {
		
			labelScore.removeAll();
			labelScore.add(new JLabel("Score"));
			for(int i = 0;i<score.size();i++) {
				labelScore.add(new JLabel("Player " +player.get(i) + " : " + Integer.toString(score.get(i))));
			}
		}
	}
	
	public Color[] getCOLORS() {
		return COLORS;
	}
}

