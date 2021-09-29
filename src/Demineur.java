import javax.swing.JFrame;

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
	Demineur() {
		champ = new Champ(Level.EASY);
		this.champ.placeMines();
		ihm = new IHMDemin(this);
		setTitle("hello");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(ihm);
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
}
