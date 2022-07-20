import PegSolitaireGTU.*;
import javax.swing.JFrame; // provides basic window features

public class Test
{

	public static void main ( String args[] )
	{
		PegSolitaireFrame pegSolitaireFrame = new PegSolitaireFrame();
		pegSolitaireFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		pegSolitaireFrame.setSize( 1000, 500 ); // set frame size
		pegSolitaireFrame.setVisible( true ); // display frame	
	}
}