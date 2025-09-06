package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import audio.AudioUtilities;
import deviceInputs.KeyHandler;
import gameObjects.DynamicObject;
import gameObjects.GameObjectUtilities;
import gameObjects.Monster;
import gameObjects.Player;
import mapControls.MapUtilities;
import windows.Window;

/**
 * The {@code GamePanel} class is the panel that
 * manages the main Thread of the game.
 * 
 * <p> It implements {@link java.lang.Runnable} to handle the game loop. </p>
 * <p> This class also implements {@link javax.swing.JPanel} functionality. </p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	/**
	 * A pointer to the {@link windows.Window} object this {@link GamePanel} object was added to.
	 */
	private Window window;
	
	/**
	 * This thread handles the game loop.
	 */
	private Thread panelThread;
	
	/**
	 * The desired FPS.
	 */
	private final int FPS = 60;
	
	/**
	 * This variable tracks whether the game is paused or not.
	 */
	public static boolean gamePaused = false;
	
	/**
	 * This variable tracks whether the game is updatable or not.
	 * It is set to false whenever the game is in a state of saving
	 * the progress of the player or refreshing sensitive values.
	 */
	public static boolean gameUpdatable = true;
	
	/**
	 * The constructor of {@link GamePanel} class that initializes the
	 * object.
	 * 
	 * @param parentWindow  A {@link windows.Window} pointer to the {@code Window} 
	 * object this {@link GamePanel} object was added to.
	 */
	public GamePanel(Window window) {
		this.window = window;
		// To draw on a buffer memory then swap to it for smooth rendering.
		setDoubleBuffered(true);
		setBackground(Color.BLACK);
		// Initialize the panelThread.
		panelThread = new Thread(this);
		// Starting the panelThread.
		panelThread.start();
		// To allow the panel to listen to device inputs.
		setFocusable(true);
	}

	/**
	 * This function updates the state of the game, by calling
	 * the update function for all components of the game.
	 */
	public void update() {
		if (window != null) {
			if (window.signInPanel != null && window.currentCardLayoutPanel == "SIGNIN") {
				window.signInPanel.update();
			}
			if (window.signUpPanel != null && window.currentCardLayoutPanel == "SIGNUP") {
				window.signUpPanel.update();
			}
			if (window.gameOverPanel != null && window.currentCardLayoutPanel == "GAMEOVER") {
				window.gameOverPanel.update();
			}
			if(!gamePaused && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ESCAPE) && window.currentCardLayoutPanel == "GAME") {
				AudioUtilities.AUDIO_RESOURCES.get("escapeSound").playOnce();
				gamePaused = true;
				window.showPanel("PAUSE");
			}
			if(gamePaused && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ESCAPE) && window.currentCardLayoutPanel == "PAUSE") {
				AudioUtilities.AUDIO_RESOURCES.get("escapeSound").playOnce();
				gamePaused = false;
				window.showPanel("GAME");
			}
			if (!gamePaused && gameUpdatable && window.currentCardLayoutPanel == "GAME") {
				for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
					if (GameObjectUtilities.dynamicObjects[i].getHealth() < 1) {
						GameObjectUtilities.dynamicObjectsStates[i] = false;
					}
				}
			}
			if (!gamePaused && gameUpdatable && window.currentCardLayoutPanel == "GAME") {
				GameObjectUtilities.updateDynamicObjects();
				MapUtilities.camera.moveInReferenceTo(new Rectangle(
					GameObjectUtilities.dynamicObjects[0].getBounds().x - MapUtilities.level.getTileSize(),
					GameObjectUtilities.dynamicObjects[0].getBounds().y - MapUtilities.level.getTileSize(),
					GameObjectUtilities.dynamicObjects[0].getBounds().width + MapUtilities.level.getTileSize() * 2,
					GameObjectUtilities.dynamicObjects[0].getBounds().height + MapUtilities.level.getTileSize() * 2
				));
			}
			if (!gamePaused && gameUpdatable && window.currentCardLayoutPanel == "GAME") {
				int monstersCount = GameObjectUtilities.dynamicObjects.length - 1;
				int deadMonstersCount = 0;
				for (DynamicObject object : GameObjectUtilities.dynamicObjects) {
					if (object instanceof Player) {
						if (object.getHealth() == 0) {
							window.showPanel("GAMEOVER");
							break;
						}
					}
					else if (object instanceof Monster) {
						if (object.getHealth() == 0) {
							deadMonstersCount += 1;
						}
					}
				}
				if (monstersCount == deadMonstersCount) {
					window.showPanel("GAMEOVER");
				}
			}
		}
	}
	
	/*
	 * This function is the painter function responsible of drawing
	 * all the components of the game.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // To clear the previous frame.
		Graphics2D painter = (Graphics2D) g;
		
		if (window != null) {
			if (gameUpdatable && window.currentCardLayoutPanel == "GAME") {
				MapUtilities.renderMap(painter);
				paintHearts(painter);
			}
		}
		
		painter.dispose();
	}
	
	/*
	 * This function runs the game loop using the panelThread. 
	 */
	@Override
	public void run() {
		// Number of nano-seconds in a one second.
		final long NANOS_PER_SECOND = 1_000_000_000;
		// The time assigned for each update.
	    final long NANOS_PER_UPDATE = NANOS_PER_SECOND / FPS;
	    long lastTime = System.nanoTime();
	    double delta = 0;
	    int updates = 0;
	    double timer = 0;
	    long currentTime = 0;
	    
	    while (panelThread != null) {
	    	currentTime = System.nanoTime();
	        delta += (currentTime - lastTime) / (double)NANOS_PER_UPDATE;
	        timer += (currentTime - lastTime);
	        lastTime = currentTime;
	        
	        while (delta >= 1) {
	            update();
	            updates = updates + 1;
	            delta = delta - 1;
	        }
	        repaint();
	        
	        try {
	        	Thread.sleep(1);
	        } catch(InterruptedException e) {
	        	e.printStackTrace();
	        }
	        
	        if (timer >= 1000000000) {
	        	//System.out.println("FPS: " + updates);
	        	updates=0;
	        	timer = 0;
	        }
	    }   
	}
	
	/**
	 * This function paints the hearts of the player.
	 * 
	 * @param painter  The painter objects from {@link java.awt.Graphics2D}
	 * that paints components on the screen. It's passed from {@link #paintComponent(Graphics)} function.
	 */
	private void paintHearts(Graphics2D painter) {
		for(DynamicObject object : GameObjectUtilities.dynamicObjects) {
			if (object instanceof Player) {
				int playerHeartCount = object.getHealth();
				for (int i = 0; i < playerHeartCount; i++) {
					painter.drawImage(Player.heart, i * 50, 0, 50, 50, null);
				}
			}
		}
	}
}