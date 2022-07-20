package PegSolitaireGTU;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.MenuElement;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.Random;

/**
 * PegSolitaireFrame class provides playable GUI for Peg Solitaire Game.
 * It Extends from JFrame and implements PegSolitaireGame, Cloneable interfaces.
 * @author Burak Kocausta
 * @version 1.0 (27.01.2022)
 */ 
public class PegSolitaireFrame extends JFrame implements PegSolitaireGame, Cloneable
{
	private JButton[][] buttons; // array of buttons for representing the board.

	private int boardType;
	private int rowBound;
	private int colBound;
	private int numOfMove;
	private boolean isFinished;

	private JPanel gridPanel;	// Panel for representing the board.
	private JPanel flowPanel;	// Panel which holds reset, undo, load, save, playauto and play auto all buttons.
	private JPanel eastPanel;	// Panel which indicates the score and total number of successful move.
	private JPanel northPanel;	// Panel which holds radio buttons for board types.

	private GridLayout gridLayout;	
	private FlowLayout flowLayout;
	private FlowLayout northLayout;
	private BoxLayout boxLayout;
	private JLabel scoreBar;	 // Label for indicating current score.
	private JLabel numOfMoveBar; // Label for indicating total number of move.

	private ButtonGroup radioGroup; 					// buttongroup to hold radio buttons
	private ArrayList< ArrayList< String > > moveList;	// it holds the moves for undo
					
	private JPopupMenu menu;		// It pops up for choosing the direction.
	private JButton	resetJButton;
	private JButton undoJButton;
	private JButton saveJButton;
	private JButton loadJButton;

	private boolean flagUp;			// JButton and boolean variables to detect which peg is currently active.
	private boolean flagDown;
	private boolean flagLeft;
	private boolean flagRight;
	private JButton upperButton2;
	private JButton upperButton1;
	private JButton belowButton2;
	private JButton belowButton1;
	private JButton leftButton2;
	private JButton leftButton1;
	private JButton rightButton2;
	private JButton rightButton1;
	private JButton currentButton;

	private JRadioButton type1Button;	// Radio buttons for board types.
	private JRadioButton type2Button;
	private JRadioButton type3Button;
	private JRadioButton type4Button;
	private JRadioButton type5Button;

	/**
	 * Sets the game, and it starts with board type 1. Toggles the necessary radio button.
	 */ 
	public PegSolitaireFrame ( )
	{
		super("Peg Solitaire Game");
		boardType = 1;
		rowBound = 7;
		colBound = 7;
		numOfMove = 0;
		isFinished = false;
		menu = new JPopupMenu("Menu");	// create popup menu for directions.

		this.flowLayout = new FlowLayout( );
		this.flowPanel = new JPanel();
		this.flowPanel.setLayout( flowLayout );
		this.gridPanel = new JPanel();
		this.eastPanel = new JPanel();

		this.northPanel = new JPanel();
		this.northLayout = new FlowLayout( );
		this.northPanel.setLayout ( northLayout );

		flowPanel.setBackground( Color.LIGHT_GRAY );
		northPanel.setBackground( Color.LIGHT_GRAY );

		BoxLayout boxLayout = new BoxLayout( eastPanel, BoxLayout.Y_AXIS);	// create vertical layout.
		eastPanel.setLayout( boxLayout );

		moveList = new ArrayList < ArrayList < String > >();	// arrayList of arrayList for each board's move history.
		moveList.add( new ArrayList< String >() );
		moveList.add( new ArrayList< String >() );
		moveList.add( new ArrayList< String >() );
		moveList.add( new ArrayList< String >() );
		moveList.add( new ArrayList< String >() );

		add ( gridPanel, BorderLayout.CENTER );		// Add every panel to frame.
		add ( flowPanel, BorderLayout.SOUTH );
		add ( eastPanel, BorderLayout.EAST );
		add ( northPanel, BorderLayout.NORTH );		

		this.setBoard();
				
		this.resetBoard();

		this.undoMove();

		this.saveFile();

		this.loadFile();

		this.setEastPanel();

		this.addRadioButtons();

		this.makeMove();		

		JButton compButton = new JButton ( "Single Auto Move" );
		flowPanel.add( compButton );

		compButton.addActionListener (


			new ActionListener() {

				public void actionPerformed ( ActionEvent event ) {

					playAuto();			// make one random move if button is clicked.
					setVisible(true);
				}
			} );
		this.playAutoAll ( );
	}// end of constructor

	/**
	 * It sets the board according to boardType value from its private field.
	 * boardType can be minimum 1, maximum 5.
	 */ 
	protected void setBoard ( )
	{
		char[][] dummyBoard;	// hold label of each peg in char array temporarily.

		if ( boardType == 1 )
		{				
			rowBound = 7; colBound = 7;
			this.gridLayout = new GridLayout( rowBound, colBound, 1, 1 );
			gridPanel.setLayout( gridLayout );
			buttons = new JButton[ rowBound ][ colBound ];
			char[][] temp = {

					{ 'W', 'W', 'P', 'P', 'P', 'W', 'W'	},
					{ 'W', 'P', 'P', 'P', 'P', 'P', 'W'	},
					{ 'P', 'P', 'P', 'E', 'P', 'P', 'P'	},
					{ 'P', 'P', 'P', 'P', 'P', 'P', 'P'	},
					{ 'P', 'P', 'P', 'P', 'P', 'P', 'P'	},
					{ 'W', 'P', 'P', 'P', 'P', 'P', 'W'	},
					{ 'W', 'W', 'P', 'P', 'P', 'W', 'W'	}
				};

			dummyBoard = temp;
		}// end if
		else if ( boardType == 2 )
		{
			rowBound = 9; colBound = 9;
			this.gridLayout = new GridLayout( rowBound, colBound, 1, 1 );
			gridPanel.setLayout( gridLayout );
			buttons = new JButton[ rowBound ][ colBound ];			
			char[][] temp = {

				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W',  'W'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W',  'W'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W',  'W'},
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P',  'P'},
				{ 'P', 'P', 'P', 'P', 'E', 'P', 'P', 'P',  'P'},
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P',  'P'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W',  'W'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W',  'W'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W',  'W'}
			};

			dummyBoard = temp;
		}// end if
		else if ( boardType == 3 )
		{
			rowBound = 8; colBound = 8;
			this.gridLayout = new GridLayout( rowBound, colBound, 1, 1 );
			gridPanel.setLayout( gridLayout );
			buttons = new JButton[ rowBound ][ colBound ];			
			char[][] temp = {
				
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W' },
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W' },
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W' },
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },  
				{ 'P', 'P', 'P', 'E', 'P', 'P', 'P', 'P' },
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W' },
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W' }
			};

			dummyBoard = temp;			
		}// end if
		else if ( boardType == 4 )
		{
			rowBound = 7; colBound = 7;
			this.gridLayout = new GridLayout( rowBound, colBound, 1, 1 );
			gridPanel.setLayout( gridLayout );
			buttons = new JButton[ rowBound ][ colBound ];			
			char[][] temp = {
				
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W'},
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W'},
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
				{ 'P', 'P', 'P', 'E', 'P', 'P', 'P'},
				{ 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W'},
				{ 'W', 'W', 'P', 'P', 'P', 'W', 'W'}
			};

			dummyBoard = temp;			
		}// end if
		else if ( boardType == 5 )
		{
			rowBound = 9; colBound = 9;
			this.gridLayout = new GridLayout( rowBound, colBound, 1, 1 );
			gridPanel.setLayout( gridLayout );
			buttons = new JButton[ rowBound ][ colBound ];			
			char[][] temp = {
				
				{ 'W', 'W', 'W', 'W', 'P', 'W', 'W', 'W', 'W'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W'},
				{ 'W', 'W', 'P', 'P', 'P', 'P', 'P', 'W', 'W'},
				{ 'W', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'W'},
				{ 'P', 'P', 'P', 'P', 'E', 'P', 'P', 'P', 'P'},
				{ 'W', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'W'},
				{ 'W', 'W', 'P', 'P', 'P', 'P', 'P', 'W', 'W'},
				{ 'W', 'W', 'W', 'P', 'P', 'P', 'W', 'W', 'W'},
				{ 'W', 'W', 'W', 'W', 'P', 'W', 'W', 'W', 'W'}
			};

			dummyBoard = temp;			
		}// end if
		else
			return;

		for ( int i = 0; i < rowBound; ++i )
		{
			
			for ( int j = 0; j < colBound; ++j )
			{
				
				if ( dummyBoard[i][j] == 'P' )
				{
					buttons[i][j] = new JButton("P");
					buttons[i][j].setBackground(Color.CYAN);
				}
				else if ( dummyBoard[i][j] == 'E' )
				{
					buttons[i][j] = new JButton(" ");
					buttons[i][j].setBackground(Color.PINK);
				}
				else
				{
					buttons[i][j] = new JButton(".");
					buttons[i][j].setBackground(Color.GRAY);
				}
			}// end inner for
		}// end outer for

		for ( int i = 0; i < rowBound; ++i )
		{
			for ( int j = 0; j < colBound; ++j )
			{
				buttons[i][j].addActionListener(
				new ActionListener() // anonymous inner class
					{ 
						// process JButton event 
						public void actionPerformed( ActionEvent event )
						{
							if ( event.getSource() instanceof JButton )
								currentButton = (JButton) event.getSource();
							else
								return;

							if ( currentButton.getText().equals(".") == false )
								menu.show( currentButton, currentButton.getWidth(), currentButton.getHeight() );


							for ( int i = 0; i < rowBound; ++i )
							{
								for ( int j = 0; j < colBound; ++j )
								{
									if ( buttons[i][j] == currentButton )
									{
										flagUp = checkUp(i,j);
						                flagDown = checkDown(i,j);
						                flagLeft = checkLeft(i,j);
						                flagRight = checkRight(i,j);

						                upperButton2 = flagUp ? buttons[i-2][j] : buttons[i][j];
						                upperButton1 = flagUp ? buttons[i-1][j] : buttons[i][j];

						                belowButton2 = flagDown ? buttons[i+2][j] : buttons[i][j];
						                belowButton1 = flagDown ? buttons[i+1][j] : buttons[i][j];

						                leftButton2 = flagLeft ? buttons[i][j-2] : buttons[i][j];
						                leftButton1 = flagLeft ? buttons[i][j-1] : buttons[i][j];

						                rightButton2 = flagRight ? buttons[i][j+2] : buttons[i][j];
						                rightButton1 = flagRight ? buttons[i][j+1] : buttons[i][j];					
									}// end if
								}// end inner for
							}// end outer for
						} // end method actionPerformed
					} // end anonymous inner class
				);
				gridPanel.add(buttons[i][j]);				
			}// end inner for
		}// end outer for
	}// end method

	/**
	 * It counts total number of peg in the board.
	 * @return It returns int which represents total number of peg in the board.
	 */ 
	@Override
	public int getNumOfPeg ( )
	{
		int count = 0;
		for ( int i = 0; i < rowBound; ++i )
			for ( int j = 0; j < colBound; ++j )
				if ( buttons[i][j].getText().equals("P") )
					count++;
		return count;
	}// end method

	// returns true if move can be made.
	private boolean checkUp ( int row, int col ) 
	{
		return ( buttons[row][col].getText().equals( "P" ) && row >= 2 && buttons[row-2][col].getText().equals( " " ) && buttons[row-1][col].getText().equals( "P" ));
	}

	// returns true if move can be made.
	private boolean checkDown ( int row, int col ) 
	{
		return ( buttons[row][col].getText().equals( "P" ) && row+2 < rowBound && buttons[row+2][col].getText().equals( " " ) && buttons[row+1][col].getText().equals( "P" ));
	}

	// returns true if move can be made.
	private boolean checkLeft ( int row, int col ) 
	{
		return ( buttons[row][col].getText().equals( "P" ) && col >= 2 && buttons[row][col-2].getText().equals( " " ) && buttons[row][col-1].getText().equals( "P" ));
	}

	// returns true if move can be made.
	private boolean checkRight ( int row, int col ) 
	{
		return ( buttons[row][col].getText().equals( "P" ) && col+2 < colBound && buttons[row][col+2].getText().equals( " " ) && buttons[row][col+1].getText().equals( "P" ));
	}
	
	/**
	 * It sets the board its initial state.
	 */ 	
	@Override
	public void resetBoard()
	{
		resetJButton = new JButton( "Reset" ); // create Reset button
		flowPanel.add( resetJButton );

		 	
		resetJButton.addActionListener(

				new ActionListener() // anonymous inner class
					{ 
						// process leftJButton event 
						public void actionPerformed( ActionEvent event )
						{
							removeButtons();
							setBoard();
							setVisible(true);
							moveList.get(boardType-1).clear();
							numOfMove = 0;
							scoreBar.setText( String.format("%d", getNumOfPeg() ) );
							numOfMoveBar.setText( String.format("%d", numOfMove ) );	
						} // end method actionPerformed
					} // end anonymous inner class
				); // end call to addActionListener	
	}// end method

	// removes button from panel.
	private void removeButtons ()
	{
		for(int i = 0; i < rowBound; ++i) {
			for(int j = 0; j < colBound; ++j) {
				currentButton = buttons[i][j];
				gridPanel.remove(currentButton);
				setVisible(true);
			}
		}	
	}// end metod
	
	/**
	 * Checks if game is ended or not.
	 * @return returns true if game is ended, otherwise false.
	 */ 
	@Override
	public boolean endGame ( )
	{
		isFinished = true;
		for ( int i = 0; i < rowBound && isFinished; ++i )
		{
			for ( int j = 0; j < colBound && isFinished; ++j )
			{
					if ( checkUp ( i,j ) )
						isFinished = false;
					else if ( checkDown ( i,j ) )
						isFinished = false;
					else if ( checkRight ( i,j ) )
						isFinished = false;
					else if ( checkLeft ( i,j ) )
						isFinished = false;				
			}
		}
		return isFinished;
	}// end method

	/**
	 * It takes back the last move, sets the board its previous state. 
	 */ 
	@Override
	public void undoMove ()
	{
		undoJButton = new JButton ( "Undo" );
		flowPanel.add( undoJButton );
		undoJButton.addActionListener(

		new ActionListener()
		{
			public void actionPerformed( ActionEvent event )
			{

				if ( moveList.get(boardType-1).size() > 0 )
				{
					int row = Integer.parseInt(String.format("%c",moveList.get(boardType-1).get(numOfMove-1).charAt(0)));
					int col = Integer.parseInt(String.format("%c",moveList.get(boardType-1).get(numOfMove-1).charAt(1)));

					for ( int i = 0; i < rowBound; ++i )
					{
						for ( int j = 0; j < colBound; ++j )
						{
							if ( i == row && j == col )
							{
								currentButton = buttons[i][j];
								currentButton.setText( "P" );
								currentButton.setBackground( Color.CYAN );

								if ( moveList.get(boardType-1).get(numOfMove-1).charAt(2) == 'U' )
								{
									currentButton = buttons[i-1][j];
									currentButton.setText( "P" );
									currentButton.setBackground( Color.CYAN );

									currentButton = buttons[i-2][j];
									currentButton.setText( " " );
									currentButton.setBackground( Color.PINK );								
								}

								else if ( moveList.get(boardType-1).get(numOfMove-1).charAt(2) == 'D' )
								{
									currentButton = buttons[i+1][j];
									currentButton.setText( "P" );
									currentButton.setBackground( Color.CYAN );

									currentButton = buttons[i+2][j];
									currentButton.setText( " " );
									currentButton.setBackground( Color.PINK );								
								}


								else if ( moveList.get(boardType-1).get(numOfMove-1).charAt(2) == 'L' )
								{
									currentButton = buttons[i][j-1];
									currentButton.setText( "P" );
									currentButton.setBackground( Color.CYAN );

									currentButton = buttons[i][j-2];
									currentButton.setText( " " );
									currentButton.setBackground( Color.PINK );								
								}

								else if ( moveList.get(boardType-1).get(numOfMove-1).charAt(2) == 'R' )
								{
									currentButton = buttons[i][j+1];
									currentButton.setText( "P" );
									currentButton.setBackground( Color.CYAN );

									currentButton = buttons[i][j+2];
									currentButton.setText( " " );
									currentButton.setBackground( Color.PINK );									
								}
								moveList.get(boardType-1).remove(numOfMove-1);
								--numOfMove;
								scoreBar.setText( String.format("%d", getNumOfPeg() ) );
								numOfMoveBar.setText( String.format("%d", numOfMove ) );
							}
						}//end inner for
					}// end outer for					
				}

			} // end method actionPerformed			
		});				
	}// end method
	
	/**
	 * It writes the game to given filename.
	 * boardType, ending state, row bound, column bound, board, and move list are written to a file with using PrintWriter.
	 * @throws IOException if any error occurs.
	 */ 
	@Override
	public void saveFile ( )
	{
		saveJButton = new JButton( "Save" );

		flowPanel.add( saveJButton );

		saveJButton.addActionListener(

			new ActionListener()
			{
				public void actionPerformed ( ActionEvent event ) {
					String fileName = JOptionPane.showInputDialog ( "Please enter the filename(Ex: game1.txt)" );
					
					if ( fileName == null || fileName.length() < 1 ) return;

					try
					{
						PrintWriter gamePrinter = new PrintWriter( fileName );
						gamePrinter.printf( "numOfMove = %d\nstate = ", numOfMove );
						
						if ( isFinished ) gamePrinter.printf( "1\n" );
						else gamePrinter.printf( "0\n" );
						gamePrinter.printf( "boardType = %d\n", boardType );
						gamePrinter.printf( "rowBound = %d\n", rowBound );
						gamePrinter.printf( "colBound = %d\n", colBound );

						for ( int i = 0; i < rowBound; ++i )
						{
							for ( int j = 0; j < colBound; ++j )
							{
								if ( buttons[i][j].getText().equals( "P" ) || buttons[i][j].getText().equals( " " ) )
									gamePrinter.printf("%s", buttons[i][j].getText() );
								else 
									gamePrinter.printf(".");

							}
							gamePrinter.printf("\n");
						}
						for ( int i = 0; i < moveList.get(boardType-1).size(); ++i )
							gamePrinter.printf( "%s\n",moveList.get(boardType-1).get(i) );
						
						gamePrinter.close();
					}
					catch ( IOException eFile )
					{
						JOptionPane.showMessageDialog ( null,"An error occurred, board cannot be saved!" );
					}					
				}
			}
		);
	}// end method
	
	/**
	 * It reads game the from given filename.
	 * boardType, ending state, row bound, column bound, board, and move list are read from file.  
	 * @throws FileNotFoundException if file cannot be found.
	 * @throws Exception for any kind of errors.
	 */ 
	@Override
	public void loadFile ( )
	{
		loadJButton = new JButton( "Load" );

		flowPanel.add( loadJButton );
		loadJButton.addActionListener(

			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					
					String fileName = JOptionPane.showInputDialog ( "Please enter the filename(Ex: game1.txt)" );	
					if ( fileName == null || fileName.length() < 1 ) return;

					try 
					{
						File myObj = new File(fileName);
						Scanner myReader = new Scanner(myObj);

						String data = myReader.nextLine();

						int[] tempVal = new int[5];

						if ( data.length() >= 13 && data.substring(0,12).equals("numOfMove = ") )
								tempVal[0] = Integer.parseInt( data.substring(12) );
						else throw new Exception( "Load file format is invalid!" );

						data = myReader.nextLine();
						if ( data.length() >= 9 && data.substring(0,8).equals("state = ") )
								tempVal[1] = Integer.parseInt( data.substring(8) );
						else throw new Exception( "Load file format is invalid!" );
						
						data = myReader.nextLine();
						if ( data.length() >= 13 && data.substring(0,12).equals("boardType = ") ){
							tempVal[2] = Integer.parseInt( data.substring(12) );
							if ( tempVal[2] < 1 || tempVal[2] > 5 )
								throw new Exception( "Load file format is invalid!" );
						}
						else throw new Exception( "Load file format is invalid!" );
						
						data = myReader.nextLine();
						if ( data.length() >= 12 && data.substring(0,11).equals("rowBound = ") )
								tempVal[3] = Integer.parseInt( data.substring(11) );
						else throw new Exception( "Load file format is invalid!" );					      

						data = myReader.nextLine();
						if ( data.length() >= 12 && data.substring(0,11).equals("colBound = ") )
								tempVal[4] = Integer.parseInt( data.substring(11) );
						else throw new Exception( "Load file format is invalid!" );

						JButton[][] tempButtons = new JButton[tempVal[3]][tempVal[4]];

						for ( int i = 0; i < tempVal[3]; ++i )
						{

							data = myReader.nextLine();

							if ( data.length() != tempVal[4] )
								throw new Exception( "Load file format is invalid!" );

							for ( int j = 0; j < tempVal[4]; ++j )
							{

								if (data.charAt(j) == 'P')
								{
									tempButtons[i][j] = new JButton("P");
									tempButtons[i][j].setBackground(Color.CYAN);
								}
								else if ( data.charAt(j) == '.' )
								{
									tempButtons[i][j] = new JButton(".");
									tempButtons[i][j].setBackground(Color.GRAY);
								}
								else if ( data.charAt(j) == ' ' )
								{
									tempButtons[i][j] = new JButton(" ");
									tempButtons[i][j].setBackground(Color.PINK);
								}
								else if ( data.charAt(j) == '\r' ) continue;
								
								else
									throw new Exception( "Load file format is invalid!" );
							}
						}
						ArrayList< String > tempMoveList = new ArrayList < String >();
						for ( int i = 0; i < tempVal[0]; ++i )
						{
							data = myReader.nextLine();

							if ( data.length() >= 3 )
							{
								boolean flag = false;
								if ( data.charAt(0) >= '0' && data.charAt(0) <= '9' &&
									 data.charAt(1) >= '0' && data.charAt(1) <= '9' &&
									 (data.charAt(2) == 'U' || data.charAt(2) == 'D' || 
									  data.charAt(2) == 'R' || data.charAt(2) == 'L' ) 
									)
								{
									flag = true;
								}// end if
								if ( data.length() > 3 ) {									
									if ( data.charAt(3) == '\r' ) {
										tempMoveList.add(data.substring(0,3));
										continue;
									}// end if
									flag = false;
								}// end if
								if(flag)
									tempMoveList.add(data);
								
								else 
									throw new Exception( "Load file format is invalid!" );
							}// end if
							else
								throw new Exception( "Load file format is invalid!" );
						}// end for

						removeButtons();
						numOfMove = tempVal[0];		boardType = tempVal[2];			rowBound = tempVal[3];		colBound = tempVal[4];
						if ( tempVal[1] == 0 ) isFinished = false;
						else isFinished = true;

						if ( tempVal[0] == 0 ) moveList.get(boardType-1).clear();
						else moveList.set( boardType-1, tempMoveList);						

						if ( boardType == 1 ) type1Button.setSelected(true);
						else if ( boardType == 2 ) type2Button.setSelected(true);
						else if ( boardType == 3 ) type3Button.setSelected(true);
						else if ( boardType == 4 ) type4Button.setSelected(true);
						else if ( boardType == 5 ) type5Button.setSelected(true);

						setBoard();

						for ( int i = 0; i < rowBound; ++i )
						{							
							for ( int j = 0; j < colBound; ++j )
							{
								buttons[i][j].setText( tempButtons[i][j].getText() );
								buttons[i][j].setBackground( tempButtons[i][j].getBackground() );
							}
						}

						scoreBar.setText( String.format("%d", getNumOfPeg() ) );
						numOfMoveBar.setText( String.format("%d", numOfMove ) );					
					    setVisible(true);
					    myReader.close(); 
					}// end try
				    catch ( FileNotFoundException e ) {
						JOptionPane.showMessageDialog ( null,"File does not exist! Board cannot be loaded.\n" );
				    }
				    catch ( Exception e2 ) {
				    	JOptionPane.showMessageDialog ( null, e2 + "\n" );
					}
				}// end of actionPerformer.
			}// end of anonymous class
		);		
	}// end method

	/**
	 * It makes deep copy of PegSolitaireFrame object.
	 * @return Returns reference of created PegSolitaireFrame object.
	 */ 
	@Override
	public PegSolitaireFrame clone ( )
	{
		try
		{
			PegSolitaireFrame copy = (PegSolitaireFrame) super.clone();

			copy.moveList = new ArrayList< ArrayList < String > >();
			moveList.add( new ArrayList< String >( moveList.get(0) ) );
			moveList.add( new ArrayList< String >( moveList.get(1) ) );
			moveList.add( new ArrayList< String >( moveList.get(2) ) );
			moveList.add( new ArrayList< String >( moveList.get(3) ) );			
			moveList.add( new ArrayList< String >( moveList.get(4) ) );

			copy.rowBound = this.rowBound;
			copy.colBound = this.colBound;
			copy.boardType = this.boardType;

			copy.buttons = new JButton[rowBound][colBound];

			for ( int i = 0; i < rowBound; ++i )
			{
				for ( int j = 0; j < colBound; ++j )
				{
					copy.buttons[i][j] = new JButton(buttons[i][j].getText());
					copy.buttons[i][j].setBackground ( buttons[i][j].getBackground() );
				}
			}

			return copy;
		}
		catch ( CloneNotSupportedException e )
		{
			return null;
		}		
	}// end of clone

	// adds radio buttons to the frame
	private void addRadioButtons ( )
	{
		type1Button = new JRadioButton( "Type 1", true );
		type2Button = new JRadioButton( "Type 2", false );
		type3Button = new JRadioButton( "Type 3", false );
		type4Button = new JRadioButton( "Type 4", false );
		type5Button = new JRadioButton( "Type 5", false );			

		radioGroup = new ButtonGroup();
		radioGroup.add( type1Button );
		radioGroup.add( type2Button );
		radioGroup.add( type3Button ); 
		radioGroup.add( type4Button );
		radioGroup.add( type5Button );
		JLabel radioLabel = new JLabel("Board Types: ");
		//	flowPanel.add(Box.createHorizontalStrut(20));
		northPanel.add ( radioLabel );
		northPanel.add ( type1Button );
		northPanel.add ( type2Button );
		northPanel.add ( type3Button );
		northPanel.add ( type4Button );
		northPanel.add ( type5Button );

		ActionListener rButtonListener = new ActionListener() 
		{
			public void actionPerformed ( ActionEvent event )
			{
				if ( type1Button.isSelected() ) boardType = 1;
				else if ( type2Button.isSelected() ) boardType = 2;
				else if ( type3Button.isSelected() ) boardType = 3;
				else if ( type4Button.isSelected() ) boardType = 4;
				else if ( type5Button.isSelected() ) boardType = 5;
				else boardType = 1;

				removeButtons();
				setBoard();
				numOfMove = 0;
				performInstructions ( moveList.get(boardType-1) );
				scoreBar.setText( String.format("%d", getNumOfPeg() ) );
				numOfMoveBar.setText( String.format("%d", numOfMove ) );					
				setVisible(true);																						
			}
		};

		type1Button.addActionListener( rButtonListener );
		type2Button.addActionListener( rButtonListener );
		type3Button.addActionListener( rButtonListener );
		type4Button.addActionListener( rButtonListener );
		type5Button.addActionListener( rButtonListener );
	}// end method

	/**
	 * Plays game according to the commands in the moveListArray
	 * @param moveListArray Accepts ArrayList parameter as command list.
	 */  
	protected void performInstructions ( ArrayList < String > moveListArray )
	{
		if ( moveListArray != null )
		{
			for ( int i = 0; i < moveListArray.size(); ++i )
			{
				if ( moveListArray.get(i).length() > 2 )
				{
					int row = Integer.parseInt( String.format("%s", moveListArray.get(i).charAt(0) ) );
					int col = Integer.parseInt( String.format("%s", moveListArray.get(i).charAt(1) ) );
					char dir = moveListArray.get(i).charAt(2);

					if ( dir == 'U' && checkUp(row,col) ){
						numOfMove++;
						moveUp(row,col);
					}
					
					else if ( dir == 'D' && checkDown(row,col) ){
						numOfMove++;
						moveDown(row,col);
					}

					else if ( dir == 'L' && checkLeft(row,col) ){
						numOfMove++;
						moveLeft(row,col);
					}

					else if ( dir == 'R' && checkRight(row,col) ){
						numOfMove++;
						moveRight(row,col);
					}
				}// end if
			}// end inner for
		}// end outer for
	}// end method

	/**
	 * Adds and implements action listener for each direction chosen.
	 */ 
	@Override
	public void makeMove ( )
	{
		JMenuItem upItem = new JMenuItem("UP");  
		JMenuItem leftItem = new JMenuItem("LEFT");  
		JMenuItem rightItem = new JMenuItem("RIGHT");
		JMenuItem downItem = new JMenuItem("DOWN"); 

		menu.add(upItem); menu.add(leftItem); menu.add(rightItem); menu.add(downItem);
		upItem.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent e) {    
 					if ( flagUp )
					{
						currentButton.setText(" ");
						currentButton.setBackground(Color.PINK);
						upperButton1.setText(" ");
						upperButton1.setBackground(Color.PINK);
						upperButton2.setText("P");
						upperButton2.setBackground(Color.CYAN);
						for ( int i = 0; i < rowBound; ++i)
						{
							for ( int j = 0; j < colBound; ++j)
							{
								if (buttons[i][j] == currentButton)
									moveList.get(boardType-1).add(String.format( "%d%dU",i,j ));
							}
						}
						++numOfMove;
						scoreBar.setText( String.format("%d", getNumOfPeg() ) );
						numOfMoveBar.setText( String.format("%d", numOfMove ) );				
						if ( endGame() )
							JOptionPane.showMessageDialog ( null,"Congratulations! Game is finished." );

					}
			 	} 
			}
		);

		leftItem.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent e) {              
 					if ( flagLeft )
					{
						currentButton.setText(" ");
						currentButton.setBackground(Color.PINK);
						leftButton1.setText(" ");
						leftButton1.setBackground(Color.PINK);
						leftButton2.setText("P");
						leftButton2.setBackground(Color.CYAN);
						for ( int i = 0; i < rowBound; ++i)
						{
							for ( int j = 0; j < colBound; ++j)
							{
								if (buttons[i][j] == currentButton)
									moveList.get(boardType-1).add(String.format( "%d%dL",i,j ));
							}
						}	
						numOfMove++;
						scoreBar.setText( String.format("%d", getNumOfPeg() ) );
						numOfMoveBar.setText( String.format("%d", numOfMove ) );				
						if ( endGame() )
							JOptionPane.showMessageDialog ( null,"Congratulations! Game is finished." );												
					}
			 	} 
			}
		);

		rightItem.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent e) {              
 					if ( flagRight )
					{
						currentButton.setText(" ");
						currentButton.setBackground(Color.PINK);
						rightButton1.setText(" ");
						rightButton1.setBackground(Color.PINK);
						rightButton2.setText("P");
						rightButton2.setBackground(Color.CYAN);
						for ( int i = 0; i < rowBound; ++i)
						{
							for ( int j = 0; j < colBound; ++j)
							{
								if (buttons[i][j] == currentButton)
									moveList.get(boardType-1).add(String.format( "%d%dR",i,j ));
							}
						}
						++numOfMove;
						scoreBar.setText( String.format("%d", getNumOfPeg() ) );
						numOfMoveBar.setText( String.format("%d", numOfMove ) );				
						if ( endGame() )
							JOptionPane.showMessageDialog ( null,"Congratulations! Game is finished." );													
					}
			 	} 
			}
		);				

		downItem.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent e) {              
 					if ( flagDown )
					{
						currentButton.setText(" ");
						currentButton.setBackground(Color.PINK);
						belowButton1.setText(" ");
						belowButton1.setBackground(Color.PINK);
						belowButton2.setText("P");
						belowButton2.setBackground(Color.CYAN);
						for ( int i = 0; i < rowBound; ++i)
						{
							for ( int j = 0; j < colBound; ++j)
							{
								if (buttons[i][j] == currentButton)
									moveList.get(boardType-1).add(String.format( "%d%dD",i,j ));
							}
						}
						++numOfMove;
						scoreBar.setText( String.format("%d", getNumOfPeg() ) );
						numOfMoveBar.setText( String.format("%d", numOfMove ) );
						if ( endGame() )
							JOptionPane.showMessageDialog ( null,"Congratulations! Game is finished." );						
					}
			 	} 
			}
		);			
	}// end method

	// makes upper movement without checking. Check validity before usage.
	private void moveUp ( int row, int col )
	{
		buttons[row][col].setText( " " );
		buttons[row][col].setBackground( Color.PINK );
		buttons[row-1][col].setText( " " );
		buttons[row-1][col].setBackground( Color.PINK );
		buttons[row-1][col].setText( " " );
		buttons[row-1][col].setBackground( Color.PINK );
		buttons[row-2][col].setText( "P" );
		buttons[row-2][col].setBackground( Color.CYAN );		
	}

	// makes below movement without checking. Check validity before usage.
	private void moveDown ( int row, int col )
	{
		buttons[row][col].setText( " " );
		buttons[row][col].setBackground( Color.PINK );
		buttons[row+1][col].setText( " " );
		buttons[row+1][col].setBackground( Color.PINK );
		buttons[row+1][col].setText( " " );
		buttons[row+1][col].setBackground( Color.PINK );
		buttons[row+2][col].setText( "P" );
		buttons[row+2][col].setBackground( Color.CYAN );		
	}

	// makes left movement without checking. Check validity before usage.
	private void moveLeft ( int row, int col )
	{
		buttons[row][col].setText( " " );
		buttons[row][col].setBackground( Color.PINK );
		buttons[row][col].setText( " " );
		buttons[row][col].setBackground( Color.PINK );
		buttons[row][col-1].setText( " " );
		buttons[row][col-1].setBackground( Color.PINK );
		buttons[row][col-2].setText( "P" );
		buttons[row][col-2].setBackground( Color.CYAN );	
	}

	// makes right movement without checking. Check validity before usage.
	private void moveRight ( int row, int col )
	{
		buttons[row][col].setText( " " );
		buttons[row][col].setBackground( Color.PINK );
		buttons[row][col].setText( " " );
		buttons[row][col].setBackground( Color.PINK );
		buttons[row][col+1].setText( " " );
		buttons[row][col+1].setBackground( Color.PINK );
		buttons[row][col+2].setText( "P" );
		buttons[row][col+2].setBackground( Color.CYAN );		
	}
	
	/**
	 * Makes one random move on the board.
	 */ 			
	@Override
	public void playAuto ( )
	{
		Random rand = new Random();
		boolean flag = true;

		while ( flag && !endGame() )
		{
			int row = rand.nextInt(rowBound);
        	int col = rand.nextInt(colBound);	
		
        	for ( int i = 0; i < 4; ++i )
        	{
        		if ( checkUp( row, col ) )
        		{
        			moveUp(row,col);
					moveList.get(boardType-1).add(String.format( "%d%dU", row, col ));        			
        			flag = false; break;  			
        		}
        		
        		else if ( checkDown( row, col ) )
        		{
					moveDown(row,col);
					moveList.get(boardType-1).add(String.format( "%d%dD", row, col ));           			
        			flag = false; break; 			
        		}

        		else if ( checkLeft( row, col ) )
        		{
        			moveLeft(row,col);
					moveList.get(boardType-1).add(String.format( "%d%dL", row, col ));           			
        			flag = false; break; 	       			
        		}

        		else if ( checkRight( row, col ) )
        		{
        			moveRight(row,col);
					moveList.get(boardType-1).add(String.format( "%d%dR", row, col ));       			
        			flag = false; break;         			
        		}
        	}
		}

		if ( !flag )
		{
			numOfMove++;
			scoreBar.setText( String.format("%d", getNumOfPeg() ) );
			numOfMoveBar.setText( String.format("%d", numOfMove ) );
			if ( endGame() )
				JOptionPane.showMessageDialog ( null,"Congratulations! Game is finished." );					
		}
	}// end method

	/**
	 * Performs random moves till game ends.
	 */ 
	@Override
	public void playAutoAll ( )
	{
		JButton compButton2 = new JButton ( "Play Auto All" );
		flowPanel.add( compButton2 );

		compButton2.addActionListener (

			new ActionListener() {

				public void actionPerformed ( ActionEvent event ) {

					while ( !endGame() )
					{
						playAuto();						
						setVisible(true);	
					}
				}
			} );		
	}// end method

	// Sets panel which holds score, and number of successful move bar. 
	private void setEastPanel ( )
	{
		scoreBar = new JLabel( String.format( "%d", getNumOfPeg() ) );

		scoreBar.setForeground(Color.BLACK);
		scoreBar.setBackground(Color.YELLOW);
		scoreBar.setOpaque(true);
		scoreBar.setFont( new Font( "Serif", Font.BOLD, 70 ) );
		
		JLabel scoreText = new JLabel("SCORE");
		scoreText.setFont( new Font("Plain", Font.BOLD, 25 ) );
		JPanel tempPanel = new JPanel();
		JPanel tempPanel2 = new JPanel();

		JPanel tempPanel3 = new JPanel();
		JPanel tempPanel4 = new JPanel();

		numOfMoveBar = new JLabel( String.format( "%d", numOfMove ) );

		numOfMoveBar.setForeground(Color.ORANGE);
		numOfMoveBar.setBackground(Color.DARK_GRAY);
		numOfMoveBar.setOpaque(true);
		numOfMoveBar.setFont(new Font("Serif", Font.BOLD, 70));

		JLabel numOfMoveText = new JLabel("Number Of Moves");
		numOfMoveText.setFont( new Font ("Plain", Font.BOLD, 15 ) );


		scoreBar = new JLabel( String.format( "%d", getNumOfPeg() ) );

		scoreBar.setForeground(Color.GREEN);
		scoreBar.setBackground(Color.DARK_GRAY);
		scoreBar.setOpaque(true);
		scoreBar.setFont(new Font("Serif", Font.BOLD, 70));
		JPanel tempPanel5 = new JPanel( );
		tempPanel5.add ( new JLabel( "Less score is better." ) );
		tempPanel4.add ( numOfMoveBar );
		tempPanel3.add( numOfMoveText ); 
		tempPanel2.add( scoreText );
		tempPanel.add(scoreBar);

		tempPanel2.setBackground(Color.GREEN);
		tempPanel3.setBackground(Color.ORANGE);
		eastPanel.add ( tempPanel2, BorderLayout.CENTER );
		eastPanel.add( tempPanel5 );
		eastPanel.add( tempPanel, BorderLayout.CENTER );
		eastPanel.add( tempPanel3, BorderLayout.CENTER );
		eastPanel.add( tempPanel4, BorderLayout.CENTER );		
	}// end method
}// end class