import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Compteur extends JPanel implements ActionListener{
	private static final int DELAY = 1000;
	private int compteur;
	private Timer timer;
	private JLabel label;
	public Compteur() {
		super();
		label = new JLabel();
		add(label);
		timer = new Timer(DELAY,this);
		compteur = 0;
		timer.start();
		
	}
	
	
	
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
