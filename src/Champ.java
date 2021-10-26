import java.util.Random;
/**
 * @version 1.0
 * @author Quentin JAYET
 *
 */

public class Champ {
	private final static int [] DIM = {10,20,30};
	private final static int [] MINES = {20,40,60};
	private Level level;
	
	private Random alea = new Random();
	private boolean [][] champ;
	private int [][] champJoueur;
	
	/**
	 * Constructor
	 */
	public Champ() {
		this(DIM[0],DIM[0]);
		level = Level.EASY;
	}
	/**
	 * Constructor with level
	 * @param level
	 */
	public Champ(Level level) {
		this(DIM[level.ordinal()],DIM[level.ordinal()]);
		setLevel(level);
		
	}
	/**
	 * Constructor with dimension
	 * @param x
	 * @param y
	 */
	public Champ(int x, int y) {
		this.level = Level.EASY;
		champ = new boolean [x][y];
		champJoueur = new int [x][y];
		
	}
	/**
	 * empty the champ
	 */
	public void empty() {
		for(int i = 0;i<champ.length;i++) {
			for(int j = 0; j< champ[0].length;j++) {
				champ[i][j]=false;
				champJoueur[i][j] = 0;
			}
		}
	}
	/**
	 * Place des mines
	 */
	public void placeMines() {
		empty();
		for(int i =0; i<MINES[level.ordinal()];i++) {
			int x = alea.nextInt(champ.length);
			int y = alea.nextInt(champ[0].length);
			if(champ[x][y]) {
				i--;
			} else {
				champ[x][y] = true;
			}
		}
	}
	
	/**
	 * Display the champ in format txt in the terminal (only mines)
	 */
	public void affText() {
		for(int i = 0;i<champ.length;i++) {
			for(int j = 0; j< champ[0].length;j++) {
				if(champ[i][j]) {
					System.out.print("X");
				} else {
					System.out.print("0");
				}
			}
			System.out.print("\n");
		}
	}
	/**
	 * Sum the number of mines around the case (x,y)
	 * @param x
	 * @param y
	 * @return  number of mines around the case (x,y)
	 */
	public int calculMines(int x,int y) {
		int n = 0;
		for(int i=-1; i<2; i++) {
			for(int j=-1;j<2;j++) {
				try {
					if(this.champ[x+i][y+j]) {
						n++;
					}
				} catch(Exception e) {
					//System.out.println(e.getMessage());
					continue;
				}
			}
		}
		return(n);
	}
	/**
	 * Display the champ in format txt in the terminal (with number of mines around)
	 */
	public void affText2() {
		for(int i = 0;i<champ.length;i++) {
			for(int j = 0; j< champ[0].length;j++) {
				if(champ[i][j]) {
					System.out.print("X");
				} else {
					System.out.print(this.calculMines(i,j));
				}
			}
			System.out.print("\n");
		}
	}
	/**
	 * get the value of the width
	 * @return
	 */
	public int getDimY() {
		return this.champ[0].length;
	}
	
	/**
	 * get the value of the length
	 * @return
	 */
	public int getDimX() {
		return this.champ.length;
	}
	/**
	 * get the Value of cell (mines : X, other : number of mines around)
	 * @param i
	 * @param j
	 * @return
	 */
	public String getValueToPrint(int i, int j) {
		if(champ[i][j]) {
			return "X";
		} else {
			return Integer.toString(calculMines(i, j));
		}
	}
	
	/**
	 * get number of mines around or -1 if mines
	 * @param i
	 * @param j
	 * @return
	 */
	public int getValuesOfCase(int i, int j) {
		if(champ[i][j]) {
			return -1;
		} else {
			return calculMines(i, j);
		}
	}
	
	/**
	 * Find if the cell is a mine
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isMine(int x, int y) {
		return(champ[x][y]);
	}
	
	/**
	 * set level and resize the champ
	 * @param level
	 */
	public void setLevel(Level level) {
		champ = new boolean [DIM[level.ordinal()]] [DIM[level.ordinal()]];
		champJoueur = new int [DIM[level.ordinal()]] [DIM[level.ordinal()]];
		this.level = level;
	}
	/**
	 * set player that clicked the case
	 * @param x
	 * @param y
	 * @param j
	 */
	public void setJoueur(int x, int y,int j) {
		champJoueur[x][y] = j;
	}
	
	/**
	 * return if the case (x,y) is clicked
	 * @param x
	 * @param y
	 * @return
	 */
	public int isClicked(int x,int y) {
		return champJoueur[x][y];
	}
	/**
	 * get the level
	 * @return
	 */
	public Level getLevel() {
		return level;
	}
	/**
	 * get number of mines in the game
	 * @return
	 */
	public int getNbMines() {
		return MINES[level.ordinal()];
	}
	
}
