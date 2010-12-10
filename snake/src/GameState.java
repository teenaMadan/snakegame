public interface GameState
{
	// Enviado para o game state quando a aplica��o recebe um evento para comecar.
	public void start();
	
	// Enviado para o game state quando a aplica��o precisa pausar o jogo.
	public void pause();
	
	// Enviado para o game state quando a aplica��o precisa terminar o jogo.
	public void destroy();
}
