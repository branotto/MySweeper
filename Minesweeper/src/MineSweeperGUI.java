import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * The MineSweeperGUI class creates the GUI
 * for Minesweeper.
 * @author Brandon Otto
 *
 */

public class MineSweeperGUI extends JFrame 
{
	
	final int WINDOW_WIDTH = 600;
	final int WINDOW_HEIGHT = 400;
	
	private MineField gameField;
	private JPanel game;
	private JPanel score;
	private boolean[][] gridStatus;
	private int[][] gridMarkers;
	
	private int rowNumber = 9;
	private int colNumber = 9;
	
	//new game button
	private JButton reset;
	
	//label for the number of mines remaining
	//to be marked
	JLabel mineDisplay;
	
	//text field displaying the current # of mines
	//remaining to be marked
	JTextField minesLeft;
	
	//arrays to hold the buttons and panels
	public JButton[][] buttons;
	private JPanel[][] panels;
	
	/**
	 * constructor
	 */
	
	public MineSweeperGUI(MineField field)
	{	
		gameField = field;
		
		setTitle("MineSweeper");
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		buildScorePanel();
		buildGamePanel();
		
		add(score, BorderLayout.NORTH);
		add(game, BorderLayout.CENTER);
		
	}
	
	/**
	 * Redraws a game board.
	 * 
	 */
	
	public void reDraw()
	{
		for(int a = 0; a < rowNumber; a++)
		{
			for(int b = 0; b < colNumber; b++)
			{
				buttons[a][b].setText("");
			}
		}
		
		gridMarkers = gameField.gridMarkers(rowNumber, colNumber);
		gridStatus = gameField.gridStatus(rowNumber, colNumber);
		
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				if(gridStatus[i][j] == true)
				{
					for(int k = 0; k < rowNumber; k++)
					{
						for(int l = 0; l < colNumber; l++ )
						{
							if(gridMarkers[k][l] == -1)
							{
								buttons[k][l].setText("Mine");
							} else
							{
								if(gridMarkers[k][l] > 0)
								{
									buttons[k][l].setText(Integer.toString(gridMarkers[k][l]));
								} else
								{
									buttons[i][j].setText("-");
								}
								
							}
						}
					}	
				}
			}
		}
		
	}
	
	public void notifyView()
	{
		minesLeft.setText(Integer.toString(gameField.minesRemaining()));
		
		gridStatus = gameField.gridStatus(rowNumber, colNumber);
		
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				if(gridStatus[i][j] == true)
				{
					if(gameField.neighborMines(i,j) > 0)
					{
						buttons[i][j].setText(Integer.toString(gameField.neighborMines(i,j)));
					} else
					{
						buttons[i][j].setText("-");
					}
				}
			}
		}
	}
	
	public void notifyView(int row, int col)
	{
		buttons[row][col].setText("M");
		minesLeft.setText(Integer.toString(gameField.minesRemaining()));
	}
	
	public void addNewGameListener(ActionListener al)
	{
		reset.addActionListener(al);
	}
	
	public void addMouseClickListener(MouseAdapter m)
	{
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				buttons[i][j].addMouseListener(m);
			}
		}
	}
	
	/**
	 * The buildScorePanel method builds the score display. 
	 * It shows the mines remaining and new game button.
	 * 
	 * @author Brandon Otto
	 *
	 */
	
	public void buildScorePanel() 
	{
		//Create the score panel
		score = new JPanel();
		
		//Create the grid layout
		score.setLayout(new GridLayout(1,3));
			

		//Create 3 panels
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
			

		//Add the labels to the panels.
		mineDisplay = new JLabel("Mines Remaining");
		minesLeft = new JTextField(10);
		minesLeft.setText(Integer.toString(gameField.minesRemaining()));
		minesLeft.setEditable(false);;
			
		panel1.add(mineDisplay);
		panel2.add(minesLeft);
		
		//Add the new game button
		reset = new JButton("New Game");
		panel3.add(reset);
			
		//Add the panels to the grid.
		score.add(panel1);
		score.add(panel2);
		score.add(panel3);
			
		//Add a border around the panel
		score.setBorder(BorderFactory.createLoweredBevelBorder());
		}
	
	/**
	 * The buildGamePanel builds the main playing area 
	 * for minesweeper.
	 * 
	 * @author Brandon Otto
	 *
	 */

	public void buildGamePanel() 
	{
		game = new JPanel();
			
		//set the layout.
		game.setLayout(new GridLayout(rowNumber,colNumber));
			
		//create the panels;
		panels = new JPanel[rowNumber][colNumber];
			
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				panels[i][j] = new JPanel();
			}
		}

		//create the buttons;
		buttons = new JButton[rowNumber][colNumber];
			
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				buttons[i][j] = new JButton();
			}
		}
			
		//add the buttons to the panels;
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				panels[i][j].add(buttons[i][j]);
			}
		}
			
		//add the panels to the grid
		for(int i = 0; i < rowNumber; i++)
		{
			for(int j = 0; j < colNumber; j++ )
			{
				game.add(panels[i][j]);
			}
		}	
			
		//Add a border around the panel
		game.setBorder(BorderFactory.createLoweredBevelBorder());
	}

}
