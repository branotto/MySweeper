/**
 * Driver for MineSweeper
 * @author Brandon Otto
 *
 */

public class MineSweeper 
{
	public static void main (String[ ] args)
	{
		MineField model = new MineField();
		MineSweeperGUI view = new MineSweeperGUI(model);
		model.setObserver(view);
		
		GameController controller = new GameController(model, view);
		
		
		view.setVisible(true);
	}
}
