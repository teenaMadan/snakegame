/*
 * Developed by: Bruno Firmino da Silva
 * Creation date: 05/12/2010
 * Interface description: send states when the application receives events to start, pause or end.
 */

public interface GameState
{
	public void start();
	
	public void pause();
	
	public void destroy();
}
