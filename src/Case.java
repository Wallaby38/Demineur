import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * 
 */

/**
 * @author jayet
 *
 */
public class Case extends JPanel implements MouseListener{
	private static final int WIDTH_CASE = 50;
	private static final int HEIGHT_CASE = 50;
	private boolean discover = false;
	private int x;
	private int y;
	private IHMDemin ihm;
	Case(int x, int y,IHMDemin ihm) {
		setPreferredSize(new Dimension(WIDTH_CASE,HEIGHT_CASE));
		addMouseListener(this);
		setBackground(Color.white);
		this.x = x;
		this.y = y;
		this.ihm = ihm;
	}
	@Override
		public void paint(Graphics g) {
			super.paint(g);
			//our design
			g.drawRect(0, 0, getWidth(), getHeight());
			if(!discover) {
				setBackground(Color.gray);
			} else if(ihm.getDemineur().getChamp().isMine(x, y)) {
				//g.drawString("X",getWidth()/2, getHeight()/2);
				Toolkit toolkit = getToolkit();
				g.drawImage(toolkit.getImage("/mines.png").getScaledInstance(getWidth(), getHeight(),  Image.SCALE_SMOOTH), 0,0,this); 
				//setBackground(Color.white);
			} else {
				g.drawString(Integer.toString(ihm.getDemineur().getChamp().calculMines(x, y)),getWidth()/2, getHeight()/2);
				setBackground(Color.white);
			}
		}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		discover = true;
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
}
