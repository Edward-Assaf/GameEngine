package camera;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The {@code CameraUtilities} class offers helper defenitions,
 * used to manage camera, perspectives & coordinates.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class CameraUtilities {
	/**
	 * This function converts a point from some world's
	 * coordinates system to another world's coordinates system.
	 * 
	 * <p><b>Note:</b> This function works with and outputs
	 * integer values only.</p>
	 * 
	 * @param world1Bounds  The bounds of world 1 (the world to convert from)
	 * @param world2Bounds  The bounds of world 2 (the world to convert to)
	 * @param world1Point  The point to convert
	 * @return  A point, the converted point from world 1 to world 2
	 * 
	 * @see #convertBound(Rectangle, Rectangle, Rectangle)
	 */
	public static Point convertPoint(Rectangle world1Bounds, Rectangle world2Bounds, Point world1Point) {
		double widthRatio = ((double) (world1Point.x - world1Bounds.x)) / world1Bounds.width;
		double heightRatio = ((double) (world1Point.y - world1Bounds.y)) / world1Bounds.height;
		
		int world2X = (int) Math.round((world2Bounds.x + (widthRatio * world2Bounds.width)));
		int world2Y = (int) Math.round((world2Bounds.y + (heightRatio * world2Bounds.height)));
		
		return new Point(world2X, world2Y);
	}
	
	/**
	 * This function converts a rectangular bound from some world's
	 * coordinates system to another world's coordinates system.
	 * 
	 * <p><b>Note:</b> This function works with and outputs
	 * integer values only.</p>
	 * 
	 * @param world1Bounds  The bounds of world 1 (the world to convert from)
	 * @param world2Bounds  The bounds of world 2 (the world to convert to)
	 * @param bound  The bound to convert
	 * @return  A Rectangle bound, the converted bound from world 1 to world 2
	 * 
	 * @see #convertPoint(Rectangle, Rectangle, Point)
	 */
	public static Rectangle convertBound(Rectangle world1Bounds, Rectangle world2Bounds, Rectangle bound) {
		Point world1Corner = new Point(bound.x, bound.y);
		Point world2Corner = convertPoint(world1Bounds, world2Bounds, world1Corner);
		
		double widthRatio = ((double) bound.width) / world1Bounds.width;
		double heightRatio = ((double) bound.height) / world1Bounds.height;
		
		int world2Width = (int) Math.round((widthRatio * world2Bounds.width));
		int world2Height = (int) Math.round((heightRatio * world2Bounds.height));
		
		return new Rectangle(world2Corner.x, world2Corner.y, world2Width, world2Height);
	}
	
	/**
	 * This function checks if an inner rectangle bound is fully
	 * contained within an outer rectangle bound. It returns an
	 * {@code ArrayList} of {@link OutOfBoundsAlerts} flags that
	 * determine the sides the breach of bounds happened along. If
	 * no breach of bounds happened, the returned list would be empty.
	 * 
	 * @param innerBound  The rectangle bound to be fully contained by the outer rectangle bound.
	 * @param outerBound  The rectangle bound to surround the inner rectangle bound.
	 * @return  An {@code ArrayList}, the {@code OutOfBoundsAlerts} flags if any occured.
	 * 
	 * @see #fixBoundBreaches(Rectangle, Rectangle, ArrayList, boolean)
	 */
	public static ArrayList<OutOfBoundsAlerts> isContained(Rectangle innerBound, Rectangle outerBound) {
		ArrayList<OutOfBoundsAlerts> alerts = new ArrayList<>();
		if (innerBound.x < outerBound.x) {
			alerts.add(OutOfBoundsAlerts.LEFT_BREACH);
		}
		if (innerBound.y < outerBound.y) {
			alerts.add(OutOfBoundsAlerts.UPPER_BREACH);
		}
		if (innerBound.x + innerBound.width > outerBound.x + outerBound.width) {
			alerts.add(OutOfBoundsAlerts.RIGHT_BREACH);
		}
		if (innerBound.y + innerBound.height > outerBound.y + outerBound.height) {
			alerts.add(OutOfBoundsAlerts.LOWER_BREACH);
		}
		return alerts;
	}
	
	/**
	 * This function takes an inner rectangle bound and an
	 * outer rectangle bound, where the inner bound is not
	 * fully contained in the outer bound (a breach of bounds
	 * has occured). 
	 * 
	 * <p>It fixes the breach by applying the
	 * minimum possible change to one of the bounds so that
	 * the inner bound becomes fully contained in the outer
	 * bound. It follows the flags specified in the
	 * {@link OutOfBoundsAlerts} {@code ArrayList}.</p>
	 * 
	 * <p>You can modify either bounds. If you wish the inner
	 * bound to be changed to fit inside the outer bound, set
	 * {@code changeFlag} to false. And if you wish the outer
	 * bound to be changed to contain the inner bound, set
	 * {@code changeFlag} to true.</p>
	 * 
	 * @param innerBound  The rectangle bound that must be fully contained
	 * @param outerBound  The rectangle bound that must surround the inner rectangle bound.
	 * @param breachAlerts  The {@code ArrayList} of breach flags to follow.
	 * @param changeFlag  The value to determine which bound to change.
	 * True for outer, false for inner.
	 * 
	 * @see #isContained(Rectangle, Rectangle)
	 */
	public static void fixBoundBreaches(Rectangle innerBound, Rectangle outerBound,
			ArrayList<OutOfBoundsAlerts> breachAlerts, boolean changeFlag) {
		if (changeFlag) fixBoundBreaches_moveOuter(innerBound, outerBound, breachAlerts);
		else fixBoundBreaches_moveInner(innerBound, outerBound, breachAlerts);
	}
	
	/**
	 * Linear Interpolation
	 * 
	 * <p><b>Formal Definition:</b> This is a mathematical function
	 * that allows the calculation of a value between two other values
	 * (called {@code start} and {@code end} values). By providing a
	 * {@code step} value ranging from 0.0 to 1.0, you can determine
	 * what value between {@code start} and {@code end} will be returned.</p>
	 * 
	 * <p>For example:</p>
	 * <p><ul><li>When {@code step = 0}, the returned value is equal to {@code start}.
	 * <li>When {@code step = 0.6}, the returned value is 60% of the way between
	 * {@code start} and {@code end}.
	 * <li>When {@code step = 1}, the returned value is equal to {@code end}.</ul></p>
	 * 
	 * <p><b>Technical Definition:</b> This is a mathematical transition
	 * function that can be used to simulate the smooth transition from
	 * one value ({@code start}) to another value ({@code end}). By
	 * iteratively calling this function with gradually increasing values
	 * for {@code step} from 0.0 to 1.0, the returned value will gradually
	 * transition from {@code start} to {@code end}.</p>
	 * 
	 * <p>With animations, the {@code step} value could represent how much
	 * of the animation has been completed, which can be, for example, the
	 * percentage of completed frames from the total number of frames in the
	 * animation, or the percentage of passed time from the total duration
	 * time of the animation. In this scenario, a {@code step} value like
	 * 0.7 would mean 70% of animation frames were rendered so far, or 70%
	 * of the animation duration time had passed so far.</p>
	 * 
	 * <p>You can use some methods to reduce the linear feeling of this
	 * transition, like the easing method {@link #easeInOut(double)}. Such
	 * methods, when applied to {@code step} before passing it to this
	 * function, counter the linearity of the change when {@code step}
	 * is gradually increased, giving the transition a more realistic feel.</p>
	 * 
	 * @param start  the start value of the transition.
	 * @param end  the target value of the transition.
	 * @param step  the step of the transition.
	 * @return  A double, the value between start and end at the current
	 * transition step.
	 */
	public static double lerp(double start, double end, double step) {
		return start + step * (end - start);
	}
	
	/**
	 * This function can be used with any mathematical transition
	 * function (like {@link #lerp(double, double, double)}) to
	 * give the transition an 'ease-in-out' animation. It should
	 * be applied to the step before passing it to the transition
	 * function.
	 * 
	 * @param step  The step value to apply ease-in-out animation on.
	 * @return  A double, the eased-in-out step value.
	 */
	public static double easeInOut(double step) {
		return step * step * (3 - 2 * step);
	}
	
	/**
	 * Special case of {@link #fixBoundBreaches(Rectangle, Rectangle, ArrayList, boolean)}
	 * when {@code changeFlag} is set to true.
	 */
	private static void fixBoundBreaches_moveOuter(Rectangle innerBound, Rectangle outerBound,
			ArrayList<OutOfBoundsAlerts> breachAlerts) {
		for (OutOfBoundsAlerts flag : breachAlerts) {
			if (flag == OutOfBoundsAlerts.LEFT_BREACH) {
				outerBound.x = innerBound.x;
			}
			else if (flag == OutOfBoundsAlerts.UPPER_BREACH) {
				outerBound.y = innerBound.y;
			}
			else if (flag == OutOfBoundsAlerts.RIGHT_BREACH) {
				outerBound.x += (innerBound.x + innerBound.width) - (outerBound.x + outerBound.width);
			}
			else if (flag == OutOfBoundsAlerts.LOWER_BREACH) {
				outerBound.y += (innerBound.y + innerBound.height) - (outerBound.y + outerBound.height);
			}
		}
	}
	
	/**
	 * Special case of {@link #fixBoundBreaches(Rectangle, Rectangle, ArrayList, boolean)}
	 * when {@code changeFlag} is set to false.
	 */
	private static void fixBoundBreaches_moveInner(Rectangle innerBound, Rectangle outerBound,
			ArrayList<OutOfBoundsAlerts> breachAlerts) {
		for (OutOfBoundsAlerts flag : breachAlerts) {
			if (flag == OutOfBoundsAlerts.LEFT_BREACH) {
				innerBound.x = outerBound.x;
			}
			else if (flag == OutOfBoundsAlerts.UPPER_BREACH) {
				innerBound.y = outerBound.y;
			}
			else if (flag == OutOfBoundsAlerts.RIGHT_BREACH) {
				innerBound.x -= (innerBound.x + innerBound.width) - (outerBound.x + outerBound.width);
			}
			else if (flag == OutOfBoundsAlerts.LOWER_BREACH) {
				innerBound.y -= (innerBound.y + innerBound.height) - (outerBound.y + outerBound.height);
			}
		}
	}
	
}