import java.util.*;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import java.util.Random;

/**
 * Autor: Bruno Firmino da Silva. Projeto para a disciplina de Modelagem e
 * Simulação de Jogos Digitais.
 */

public class StatePlaying extends Canvas implements GameState {
	private boolean rightPressed;
	private boolean leftPressed;
	private boolean upPressed;
	private boolean downPressed;
	private int init = 0;
	private int qtdepixels = 10;
	private int sentido = 0;
	// private int pos_x = 0;
	private int DIREITA = 1;
	private int ESQUERDA = 2;
	private int CIMA = 3;
	private int BAIXO = 4;
	private int tamanho = 3;
	private int tamanho_rastro = 0;
	boolean gameover = false;
	int macas_comidas = 0;

	Sprite maca, cobra, menu, background, gover;

	// Objeto que vai manter o rastro da cobra.
	Rastro rastro[] = new Rastro[100];

	private Timer timer;
	private StateTask task;
	private final int JOGANDO = 0;
	private int statusJogo = JOGANDO;
	Random rm = new Random();

	// Classe para criar o rastro da cobra.
	public class Rastro extends Sprite {

		// tempovida: serve para determinar o tempo de duração do rastro na
		// tela.
		// ativo: define se o rastro permanece ou deve ser "apagado" da tela.
		public int tempovida;
		public boolean ativo = false;

		public Rastro() {
			super();
		}

		public Rastro(int _x, int _y, int _tempovida) {
			this.x = _x;
			this.y = _y;
			this.tempovida = _tempovida;
		}

	}

	public StatePlaying() {
		// Cria as quatro entidades de imagens.
		maca = new Sprite();
		cobra = new Sprite();
		menu = new Sprite();
		background = new Sprite();
		gover = new Sprite();

		// Carrega as imagens do arquivo.
		maca.loadFromFile("/apple.png", 1);
		cobra.loadFromFile("/snake.png", 1);
		menu.loadFromFile("/menu.png", 1);
		background.loadFromFile("/bg.png", 1);
		gover.loadFromFile("/gameover.png", 1);

		// Inicia o rastro da cobra.
		for (int i = 0; i < 100; i++) {
			rastro[i] = new Rastro();
			rastro[i].loadFromFile("/snake.png", 1);
			rastro[i].x = -10;
			rastro[i].y = -10;
		}

		// Define posição inicial da maçã randomicamente.
		maca.x = rm.nextInt(23) * 10;
		maca.y = (rm.nextInt(25) * 10) + 30;

		// Define a posição da cobra no centro da tela.
		cobra.x = 120;
		cobra.y = 140;

		// Define a posição da imagem de fundo do jogo.
		menu.x = 0;
		menu.y = 0;

		// Degine a posição da imagem de fundo do jogo.
		background.x = 0;
		background.y = 0;

		// Define a posição da tela de "Game Over".
		gover.x = 0;
		gover.y = 0;

		// Controle da animação.
		timer = new Timer();
		task = new StateTask();
		timer.scheduleAtFixedRate(task, 100, 100);
	}

	public void start() {
		Snake.Display(this);
	}

	public void pause() {
	}

	public void destroy() {
	}

	protected void paint(Graphics g) {
		g.setClip(0, 0, getWidth(), getHeight());
		g.setColor(255, 255, 255);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Desenha a tela do jogo e a quantidade de maçãs comidas.
		if (init == 0) {
			menu.draw(g);
		} else {
			background.draw(g);
			g.setColor(255, 255, 255);
			g.drawString("Maçãs comidas:" + Integer.toString(macas_comidas), 5,
					2, Graphics.TOP | Graphics.LEFT);

			// Desenha a tela de "Game Over" e a pontuação final do jogador.
			if (gameover == true) {
				gover.draw(g);
				g.setColor(0, 0, 0);
				g.drawString(
						Integer.toString(macas_comidas * 1000) + " pontos", 90,
						180, Graphics.TOP | Graphics.LEFT);
			} else {
				g.setColor(0, 0, 0);
				maca.draw(g);

				// Momento em que o rastro da cobra é desenhado na tela. Se
				// ativo,
				// o tempo em que é mostrado na tela descresce um, até que se
				// torne inativo e desapareça.
				for (int i = 0; i < 100; i++) {
					if (rastro[i].ativo) {
						rastro[i].tempovida--;
						rastro[i].draw(g);
						if (rastro[i].tempovida < 0) {
							rastro[i].ativo = false;
						}
					}
				}
				cobra.draw(g);
			}
		}
	}

	private class StateTask extends TimerTask {

		public void run() {

			if (init == 1) {

				// Movimentos das teclas (cima, baixo, direita e esquerda).
				if (leftPressed == true) {
					if (sentido != DIREITA) {
						cobra.x -= qtdepixels;
						sentido = ESQUERDA;
					} else {
						cobra.x += qtdepixels;
						sentido = DIREITA;
					}
				}

				else if (rightPressed == true) {
					if (sentido != ESQUERDA) {
						cobra.x += qtdepixels;
						sentido = DIREITA;
					} else {
						cobra.x -= qtdepixels;
						sentido = ESQUERDA;
					}
				}

				else if (upPressed == true) {
					if (sentido != BAIXO) {
						cobra.y -= qtdepixels;
						sentido = CIMA;
					} else {
						cobra.y += qtdepixels;
						sentido = BAIXO;
					}
				}

				else if (downPressed == true) {
					if (sentido != CIMA) {
						cobra.y += qtdepixels;
						sentido = BAIXO;
					} else {
						cobra.y -= qtdepixels;
						sentido = CIMA;
					}
				}

				// Atribui as propriedades do rastro da cobrinha, definindo o ativo e seu x/y.
				if (rastro[tamanho_rastro].ativo == false) {
					// Verifica colisão com cauda da cobra.
					if (sentido != 0) {
						for (int i = 0; i < tamanho_rastro - 1; i++) {
							if (rastro[i].ativo == true) {
								if (cobra.x == rastro[i].x
										&& cobra.y == rastro[i].y) {
									gameover = true;
								}
							}
						}
					}
					rastro[tamanho_rastro].x = cobra.x;
					rastro[tamanho_rastro].y = cobra.y;
					rastro[tamanho_rastro].tempovida = tamanho;
					rastro[tamanho_rastro].ativo = true;
					tamanho_rastro++;
					if (tamanho_rastro > 99) {
						tamanho_rastro = 0;
					}
				}

				// Lógica para colidir com extremidades da tela (verticalmente).
				if (cobra.y < 0
						|| cobra.y + cobra.getFrameHeight() > getHeight()) {
					gameover = true;
				}
				if (cobra.y < cobra.getFrameHeight() + 10) {
					gameover = true;
				}
				if (cobra.x < 0 || cobra.x + cobra.getFrameWidth() > getWidth()) {
					gameover = true;
				}
				if (cobra.x < 0) {
					gameover = true;
				}

				// Lógica para colidir com as maçãs.
				if ((cobra.x > maca.x - 8) && (cobra.x < maca.x + 8)) {
					if ((cobra.y > maca.y - 8) && (cobra.y < maca.y + 8)) {
						// Tentando fazer funcionar para todas as resoluções.
						/*
						 * pos_x = rm.nextInt(getWidth() -
						 * cobra.getFrameWidth()) / 10; if(pos_x <= 0){ maca.x =
						 * 0; System.out.println(maca.x); } else { String
						 * pos_strx = String.valueOf(pos_x); String pos_xtrat =
						 * pos_strx.substring(0, 2); maca.x =
						 * Integer.valueOf(pos_xtrat).intValue() * 10;
						 * System.out.println(maca.x); }
						 */

						maca.x = (rm.nextInt(23) * 10);
						maca.y = (rm.nextInt(25) * 10) + 30;
						macas_comidas++;
						tamanho++;
					}
				}

				// Controle do número de ciclos de exibicao de mensagem.
				if (statusJogo > 0)
					statusJogo--;
				else if (statusJogo < 0)
					statusJogo++;
				repaint();
			}
		}
	}

	protected void keyPressed(int keyCode) {

		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;

		// Captura da tecla pressionada.
		switch (getGameAction(keyCode)) {
		case LEFT:
			leftPressed = true;
			break;
		case RIGHT:
			rightPressed = true;
			break;
		case UP:
			upPressed = true;
			break;
		case DOWN:
			downPressed = true;
			break;
		case FIRE:
			init = 1;
			rightPressed = true;
			break;
		}
	}
}