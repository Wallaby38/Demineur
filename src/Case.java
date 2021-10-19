import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
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
	private static final int WIDTH_CASE = 20;
	private static final int HEIGHT_CASE = 20;
	private int player;
	private int x;
	private int y;
	private IHMDemin ihm;
	private boolean flag;
	private int value;
	private boolean discover;
	private static final String[] PATH = {"./img/0.png","./img/1.png","./img/2.png","./img/3.png","./img/4.png","./img/5.png","./img/6.png","./img/7.png","./img/flag.png","./img/mines.png","./img/non_clicked.png"};
	private static final String[] PATH2 = {"./img/0.png","./img/1_Plan.png","./img/2_Plan de travail 1.png","./img/3_Plan de travail 1.png","./img/4_Plan de travail 1.png","./img/5_Plan de travail 1.png","./img/6_Plan de travail 1.png","./img/7_Plan de travail 1.png","./img/flag.png","./img/mines.png","./img/non_clicked.png"};
	
	
	Case(int x, int y,IHMDemin ihm) {
		setPreferredSize(new Dimension(WIDTH_CASE,HEIGHT_CASE));
		addMouseListener(this);
		setBackground(Color.white);
		this.x = x;
		this.y = y;
		this.ihm = ihm;
		player = 1;
		flag = false;
		discover = false;
	}
	@Override
		public void paint(Graphics g) {
			super.paint(g);
			//our design
			if(!discover) {
				if(flag) {
					Toolkit toolkit = getToolkit();
					g.drawImage(toolkit.getImage(PATH[8]), 0,0,this); 
					//setBackground(Color.red);
				} else {
					Toolkit toolkit = getToolkit();
					g.drawImage(toolkit.getImage(PATH[10]), 0,0,this); 
					setBackground(Color.gray);
				}
			} else {
				if(this.ihm.getDemineur().isOnline()) {
					
					if(player == this.ihm.getDemineur().getPlayer()) {
						if(value == -1) {
							//g.drawString("X",getWidth()/2, getHeight()/2);
							Toolkit toolkit = getToolkit();
							Image image = toolkit.getImage(PATH2[9]).getScaledInstance(WIDTH_CASE,HEIGHT_CASE,Image.SCALE_SMOOTH);
							ImageIcon img = new ImageIcon(image);
							image =img.getImage();
							g.drawImage(image, 0,0,this); 
						} else {
							Toolkit toolkit = getToolkit();
							Image image = toolkit.getImage(PATH2[value]).getScaledInstance(WIDTH_CASE,HEIGHT_CASE,Image.SCALE_SMOOTH);
							ImageIcon img = new ImageIcon(image);
							image =img.getImage();
							g.drawImage(image, 0,0,this); 
							
							//g.drawString(Integer.toString(value),getWidth()/2, getHeight()/2);
							setBackground(ihm.getCOLORS()[0]);
						}
					} else {
						if(value == -1) {
							//g.drawString("X",getWidth()/2, getHeight()/2);
							Toolkit toolkit = getToolkit();
							Image image = toolkit.getImage(PATH2[9]).getScaledInstance(WIDTH_CASE,HEIGHT_CASE,Image.SCALE_SMOOTH);
							ImageIcon img = new ImageIcon(image);
							image =img.getImage();
							g.drawImage(image, 0,0,this); 
							//setBackground(Color.white);
						} else {
							Toolkit toolkit = getToolkit();
							Image image = toolkit.getImage(PATH2[value]).getScaledInstance(WIDTH_CASE,HEIGHT_CASE,Image.SCALE_SMOOTH);
							ImageIcon img = new ImageIcon(image);
							image =img.getImage();
							g.drawImage(image, 0,0,this); 
							
							//g.drawString(Integer.toString(value),getWidth()/2, getHeight()/2);
							setBackground(ihm.getCOLORS()[player]);
						}
					}
				} else {
					if(value == -1) {
						//g.drawString("X",getWidth()/2, getHeight()/2);
						Toolkit toolkit = getToolkit();
						Image image = toolkit.getImage(PATH2[9]).getScaledInstance(WIDTH_CASE,HEIGHT_CASE,Image.SCALE_SMOOTH);
						ImageIcon img = new ImageIcon(image);
						image =img.getImage();
						g.drawImage(image, 0,0,this); 
						//setBackground(Color.white);
					} else {
						Toolkit toolkit = getToolkit();
						Image image = toolkit.getImage(PATH2[value]).getScaledInstance(WIDTH_CASE,HEIGHT_CASE,Image.SCALE_SMOOTH);
						ImageIcon img = new ImageIcon(image);
						image =img.getImage();
						g.drawImage(image, 0,0,this); 
						
						//g.drawString(Integer.toString(value),getWidth()/2, getHeight()/2);
						setBackground(Color.white);
					}
				}
			}
			g.drawRect(0, 0, getWidth(), getHeight());
			
			
			
		
			
			
			
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
		if(!discover) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				if(this.ihm.getDemineur().isOnline()) {
					player = 1;
					ihm.getDemineur().clickOnCase(x, y);
					discover = true;
				} else {
					player = 1;
					value = ihm.getDemineur().getValueOffline(x, y);
					discover = true;
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
	
	public void setValue(int value) {
		this.value = value;
		repaint();
	}
	
	public void setPlayer(int player) {
		this.player = player;
		repaint();
	}
	
	public void setDiscover(boolean d) {
		discover = d;
		repaint();
	}
	

	
}
