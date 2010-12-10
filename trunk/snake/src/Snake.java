/*
 * Developed by: Bruno Firmino da Silva
 * Creation date: 05/12/2010
 * Class description: implements MIDlet functions related to the cellphone.
 */

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Snake extends MIDlet
{
	public static Snake gameInstance;
	private static Display display;
	private static GameState currentGameState;
	
	static	{
		currentGameState = new StatePlaying();
	}

	protected void startApp() throws MIDletStateChangeException	{
		gameInstance = this;
		display = Display.getDisplay(this);
		currentGameState.start(); 
	}

	protected void pauseApp()	{
		currentGameState.pause();	
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		currentGameState.destroy();	
	}
	
	public void exit()	{
		notifyDestroyed();
	}	
	
	static public void setCurrGameState(GameState gs) {
		currentGameState = gs;
	}
	
	static public void Display(Displayable d) {
		display.setCurrent(d);
	}	
}
