package gameObjects;

import java.awt.Point;
import java.util.ArrayList;

import audio.AudioUtilities;
import mapControls.MapUtilities;
import physics.AnimationMechanic;
import physics.PathFinder;
import physics.PhysicsUtilities;
import physics.PathFinder.Node;
import physics.WanderMechanic;

/**
 * The {@code Player} class initializes a enemy
 * object that attacks the player.
 * 
 * @see Player
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Monster extends DynamicObject {
	/**
	 * The speed of the player.
	 */
	private int speed;
	
	/**
	 * This is responsible of animating the movement.
	 */
	private AnimationMechanic animator;
	
	/**
	 * This is responsible for the wandering animation.
	 */
	private WanderMechanic wanderer;
	
	/**
	 * This variable represents if the object is wandering.
	 */
	private boolean wanderingFlag = true;
	
	/**
	 * This variable represents if the object needs a new motion vector.
	 */
	private boolean needNewMotionVector = false;
	
	/**
	 * The motion vector of the object.
	 */
	private Point motionVector = new Point(0,0);
	
	/**
	 * This tracks how much the object has moved in the direction of {@link #motionVector}.
	 */
	private Point motionTracker = new Point(0,0);
	
	/**
	 * This tracks how much the object needs to move in the direction of {@link #motionVector}.
	 */
	private Point motionAmount = new Point(0,0);
	
	/**
	 * The motion destination the object needs to reach (point).
	 */
	private Point motionDestination = new Point(0,0);
	
	/**
	 * The view range of the object (in tiles).
	 */
	private int viewRange = 5;
	
	/**
	 * The number of {@link #update()} calls to wait between hits.
	 */
	private final int HIT_WAIT = 35;
	
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
	 * This constructor constructs the {@code Monster} object
	 * and initializes its attributes.
	 * 
	 * @param layerID  The ID of the layer the object belongs to.
	 * @param tileSetName  The name of the tileset the object uses.
	 * @param speed  The speed of the object.
	 * @param monsterName  A unique name that is given to every object.
	 */
	public Monster(int layerID, String tileSetName, int speed, String monsterName) {
		super(layerID, tileSetName, monsterName);
		this.speed = speed;
		this.animator = new AnimationMechanic(10, 25, tileSet.getFirstID());
		this.wanderer = new WanderMechanic(120, 32, 32, this.speed);
	}

	@Override
	public void update() {
		animator.resetPositionFlags();
		MapUtilities.level.getLevelLayers()[layerID].setLayerDataElement(rowPosition, columnPosition, -1);
		updateHit();
		if (wanderingFlag) {
			if (PathFinder.heuristic(
					new Node(
						GameObjectUtilities.dynamicObjects[0].rowPosition, 
						GameObjectUtilities.dynamicObjects[0].columnPosition
					), 
					new Node(rowPosition, columnPosition)
				) < viewRange) {
				wanderingFlag = false;
				needNewMotionVector = true;
				return;
			}
			Point motion = wanderer.wander(new Point(getBounds().x, getBounds().y));
			updatePosition(motion.x, motion.y);
			if (motion.x < 0) {
				animator.leftDirectionFlag = true;
			}
			if (motion.x > 0) {
				animator.rightDirectionFlag = true;
			}
			if (motion.y < 0) {
				animator.upDirectionFlag = true;
			}
			if (motion.y > 0) {
				animator.downDirectionFlag = true;
			}
		}
		else {
			if (PathFinder.heuristic(
					new Node(
						GameObjectUtilities.dynamicObjects[0].rowPosition, 
						GameObjectUtilities.dynamicObjects[0].columnPosition
					), 
					new Node(rowPosition, columnPosition)
				) >= viewRange) {
				wanderingFlag = true;
				needNewMotionVector = false;
				return;
			}
			Point motion = followTarget(
				GameObjectUtilities.dynamicObjects[0].rowPosition,
				GameObjectUtilities.dynamicObjects[0].columnPosition
			);
			updatePosition(motion.x, motion.y);
			if (motion.x < 0) {
				animator.leftDirectionFlag = true;
			}
			if (motion.x > 0) {
				animator.rightDirectionFlag = true;
			}
			if (motion.y < 0) {
				animator.upDirectionFlag = true;
			}
			if (motion.y > 0) {
				animator.downDirectionFlag = true;
			}
		}
		currentTileValue = animator.getNextTileIndex(currentTileValue);
		MapUtilities.level.getLevelLayers()[layerID].setLayerDataElement(rowPosition, columnPosition, currentTileValue);
	}
	
	/**
	 * This function follows a target specified by row and column.
	 * 
	 * @param row  The row of the target.
	 * @param column  The column of the target.
	 * 
	 * @return  The direction the object needs to move in.
	 */
	private Point followTarget(int row, int column) {
		if (needNewMotionVector) {
			ArrayList<Node> path = PathFinder.findPath(
				new PathFinder.Node(rowPosition, columnPosition),
				new PathFinder.Node(row, column),
				GameObjectUtilities.dynamicObjects[0].getTileSet()
			);
			if (path.size() < 2) {
				needNewMotionVector = false;
				wanderingFlag = true;
				return new Point(0,0);
			}
			motionDestination = new Point(path.get(1).y * drawSize, path.get(1).x * drawSize);
			motionVector = PhysicsUtilities.calculateMotionVector(
				new Point(getBounds().x, getBounds().y),
				motionDestination,
				speed
			);
			motionTracker = new Point(0,0);
			motionAmount.x = Math.abs(motionDestination.x - getBounds().x);
			motionAmount.y = Math.abs(motionDestination.y - getBounds().y);
			needNewMotionVector = false;
		}
		if ((Math.abs(motionTracker.x) < motionAmount.x) && (Math.abs(motionTracker.y) < motionAmount.y)) {
			motionTracker.x += motionVector.x;
			motionTracker.y += motionVector.y;
			return new Point(motionVector.x, motionVector.y);
		}
		if (Math.abs(motionTracker.x) < motionAmount.x) {
			motionTracker.x += motionVector.x;
			return new Point(motionVector.x, 0);
		}
		if (Math.abs(motionTracker.y) < motionAmount.y) {
			motionTracker.y += motionVector.y;
			return new Point(0, motionVector.y);
		}
		needNewMotionVector = true;
		return new Point(0, 0);
	}

	/**
	 * This function updates the hit state of the monster.
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
		if (getBounds().intersects(GameObjectUtilities.dynamicObjects[0].getBounds())) {
			hitWaitFlag = true;
			AudioUtilities.AUDIO_RESOURCES.get("hitPunch").playOnce();
			int newHealth = GameObjectUtilities.dynamicObjects[0].getHealth() - 1;
			GameObjectUtilities.dynamicObjects[0].setHealth(newHealth);
		}
	}

}
