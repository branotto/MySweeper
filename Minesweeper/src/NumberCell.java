
/**
 * Sub class to a cell for a non-mine cell.
 * 
 * @author Brandon Otto
 *
 */

public class NumberCell extends Cell
{
	//Count of adjacent mines.
	private int adjacentMines;
	
	/**
	 * Constructor
	 */
	
	public NumberCell()
	{
		//Initialize starting values for adjacent mines
		//marked and revealed.
		adjacentMines = 0;
		marked = false;
		revealed = false;
		
	}
	
	/**
	 * The addNeighborMine method increments the 
	 * count of adjacent mines each time a neighboring
	 * cell is a mine cell.
	 */
	
	public void addNeighborMine()
	{
		adjacentMines++;
	}
	
	/**
	 * Returns the number of adjacent mines.
	 * @return The number of adjacent cells that are 
	 * mines.
	 */
	
	public int getNeighborMines()
	{
		return adjacentMines;
	}
	
	
}
