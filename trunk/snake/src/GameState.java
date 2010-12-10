public interface GameState
{
	// Enviado para o game state quando a aplicação recebe um evento para comecar.
	public void start();
	
	// Enviado para o game state quando a aplicação precisa pausar o jogo.
	public void pause();
	
	// Enviado para o game state quando a aplicação precisa terminar o jogo.
	public void destroy();
}
