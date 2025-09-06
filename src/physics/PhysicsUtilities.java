package physics;

import java.awt.Point;

/**
 * The {@code PhysicsUtilities} class offers
 * helper definitions for game physics.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class PhysicsUtilities {
	/**
	 * This function calculates the motion vector between two points.
	 * 
	 * @param p1  The first point to move from.
	 * @param p2  The second point to move to.
	 * @param scalar  The speed to move by.
	 * 
	 * @return The motion vector.
	 */
	public static Point calculateMotionVector(Point p1, Point p2, int scalar) {
		double dx = p2.x - p1.x;
		double dy = p2.y - p1.y;
		double distance = Math.sqrt(dx * dx + dy * dy);
		double dirX = dx / distance;
		double dirY = dy / distance;
		dirX *= scalar;
		dirY *= scalar;
		if (dirX < 0) {
			dirX = -Math.ceil(Math.abs(dirX));
		}
		else {
			dirX = Math.ceil(dirX);
		}
		if (dirY < 0) {
			dirY = -Math.ceil(Math.abs(dirY));
		}
		else {
			dirY = Math.ceil(dirY);
		}
		return new Point((int)dirX, (int)dirY);
	}
	
}
