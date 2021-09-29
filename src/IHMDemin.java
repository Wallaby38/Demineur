import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
	JMenuBar menuBar;
	IHMDemin(Demineur demineur) {
		
		this.demineur = demineur;
		caseLand = new Case [demineur.getChamp().getDimX()][demineur.getChamp().getDimX()];
		for(int i = 0; i<demineur.getChamp().getDimX();i++) {
					
			for(int j = 0; j< demineur.getChamp().getDimY();j++) {
				caseLand[i][j] = new Case(i,j,this);
			}
		
		}
		refresh();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		demineur.newGame();
		
	}
	/**
	 * refresh all the component
	 */
	public void refresh() {
		this.removeAll();
		JPanel labelMines = new JPanel();
		this.setLayout(new BorderLayout());
		labelMines.setLayout(new GridLayout(demineur.getChamp().getDimX(),demineur.getY()));
		
		for(int i = 0; i<demineur.getChamp().getDimX();i++) {
			
			for(int j = 0; j< demineur.getChamp().getDimY();j++) {
				labelMines.add(caseLand[i][j]);
			}
		}
		this.add(labelMines,BorderLayout.CENTER);
		newGameButton = new JButton("Nouvelle partie");
		add(newGameButton,BorderLayout.SOUTH);
		add(new JLabel("Score"),BorderLayout.EAST);
		add(new JLabel("Difficulté"),BorderLayout.NORTH);
		newGameButton.addActionListener(this);
	}
	
	public Demineur getDemineur() {
		return demineur;
	}
	
	
}
