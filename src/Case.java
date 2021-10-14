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
import javax.swing.SwingUtilities;



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
	private static final String MINES = "./img/mines.png";
	private int discover;
	private int x;
	private int y;
	private IHMDemin ihm;
	private boolean flag;
	private int value;
	
	
	Case(int x, int y,IHMDemin ihm) {
		setPreferredSize(new Dimension(WIDTH_CASE,HEIGHT_CASE));
		addMouseListener(this);
		setBackground(Color.white);
		this.x = x;
		this.y = y;
		this.ihm = ihm;
		discover = 0;
		flag = false;
	}
	@Override
		public void paint(Graphics g) {
			super.paint(g);
			//our design
			g.drawRect(0, 0, getWidth(), getHeight());
			if(discover == 0) {
				if(flag) {
					setBackground(Color.red);
				} else {
					setBackground(Color.gray);
				}
			} else if(value == -1) {
				g.drawString("X",getWidth()/2, getHeight()/2);
				//Toolkit toolkit = getToolkit();
				//g.drawImage(toolkit.getImage(MINES), 0,0,this); 
				setBackground(Color.white);
			} else {
				g.drawString(Integer.toString(value),getWidth()/2, getHeight()/2);
				setBackground(Color.white);
			}
		}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//e.getButton();
//		if(discover == 0) {
//			if(e.getButton() == 1) {
//				if(this.ihm.getDemineur().isOnline()) {
//					value = ihm.getDemineur().clickOnCase(x, y);
//				} else {
//					discover = 1;
//					value = ihm.getDemineur().getValueOffline(x, y);
//				}
//			} else if(e.getButton() == 2) {
//				flag = !flag;
//			}
//			repaint();
//		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(discover == 0) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				if(this.ihm.getDemineur().isOnline()) {
					discover = 1;
					value = ihm.getDemineur().clickOnCase(x, y);
					System.out.println(value);
				} else {
					discover = 1;
					value = ihm.getDemineur().getValueOffline(x, y);
				}
			} else if(SwingUtilities.isRightMouseButton(e)) {
				flag = !flag;
			}
			repaint();
		}
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
