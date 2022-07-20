package PegSolitaireGTU;

/**
 * Interface PegSolitaireGame provides generic methods to implement for any type of Peg Solitaire game.
 * @author Burak Kocausta
 * @version 1.0 ( 27.01.2022 ) 
 */
public interface PegSolitaireGame
{
	/**
	 * Method to check if game is ended or not.
	 * @return It returns true if game is ended, otherwise returns false.
	 */
	boolean endGame ( );

	/**
	 * Method to get total number of pegs.
	 * @return It returns integer which represents total number of pegs.
	 */
	int getNumOfPeg ( );

	/**
	 * It sets the board its initial state.
	 */
	void resetBoard ( );

	/**
	 * Performs movement.
	 */
	void makeMove( );

	/**
	 * Performs single random movement.
	 */
	void playAuto ( );

	/**
	 * Makes random move till the game ends.
	 */
	void playAutoAll ( );

	/**
	 * It takes back the last move, sets the board its previous state.
	 */
	void undoMove ( );

	/**
	 * It saves some information about the game to file.
	 */
	void saveFile ( );

	/**
	 * It reads some information from given file and loads the game according to that file.
	 */
	void loadFile ( );
}