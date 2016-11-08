import java.util.Random;

/**
 * The MineField class is the main model for the minesweeper
 * game.
 * 
 * @author Brandon Otto
 *
 */

public class MineField 
{
	//Reference to the view.
	private MineSweeperGUI gameView;
	
	//2-d Array for the mine field
	private Cell[][] grid;
	
	//# of mines in the game.
	private int totalMines;
	
	//# of rows constant
	private final int ROWS = 9;

	//# of columns Constant
	private final int COLUMNS = 9;
	
	//Constant to determine the mine chance. 
	//Lower #s equals less mines and easy game.
	private final int DIFFICULTY = 10;
	
	/**
	 * Constructor
	 */
	
	public MineField()
	{
		//Initialize the grid.
		grid = new Cell[ROWS][COLUMNS];
		
	}
	
	/**
	 * The newGame method creates a new mine field.
	 * 
	 * @param rows The number of rows.
	 * @param columns The number of columns.
	 * 
	 */
	
	public void newGame(int row, int column)
	{
		//Reset the count of mines for a new game.
		totalMines = 0;
		
		//Set up a random number generator.
		Random randomGenerator = new Random();
		
		//An integer to hold the random number.
		int mineChance;
		
		//Loop through the rows and columns to create 
		//the grid.
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < column; j++)
			{
				//A mine chance should be between 0 and 99
				mineChance = randomGenerator.nextInt(99);
				
				//If the random number is less than or equal
				//to difficulty, create a mine. 
				if(mineChance <= DIFFICULTY)
				{
					//Create a mine cell and increment the number 
					//of mines. 
					grid[i][j] = new MineCell();
					totalMines++;
					
				} else
				{
					//Create a safe cell.
					grid[i][j] = new NumberCell();
					
				}
			}
		}
		
		gameView.notifyView();
				
	}
	
	/**
	 * The countAdjacentMines method counts all the mines 
	 * adjacent to a number cell.
	 * 
	 * @param row The number of rows in the mine field.
	 * @param column The number of columns in the mine field.
	 * 
	 */
	
	public void countAdjacentMines(int row, int column)
	{
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < column; j++)
			{
				if(grid[i][j] instanceof NumberCell)
				{
					//Test the cell above and right.
					if(i - 1 > -1 && j - 1 > -1 && grid[i-1][j-1] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell above.
					if(i - 1 > -1 && j  > -1 && grid[i-1][j] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell above and left.
					if(i - 1 > -1 && j + 1 < column && grid[i-1][j+1] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell on the same row right.
					if(j - 1 > -1 && grid[i][j-1] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell on the same row left.
					if(j + 1 < column && grid[i][j+1] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell below and right.
					if(i + 1 < row && j - 1 > -1 && grid[i+1][j-1] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell below
					if(i + 1 < row && j  > -1 && grid[i+1][j] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
					
					//Test the cell below and left.
					if(i + 1 < row && j + 1 < column && grid[i+1][j+1] instanceof MineCell)
					{
						((NumberCell) grid[i][j]).addNeighborMine();
					}
				}
			}
		}
	}
	
	/**
	 * The setObserver method sets the view to be used.
	 * 
	 * @param view Reference to the view
	 * 
	 */
	
	public void setObserver(MineSweeperGUI view)
	{
		gameView = view;
	}
	
	/**
	 * The minesRemaining method counts the number
	 * of correctly marked mines.
	 * @return The number of mines left or incorrectly marked.
	 * 
	 */
	
	public int minesRemaining()
	{
		//All mines are unmarked to begin.
		int unMarkedMines = totalMines;
		
		//Check all of the cells.
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLUMNS; j++)
			{
				//If it is a mine, see if it is marked correctly.
				if(grid[i][j] instanceof MineCell)
				{
					if(((MineCell) grid[i][j]).markedCorrectly())
					{
						//If so 1 mine is marked correctly, decrement unmarked mines.
						unMarkedMines--;
					}
				}
			}
		}
		
		return unMarkedMines;
	}
	
	/**
	 * The guessSafe method guesses a cell is safe.
	 * 
	 * @param row Row number of the cell.
	 * @param col column number of the cell.
	 */
	
	public void guessSafe(int row, int col)
	{
		//If it is a number cell reveal its neighbors.
		if(grid[row][col].guessedSafe())
		{
			revealNeighbors(row, col);
			gameView.notifyView();
			
		} else 
		{
			//It is a mine. Game Over.
			gameOver();
			gameView.reDraw();
		}
	}
	
	public void markMine(int row, int col)
	{
		grid[row][col].markMine();
		gameView.notifyView(row, col);
	}
	
	/**
	 * The revealNeighbors method uses recursion to reveal all 
	 * nearby cells that are blank or have a number.
	 * @param rowB The row of the cell to begin with.
	 * @param colB The Column of the cell to begin with.
	 */
	
	public void revealNeighbors(int rowB, int colB)
	{
		if(rowB < ROWS && rowB > -1 && colB < COLUMNS && colB > -1)
		{
			if(!grid[rowB][colB].isRevealed())
			{
				if(grid[rowB][colB] instanceof NumberCell)
				{
					grid[rowB][colB].reveal();
					if(((NumberCell)(grid[rowB][colB])).getNeighborMines() == 0)
					{
						revealNeighbors(rowB-1, colB-1);
						revealNeighbors(rowB-1, colB);
						revealNeighbors(rowB-1, colB+1);
						revealNeighbors(rowB, colB-1);
						revealNeighbors(rowB, colB+1);
						revealNeighbors(rowB+1, colB-1);
						revealNeighbors(rowB+1, colB);
						revealNeighbors(rowB+1, colB+1);
					}
				}
				
			}
			
		}

	
	}
	
	public void gameOver()
	{
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLUMNS; j++)
			{
				grid[i][j].reveal();
			}
		}
	}
	
	public boolean[][] gridStatus(int rows, int columns)
	{
		boolean[][] status = new boolean[rows][columns];
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				if(grid[i][j].isRevealed())
				{
					status[i][j] = true;
				} else
				{
					status[i][j] = false;
				}
			}
			
		}
		return status;
	}
	
	public int[][] gridMarkers(int rows, int columns)
	{
		int[][] markers = new int[rows][columns];
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				if(grid[i][j] instanceof MineCell)
				{
					markers[i][j] = -1;
				} else
				{
					markers[i][j] = ((NumberCell) grid[i][j]).getNeighborMines();
				}
			}
			
		}
		return markers;
	}
	
	public int neighborMines(int row, int col)
	{
		return ((NumberCell) grid[row][col]).getNeighborMines();
	}
	
	/**
	 * the printGrid method prints the location of mines and
	 * number cells. Debug Utility method.
	 * @param rows The number of rows in the field.
	 * @param columns The number of columns in the field
	 * 
	 */
	
	public void printGrid(int rows, int columns)
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				if(grid[i][j] instanceof MineCell)
				{
					System.out.print("M\t");
				} else
				{
					System.out.print(((NumberCell) grid[i][j]).getNeighborMines() + "\t");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// To test the class
	public static void main (String[] args)
	{
		MineField field = new MineField();
		field.printGrid(9,9);
	}

}
