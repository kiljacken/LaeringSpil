package dk.kiljacken.laeringsspil;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import dk.kiljacken.laeringsspil.particle.FireworkParticle;
import dk.kiljacken.laeringsspil.particle.ParticleSystem;

public class MainClass extends BasicGame {
	public static final boolean DEBUG = false;
	public static final boolean DEBUG_SHOW_FPS = DEBUG && true;
	public static final boolean DEBUG_SHOW_BOUNDS = DEBUG && true;
	public static final boolean DEBUG_SHOW_STATE = DEBUG && true;
	public static final boolean DEBUG_DRAW_EDGES = DEBUG && true;
	public static final boolean DEBUG_SHOW_NUM_PARTICLES = DEBUG && true;

	private State gameState;

	private int numPlayers;
	private int playerIndex;
	private Player[] players;
	
	private LabeledButton onePlayerButton;
	private LabeledButton twoPlayersButton;
	private LabeledButton threePlayersButton;
	private LabeledButton fourPlayersButton;

	private GameBoard gameBoard;
	private Dice dice;
	private QuestionManager questionManager;
	private QuestionCard currentQuestion;
	private boolean rightAnswer;
	
	private LabeledButton continueButton;
	private LabeledButton mainMenuButton;
	
	private ParticleSystem particleSystem;
	
	public MainClass() {
		super("Kom./IT Spil");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.gameState = State.MENU;
		
		float x1 = 400 - container.getDefaultFont().getWidth("1 Spiller") / 2;
		float x2 = 400 - container.getDefaultFont().getWidth("x Spillere") / 2;
		float y = 300 - container.getDefaultFont().getLineHeight() * 4;
		float yOff = 2 * container.getDefaultFont().getLineHeight();
		
		this.onePlayerButton = new LabeledButton(container, x1, y + 0 * yOff, "1 Spiller");
		this.twoPlayersButton = new LabeledButton(container, x2, y + 1 * yOff, "2 Spillere");
		this.threePlayersButton = new LabeledButton(container, x2, y + 2 * yOff, "3 Spillere");
		this.fourPlayersButton = new LabeledButton(container, x2, y + 3 * yOff, "4 Spillere");
		
		this.gameBoard = new GameBoard(container, 148, 48);
		this.dice = new Dice(container, 64, 64);
		this.questionManager = new QuestionManager(container, Assets.questionList);
		this.currentQuestion = null;
		this.rightAnswer = false;
		
		this.continueButton = new LabeledButton(container, (800 - container.getDefaultFont().getWidth("Fortsæt")) / 2, 332, "Fortsæt");
		this.mainMenuButton = new LabeledButton(container, (800 - container.getDefaultFont().getWidth("Tilbage til menuen"))/2, 340, "Tilbage til menuen");
		
		this.particleSystem = new ParticleSystem();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (gameState == State.MENU) {
			onePlayerButton.setAcceptingInput(true);
			twoPlayersButton.setAcceptingInput(true);
			threePlayersButton.setAcceptingInput(true);
			fourPlayersButton.setAcceptingInput(true);
			
			boolean onePlayer = onePlayerButton.update(container, delta);
			boolean twoPlayers = twoPlayersButton.update(container, delta);
			boolean threePlayers = threePlayersButton.update(container, delta);
			boolean fourPlayers = fourPlayersButton.update(container, delta);
			boolean clicked = onePlayer || twoPlayers || threePlayers || fourPlayers;

			if (onePlayer)
				numPlayers = 1;
			
			if (twoPlayers)
				numPlayers = 2;
			
			if (threePlayers)
				numPlayers = 3;
			
			if (fourPlayers)
				numPlayers = 4;
			
			if (clicked) {
				playerIndex = 0;
				players = new Player[numPlayers];
				
				for (int i=0; i < numPlayers; i++) {
					players[i] = new Player(gameBoard.getCenter().getX() + 4, gameBoard.getCenter().getY() + 4, Category.values()[i]);
				}
				
				gameState = State.ROLL;
				
				onePlayerButton.setAcceptingInput(false);
				twoPlayersButton.setAcceptingInput(false);
				threePlayersButton.setAcceptingInput(false);
				fourPlayersButton.setAcceptingInput(false);
			}
		}

		if (gameState == State.ROLL) {
			dice.setAcceptingInput(true);
			boolean clicked = dice.update(container, delta);

			if (clicked) {
				gameState = State.MOVE;
				dice.setAcceptingInput(false);

				gameBoard.clearMovable();
				gameBoard.setMovable(gameBoard.getFieldNear(players[playerIndex].getX(), players[playerIndex].getY(), 32), dice.getValue() + 1);
			}
		}

		if (gameState == State.MOVE) {
			gameBoard.setAcceptingInput(true);
			gameBoard.update(container, delta);

			if (gameBoard.hasMoved()) {
				gameBoard.setAcceptingInput(false);
				players[playerIndex].setTarget(gameBoard.getMovedField());
				gameBoard.clearMovable();
				gameBoard.clearMoved();
			}
			
			if (players[playerIndex].isMoving()) {
				players[playerIndex].update(container, delta);
			}

			if (players[playerIndex].arrived()) {
				if (players[playerIndex].couldWin() && players[playerIndex].getTarget() == gameBoard.getCenter()) {
					gameState = State.VICTORY;
				} else {
					gameState = State.ASK;
					
					Field field = players[playerIndex].getTarget();
					Category category = field.getCategory();
					players[playerIndex].clearTarget();
					
					if (category != Category.START) {
						currentQuestion = questionManager.getRandomQuestionForCategory(category);
						currentQuestion.reset();
					} else {
						playerIndex = (playerIndex + 1) % numPlayers;
						gameState = State.ROLL;
					}
				}
			}
			
		}

		if (gameState == State.ASK) {
			currentQuestion.setAcceptingInput(true);
			currentQuestion.update(container, delta);

			if (currentQuestion.isAnswered()) {
				currentQuestion.setAcceptingInput(false);
				players[playerIndex].setAnswered(currentQuestion.getCategory(), currentQuestion.isCorrect());
				rightAnswer = currentQuestion.isCorrect();
				currentQuestion.reset();

				gameState = State.ANSWERED;
			}
		}

		if (gameState == State.ANSWERED) {
			continueButton.setAcceptingInput(true);
			if (continueButton.update(container, delta)) {
				continueButton.setAcceptingInput(false);
				playerIndex = (playerIndex + 1) % numPlayers;
				gameState = State.ROLL;
			}
		}

		if (gameState == State.VICTORY) {
			mainMenuButton.setAcceptingInput(true);
			if (mainMenuButton.update(container, delta)) {
				mainMenuButton.setAcceptingInput(false);
				gameState = State.MENU;
			}
			
			if (Math.random() < 0.05) {
				float x = (float)(Math.random() * 800.0f);
				float y = (float)(Math.random() * 600.0f);
				
				float ang = (float)((Math.PI * 2) / 16.0f);
				
				Color color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
				
				for (int i=0; i < 16; i++) {
					float xVel = 64.0f * (float)Math.sin(i * ang);
					float yVel = 64.0f * (float)-Math.cos(i * ang);
					
					FireworkParticle p = new FireworkParticle(x, y, xVel, yVel, 2000 + (int)(Math.random() * 250), color);
					particleSystem.addParticle(p);
				}
			}
		}
		
		particleSystem.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// Background
		Assets.background.draw();
		
		// Particles
		particleSystem.render(container, g);
		
		// Debug rendering
		float debugTextY = 0;

		if (DEBUG_SHOW_FPS) {
			g.setColor(Color.white);
			g.drawString("FPS: " + container.getFPS(), 0, debugTextY);
			debugTextY += container.getDefaultFont().getLineHeight();
		}

		if (DEBUG_SHOW_STATE) {
			g.setColor(Color.white);
			g.drawString("State: " + gameState.name(), 0, debugTextY);
			debugTextY += container.getDefaultFont().getLineHeight();
		}
		
		if (DEBUG_SHOW_NUM_PARTICLES) {
			g.setColor(Color.white);
			g.drawString("Number of particles: " + particleSystem.getNumParticles(), 0, debugTextY);
			debugTextY += container.getDefaultFont().getLineHeight();
		}
		
		// Draw menu
		if (gameState == State.MENU) {
			onePlayerButton.render(container, g);
			twoPlayersButton.render(container, g);
			threePlayersButton.render(container, g);
			fourPlayersButton.render(container, g);
			
			String s = "Baggrund af Bart Kelsey. Brugt under Creative Commons BY-SA 3.0";
			g.drawString(s, 800 - container.getDefaultFont().getWidth(s), 600 - container.getDefaultFont().getHeight(s));
		}
		
		// Draw always visible stuff
		if (gameState != State.MENU && gameState != State.VICTORY) {
			// Draw dice
			dice.render(container, g);
			
			// Draw game fields
			gameBoard.render(container, g);
			
			// Draw player(s)
			players[playerIndex].render(container, g);
			
			// Show which player's turn it is
			String text = String.format("Spiller %d's tur...", playerIndex + 1);
			int textHeight = container.getDefaultFont().getLineHeight();
			int textWidth = container.getDefaultFont().getWidth(text);
	
			g.setColor(Color.white);
			g.drawString(text, 400 - textWidth / 2, 576 + (12 - textHeight / 2));
		}
		
		if (gameState == State.ASK) {
			currentQuestion.render(container, g);
		}
		
		if (gameState == State.ANSWERED) {
			if (rightAnswer) {
				Assets.correct.draw((800 - Assets.correct.getWidth()) / 2, (600 - Assets.correct.getHeight()) / 2);
			} else {
				Assets.wrong.draw((800 - Assets.wrong.getWidth()) / 2, (600 - Assets.wrong.getHeight()) / 2);
			}

			continueButton.render(container, g);
		}
		
		if (gameState == State.VICTORY) {
			Assets.victory.draw((800 - Assets.victory.getWidth())/2, (600 - Assets.victory.getHeight())/2);
			// TODO: Draw victory screen
			mainMenuButton.render(container, g);
		}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MainClass());

		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.setAlwaysRender(true);
		app.start();
	}

	public static enum State {
		MENU, ROLL, MOVE, ASK, ANSWERED, VICTORY;
	}

	public static enum Category {
		CHEMISTRY, PHYSICS, BIOLOGY, GEOGRAPHY, START;
	}
}
