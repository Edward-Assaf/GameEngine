	package gameObjects;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import audio.AudioUtilities;
import deviceInputs.KeyHandler;
import mapControls.MapUtilities;
import media.MediaResource;
import physics.AnimationMechanic;

/**
 * The {@code Player} class initializes a player
 * that is the main character of the game.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Player extends DynamicObject {
	/**
	 * The speed of the player.
	 */
	private int speed;
	
	/**
	 * This is responsible of animating the movement.
	 */
	private AnimationMechanic animator;
	
	/**
	 * This stores the heart icon that can be used to represent health.
	 */
	public static BufferedImage heart;
	
	/**
	 * The number of {@link #update()} calls to wait between hits.
	 */
	private final int HIT_WAIT = 55;
	
	/**
	 * The current number of {@link #update()} calls waited.
	 */
	private int hitWaitCounter = 0;
	
	/**
	 * This variable determines the state of the player,
	 * true for attacking state and false for idle state.
	 */
	private boolean hitWaitFlag = false;
	
	/**
	 * This constructor constructs the {@code Player} object
	 * and initializes its attributes.
	 * 
	 * @param layerID  The ID of the layer the object belongs to.
	 * @param tileSetName  The name of the tileset the object uses.
	 * @param speed  The speed of the object.
	 * @param playerName  A unique name that is given to every object.
	 */
	public Player(int layerID, String tileSetName, int speed, String playerName) {
		super(layerID, tileSetName, playerName);
		this.speed = speed;
		this.animator = new AnimationMechanic(10, 25, tileSet.getFirstID());
		if (heart == null) {
			try {
				heart = ImageIO.read(new File(new MediaResource("heart.png").getResourceAbsolutePath()));
			} catch (IOException e) {
				System.out.println("Heart png image failed to load");
			}
		}
	}

	@Override
	public void update() {
		animator.resetPositionFlags();
		MapUtilities.level.getLevelLayers()[layerID].setLayerDataElement(rowPosition, columnPosition, -1);
		keyBoardInputUpdate();
		updateHit();
		currentTileValue = animator.getNextTileIndex(currentTileValue);
		MapUtilities.level.getLevelLayers()[layerID].setLayerDataElement(rowPosition, columnPosition, currentTileValue);
	}
	
	/**
	 * This function updates the motion state of the player.
	 */
	private void keyBoardInputUpdate() {
		if (KeyHandler.isKeyPressed(KeyEvent.VK_W) && KeyHandler.isKeyPressed(KeyEvent.VK_D)) {
			updatePosition(speed, -speed);
			animator.upDirectionFlag = true;
			animator.rightDirectionFlag = true;
			return;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_W) && KeyHandler.isKeyPressed(KeyEvent.VK_A)) {
			updatePosition(-speed, -speed);
			animator.upDirectionFlag = true;
			animator.leftDirectionFlag = true;
			return;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_S) && KeyHandler.isKeyPressed(KeyEvent.VK_D)) {
			updatePosition(speed, speed);
			animator.downDirectionFlag = true;
			animator.rightDirectionFlag = true;
			return;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_S) && KeyHandler.isKeyPressed(KeyEvent.VK_A)) {
			updatePosition(-speed, speed);
			animator.downDirectionFlag = true;
			animator.leftDirectionFlag = true;
			return;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_W)) {
			updatePosition(0, -speed);
			animator.upDirectionFlag = true;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_S)) { 
			updatePosition(0, speed);
			animator.downDirectionFlag = true;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_D)) { 
			updatePosition(speed, 0);
			animator.rightDirectionFlag = true;
		}
		else if (KeyHandler.isKeyPressed(KeyEvent.VK_A)) { 
			updatePosition(-speed, 0);
			animator.leftDirectionFlag = true;
		}
	}
	
	/**
	 * This function updates the hit state of the player.
	 */
	public void updateHit() {
		if (hitWaitFlag) {
			if (hitWaitCounter == HIT_WAIT) {
				hitWaitFlag = false;
				hitWaitCounter = 0;
			}
			hitWaitCounter++;
			return;
		}
		if (!KeyHandler.isKeyPressedOnce(KeyEvent.VK_L)) {
			return;
		}
		hitWaitFlag = true;
		for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
			if (equals(GameObjectUtilities.dynamicObjects[i])) {
				continue;
			}
			if (!GameObjectUtilities.dynamicObjectsStates[i]) {
				continue;
			}
			if (getBounds().intersects(GameObjectUtilities.dynamicObjects[i].getBounds())) {
				AudioUtilities.AUDIO_RESOURCES.get("hitPunch").playOnce();
				int newHealth = GameObjectUtilities.dynamicObjects[i].getHealth() - 1;
				GameObjectUtilities.dynamicObjects[i].setHealth(newHealth);
			}
			else {
				AudioUtilities.AUDIO_RESOURCES.get("missPunch").playOnce();
			}
		}
	}
	
}
