import java.awt.event.*;

/**
 * Controller for Mine Sweeper.
 * 
 * @author Brandon Otto
 *
 */

public class GameController 
{
	private MineField field;
	private MineSweeperGUI gui;

	/**
	 * Constructor
	 * 
	 * @param newField The model for the game.
	 * @param newGUI The gui for the game.
	 */
	
	public GameController(MineField newField, MineSweeperGUI newGUI)
	{
		field = newField;
		gui = newGUI;
		
		//Add button listener.
		gui.addNewGameListener(new NewGame());
		
		//Add mouse listener
		gui.addMouseClickListener(new MouseClick());
		
		//Create the first game.
		field.newGame(9,9);
		field.countAdjacentMines(9,9);
	}
	
	private class NewGame implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			field.newGame(9,9);
			field.countAdjacentMines(9,9);
			gui.reDraw();
		}
	}
	
	private class MouseClick extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int row = -1;
			int col = -1;
			
			for(int i = 0; i < 9; i++)
			{
				for( int j = 0; j < 9; j ++)
				{
					if(e.getSource() == gui.buttons[i][j])
					{
						row = i;
						col = j;
					}
				}
			}
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				field.guessSafe(row,col);
			
				
			} else if(e.getButton() == MouseEvent.BUTTON3)
			{
				field.markMine(row,col);
			}
		}
	}
}
