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
	private static final String MINES = "./img/mines.png";
	private boolean discover;
	private int x;
	private int y;
	private IHMDemin ihm;
	private boolean flag;
	
	
	Case(int x, int y,IHMDemin ihm) {
		setPreferredSize(new Dimension(WIDTH_CASE,HEIGHT_CASE));
		addMouseListener(this);
		setBackground(Color.white);
		this.x = x;
		this.y = y;
		this.ihm = ihm;
		discover = false;
		flag = false;
	}
	@Override
		public void paint(Graphics g) {
			super.paint(g);
			//our design
			g.drawRect(0, 0, getWidth(), getHeight());
			if(!discover) {
				if(flag) {
					setBackground(Color.red);
				} else {
					setBackground(Color.gray);
				}
			} else if(ihm.getDemineur().getChamp().isMine(x, y)) {
				g.drawString("X",getWidth()/2, getHeight()/2);
				//Toolkit toolkit = getToolkit();
				//g.drawImage(toolkit.getImage(MINES), 0,0,this); 
				setBackground(Color.white);
			} else {
				g.drawString(Integer.toString(ihm.getDemineur().getChamp().calculMines(x, y)),getWidth()/2, getHeight()/2);
				setBackground(Color.white);
			}
		}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//e.getButton();
		if(e.getButton() == 1) {
			discover = true;
		} else if(e.getButton() == 2) {
			flag = !flag;
		}
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
