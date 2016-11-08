/**
 * The cell class is the parent to mine and number 
 * cells.
 * 
 * @author Brandon Otto
 *
 */
public class Cell 
{
	//Boolean to determine if a cell is marked as a mine.
	protected boolean marked;
	
	//Boolean to determine if a cell is revealed.
	protected boolean revealed;
	
	public Cell()
	{
		marked = false;
		revealed = false;
	}
	
	public void markMine()
	{
		marked = true;
	}
	
	public void reveal()
	{
		revealed = true;
	}
	
	public boolean guessedSafe()
	{
		return true;
	}
	
	public boolean isRevealed()
	{
		return revealed;
	}
}
