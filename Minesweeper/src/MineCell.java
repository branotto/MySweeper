
public class MineCell extends Cell
{

	public MineCell()
	{
		marked = false;
		revealed = false;
	}
	
	public boolean guessedSafe()
	{
		return false;
	}
	
	public boolean markedCorrectly()
	{
		return marked;
	}
	
	
}
