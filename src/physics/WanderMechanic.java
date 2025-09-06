package physics;

import java.awt.Point;
import java.util.Random;

/**
 * The {@code WanderMechanic} class
 * handles movement wandering mechanics.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class WanderMechanic {
	/**
	 * The range in tiles to wander in horizontally.
	 */
	private final int WANDER_RANGE_X;
	
	/**
	 * The range in tiles to wander in vertically.
	 */
	private final int WANDER_RANGE_Y;
	
	/**
	 * The number of update calls to wait before wandering.
	 */
	private final int WANDER_PAUSE;
	
	/**
	 * This variable determines if the object is wandering.
	 */
	public boolean wanderPauseFlag = false;
	
	/**
	 * The actual value to wander horizontally.
	 */
	private int wanderX;
	
	/**
	 * The actual value to wander vertically.
	 */
	private int wanderY;
	
	/**
	 * This variable checks if new wander values are needed.
	 */
	public boolean newWanderNeededFlag = true;
	
	/**
	 * The current number of update calls waited.
	 */
	private int wanderPauseCounter = 0;
	
	/**
	 * The original position of the object before wandering.
	 */
	private Point originalPosition = new Point(0,0);
	
	/**
	 * The motion vector that determines the wander direction.
	 */
	private Point motionVector = new Point(0,0);
	
	/**
	 * This tracks how much the object has moved in the direction of {@link #motionVector}.
	 */
	private Point wanderTracker = new Point(0,0);
	
	/**
	 * The speed at which the object wanders.
	 */
	private int wanderRate;
	
	/**
	 * The random generator of wander points.
	 */
	private Random rand;
	
	/**
	 * This constructor constructs the {@code WanderMechanic} object.
	 * 
	 * @param wanderPause  The number of update calls to wait before wandering.
	 * @param rangeX  The range in tiles to wander in horizontally.
	 * @param rangeY  The range in tiles to wander in vertically.
	 * @param wanderRate  The speed at which the object wanders.
	 */
	public WanderMechanic(int wanderPause, int rangeX, int rangeY, int wanderRate) {
		WANDER_PAUSE = wanderPause;
		WANDER_RANGE_X = rangeX;
		WANDER_RANGE_Y = rangeY;
		this.wanderRate = wanderRate;
		this.rand = new Random();
	}
	
	/**
	 * This function is responsible of the wandering process.
	 * 
	 * @param currentPosition  The current position of the object
	 * 
	 * @return  A point, the motion vector to use in wandering.
	 */
	public Point wander(Point currentPosition) {
		if (wanderPauseFlag) {
			if (wanderPauseCounter == WANDER_PAUSE) {
				wanderPauseFlag = false;
				newWanderNeededFlag = true;
				wanderPauseCounter = 0;
			}
			wanderPauseCounter++;
			return new Point(0, 0);
		}
		if (newWanderNeededFlag) {
			originalPosition = currentPosition;
			wanderX = rand.nextInt(-WANDER_RANGE_X, WANDER_RANGE_X);
			wanderY = rand.nextInt(-WANDER_RANGE_Y, WANDER_RANGE_Y);
			motionVector = PhysicsUtilities.calculateMotionVector(
				originalPosition,
				new Point(originalPosition.x + wanderX, originalPosition.y + wanderY),
				wanderRate
			);
			wanderTracker = new Point(0,0);
			newWanderNeededFlag = false;
		}
		if ((Math.abs(wanderTracker.x) < Math.abs(wanderX)) && (Math.abs(wanderTracker.y) < Math.abs(wanderY))) {
			wanderTracker.x += motionVector.x;
			wanderTracker.y += motionVector.y;
			return new Point(motionVector.x, motionVector.y);
		}
		if (Math.abs(wanderTracker.x) < Math.abs(wanderX)) {
			wanderTracker.x += motionVector.x;
			return new Point(motionVector.x, 0);
		}
		if (Math.abs(wanderTracker.y) < Math.abs(wanderY)) {
			wanderTracker.y += motionVector.y;
			return new Point(0, motionVector.y);
		}
		wanderPauseFlag = true;
		return new Point(0, 0);
	}
	
}
