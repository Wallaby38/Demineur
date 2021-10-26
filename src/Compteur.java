import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * Implementation of a compteur
 * @author Quentin
 *
 */
public class Compteur extends JPanel implements ActionListener{
	private static final int DELAY = 1000;
	private int compteur;
	private Timer timer;
	private JLabel label;
	/**
	 * Constructor
	 */
	public Compteur() {
		super();
		label = new JLabel();
		add(label);
		timer = new Timer(DELAY,this);
		compteur = 0;
		timer.start();
		
	}
	
	
	/**
	 * get the compteur
	 * @return
	 */
	public int getCompteur() {
		return compteur;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		compteur++;
		
		label.setText(Integer.toString(compteur));
	
	}
	
	
	

	
}
