package camera;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * The {@code Perspective} class defines a rectangular
 * and movable view, which lives within the bounds of
 * a rectangular container. A {@code Perspective} object
 * can used to follow objects (called references) within
 * the parent container (i.e. a camera in a world).
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Perspective {
	/**
	 * The bounds of the container the perspective lives in.
	 */
	private Rectangle containerBounds;
	
	/**
	 * The bounds of the perspective (the view/camera).
	 */
	private Rectangle perspectiveBounds;
	
	/**
	 * This constructor constructs a {@code Perspective} object
	 * by specifying its bounds, and the bounds of the container
	 * it will live in. The {@code Perspective} object will be
	 * unable to leave the bounds of its container.
	 * 
	 * <p><b>Note:</b> If the passed perspective bounds are not within
	 * the passed container bounds, the perspective's bounds will be set
	 * to the closest they can be from within the bounds
	 * of the container.</p>
	 * 
	 * @param containerBounds  The bounds of the container the perspective will live in.
	 * @param perspectiveBounds  The bounds of the perspective.
	 */
	public Perspective(Rectangle containerBounds, Rectangle perspectiveBounds) {
		this.containerBounds = containerBounds;
		this.perspectiveBounds = perspectiveBounds;
		keepPerspectiveBounds();
	}
	
	/**
	 * This constructor constructs a {@code Perspective} object
	 * by specifying the bounds of the container it will live in,
	 * as well as the bounds of a reference object that the
	 * {@code Perspective} object's position will be set relative to.
	 * 
	 * <p>The {@code Perspective} object's bounds will be set such
	 * that it is centered around the reference object. The {@code Perspective}
	 * object must be at least as large as the reference object, and
	 * it can be larger by specifying a positive integer offset which
	 * determines how much larger the {@code Perspective} object will be
	 * than the reference.</p>
	 * 
	 * <p>Setting a reference allows the {@code Perspective} object to
	 * smoothly follow the reference if desired. This can be achieved by
	 * calling {@link #moveInReferenceTo(Rectangle)} for every time the
	 * reference object's position changes.</p>
	 * 
	 * <p><b>Note:</b> If the reference is not within the bounds of the
	 * container the {@code Perspective} object lives in, the perspective
	 * will not be able to be centered around it, but it will be set to
	 * the closest it can be from within the bounds of the container.</p>
	 * 
	 * @param containerBounds  The bounds of the container the perspective will live in.
	 * @param referenceBounds  The bounds of the reference the perspective should be set relative to.
	 * @param offset  A positive integer specifying how much larger the perspective should be
	 * than the specified reference.
	 */
	public Perspective(Rectangle containerBounds, Rectangle referenceBounds, int offset) {
		if (offset < 0) {
			offset = 0;
		}
		this.containerBounds = containerBounds;
		this.perspectiveBounds = new Rectangle(referenceBounds.x - offset, referenceBounds.y - offset,
				referenceBounds.width + 2 * offset, referenceBounds.height + 2 * offset);
		keepPerspectiveBounds();
	}
	
	/**
	 * Getter for {@link #perspectiveBounds}.
	 * @return  A rectangle, the perspective's bounds.
	 */
	public Rectangle getBounds() {
		return this.perspectiveBounds;
	}
	
	/**
	 * Getter for {@link #containerBounds}.
	 * @return  A rectangle, the container's bounds.
	 */
	public Rectangle getContainerBounds() {
		return this.containerBounds;
	}
	
	/**
	 * This function changes the reference the {@code Perspective}
	 * object is set relative to, and centers the perspective around
	 * the new reference object. A positive integer offset is specified
	 * to determine how much larger the {@code Perspective} object
	 * should be than the new reference.
	 * 
	 * <p><b>Note:</b> If the reference is not within the bounds of the
	 * container the {@code Perspective} object lives in, it will not be
	 * able to be centered around it, but it will be set to the closest it
	 * can be from within the bounds of the container.</p>
	 * 
	 * @param referenceBounds  The bounds of the new reference the perspective should be set
	 * relative to.
	 * @param offset  A positive integer specifying how much larger the perspective should be
	 * than the specified reference.
	 */
	public void setNewReference(Rectangle referenceBounds, int offset) {
		if (offset < 0) {
			offset = 0;
		}
		this.perspectiveBounds.x = referenceBounds.x - offset;
		this.perspectiveBounds.y = referenceBounds.y - offset;
		this.perspectiveBounds.width = referenceBounds.width + 2 * offset;
		this.perspectiveBounds.height = referenceBounds.height + 2 * offset;
		keepPerspectiveBounds();
	}
	
	/**
	 * This function redefines the bounds of the container
	 * the {@code Perspective} object lives in. If the
	 * {@code Perspective} object becomes outside the
	 * container after the change of bounds, the perspective
	 * will be moved to be inside the new container bounds.
	 * 
	 * @param newBounds  The new bounds of the container.
	 */
	public void setNewContainer(Rectangle newBounds) {
		this.containerBounds = newBounds;
		keepPerspectiveBounds();
	}
	
	/**
	 * This function redefines the bounds of the {@code Perspective}
	 * object. If the {@code Perspective} object was set relative
	 * to some reference earlier, that information will be lost.
	 * 
	 * <p><b>Note:</b> If the perspective's bounds become outside
	 * the bounds of the container it lives in, it will be set to
	 * the closest it can be from within the bounds of the container.</p>
	 * 
	 * @param newBounds  The new bounds of the perspective.
	 */
	public void setNewBounds(Rectangle newBounds) {
		this.perspectiveBounds = newBounds;
		keepPerspectiveBounds();
	}
	
	/**
	 * This function moves the {@code Perspective} object to a
	 * new position within the bounds of the container by specifying
	 * a new point for the {@code Perspective} object's upper-left corner.
	 * If the {@code Perspective} object was set relative to some
	 * reference earlier, that information will be lost.
	 * 
	 * <p><b>Note:</b> If the perspective's bounds become outside
	 * the bounds of the container it lives in, it will be set to
	 * the closest it can be from within the bounds of the container.</p>
	 * 
	 * @param newPosition  The position of the new upper-left corner for the perspective.
	 */
	public void moveTo(Point newPosition) {
		this.perspectiveBounds.x = newPosition.x;
		this.perspectiveBounds.y = newPosition.y;
		keepPerspectiveBounds();
	}
	
	/**
	 * This function moves the {@code Perspective} object to a
	 * new position within the bounds of the container by specifying
	 * a new point for the {@code Perspective} object's upper-left corner.
	 * If the {@code Perspective} object was set relative to some
	 * reference earlier, that information will be lost.
	 *
	 * <p>However, the transition is animated using lerping and easing
	 * instead of instantly teleporting the perspective to the destination
	 * (for more, see {@link camera.CameraUtilities}). You can also specify
	 * the animation's duration and FPS.</p>
	 * 
	 * <p><b>Note:</b> If the perspective's bounds become outside
	 * the bounds of the container it lives in at any point during
	 * the transition, the perspective will not continue its path
	 * to the new position and will remain at the bounds of the
	 * container.</p>
	 * 
	 * @param newPosition  The position of the new upper-left corner
	 * @param animationDuration  The duration, in seconds, for the transition animation
	 * to the new position.
	 * @param animationFPS  The desired FPS for the animation.
	 */
	public void moveToSmoothly(Point newPosition, double animationDuration, int animationFPS) {
		new Thread(() -> {
			// The point that the animation will start from.
			Point2D.Double startPoint = new Point2D.Double(perspectiveBounds.x, perspectiveBounds.y);
			// The amount of time each frame should take, in nanometers.
			long frameTime = (long) ((1.0 / animationFPS) * 1000000000);
			// The amount of time in each step beyond the amount of time each frame should take.
			long timeStepBuildup = 0;
			// The start time of the animation.
			long startTime = System.nanoTime();
			// This will be used to track how much time is consumed in every iteration.
			long timeTracker = startTime;
			while (true) {
				// The amount of time that has passed since the animation started, in seconds.
				double passedTime = (System.nanoTime() - startTime) / 1000000000.0;
				// The fraction of the animation duration time completed (0 = nothing, 1 = completed).
				double timeStep = Math.min(passedTime / animationDuration, 1);
				// This applies ease-in-out smoothing to the time step.
				double easedTimeStep = CameraUtilities.easeInOut(timeStep);
				// Updating the position in each step smoothly using the lerp function.
				Point nextPosition = new Point();
				nextPosition.x = (int) CameraUtilities.lerp(startPoint.x, newPosition.x, easedTimeStep);
				nextPosition.y = (int) CameraUtilities.lerp(startPoint.y, newPosition.y, easedTimeStep);
				moveTo(nextPosition);
				// When time step = 1, it means passed time = animation duration and we're done.
				if (timeStep >= 1) {
					break;
				}
				// This calculates how much time was consumed in this iteration.
				long consumedTime = System.nanoTime() - timeTracker;
				// This makes the thread sleep for the remaining time of the frame.
				if ((consumedTime + timeStepBuildup) < frameTime) {
					try {
						Thread.sleep((frameTime - (consumedTime + timeStepBuildup)) / 1000000);
						timeStepBuildup = 0;
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
				else { // In case we spent more time than the frame time, this buildup is stored.
					timeStepBuildup = (consumedTime + timeStepBuildup) - frameTime;
				}
				// Update time tracker for next step
				timeTracker = System.nanoTime();
			}
			moveTo(newPosition); // Fixes inaccuracies.
		}).start();
	}
	
	/**
	 * This function moves the {@code Perspective} object to a
	 * new position within the bounds of the container by specifying
	 * a reference's bounds for the {@code Perspective} object to
	 * transition to (which it will be centered relative to).
	 *
	 * <p>However, the transition is animated using lerping and easing
	 * instead of instantly teleporting the perspective to the destination
	 * (for more, see {@link camera.CameraUtilities}). You can also specify
	 * the animation's duration and FPS.</p>
	 * 
	 * <p><b>Note:</b> If the perspective's bounds become outside
	 * the bounds of the container it lives in at any point during
	 * the transition, the perspective will not continue its path
	 * to the new position and will remain at the bounds of the
	 * container.</p>
	 * 
	 * @param referenceBounds  The bounds of the reference the perspective should
	 * transition to.
	 * @param animationDuration  The duration, in seconds, for the transition animation
	 * to the new position.
	 * @param animationFPS  The desired FPS for the animation.
	 */
	public void moveToSmoothly(Rectangle referenceBounds, double animationDuration, int animationFPS) {
		int xOffset = this.perspectiveBounds.width - referenceBounds.width;
		int yOffset = this.perspectiveBounds.height - referenceBounds.height;
		Point newUpperLeft = new Point(referenceBounds.x - xOffset / 2, referenceBounds.y - yOffset / 2);
		moveToSmoothly(newUpperLeft, animationDuration, animationFPS);
	}
	
	/**
	 * This function, much like {@link #setNewReference(Rectangle, int)},
	 * defines a new bound that the {@code Perspective} object should be
	 * set relative to. However, this function does not center the
	 * {@code Perspective} object around the new bounds, it keeps the
	 * {@code Perspective} object unchanged as long as the new bounds do
	 * not exceed or breach the {@code Perspective} object's bounds.
	 * 
	 * <p>This function can be used to allow the {@code Perspective} object
	 * to follow some reference. For every time the bounds of a reference
	 * object change, this function can be called with the new reference
	 * bounds to update the position of the {@code Perspective} object
	 * accordingly. The perspective will only move when it needs to (to
	 * keep the reference contained inside).</p>
	 * 
	 * <p><b>Tip:</b> If the passed width & height in the reference's bounds
	 * are greater than what they actually are, the perspective will start moving
	 * earlier (before the reference exceeds the bounds). And if they are
	 * smaller than what they actually are, the perspective will start moving
	 * later (after the reference exceeds the bounds).</p>
	 * 
	 * <p><b>Note:</b> If the perspective's bounds become outside
	 * the bounds of the container it lives in, it will be set to
	 * the closest it can be from within the bounds of the container.</p>
	 * 
	 * @param referenceBounds  The updated bounds of the reference the perspective should follow.
	 */
	public void moveInReferenceTo(Rectangle referenceBounds) {
		ArrayList<OutOfBoundsAlerts> flags = CameraUtilities.isContained(referenceBounds, perspectiveBounds);
		CameraUtilities.fixBoundBreaches(referenceBounds, perspectiveBounds, flags, true);
		keepPerspectiveBounds();
	}
	
	/**
	 * This function increases (extends) the size of the {@code Perspective}
	 * object but keeps it centered around its original position.
	 * 
	 * <p><b>Note:</b> If the perspective's bounds become outside
	 * the bounds of the container it lives in, it will be set to
	 * the closest it can be from within the bounds of the container.</p>
	 * 
	 * @param extendOffset  A positive integer specifying how much the perspective should extend.
	 */
	public void extendBy(int extendOffset) {
		if (extendOffset < 0) {
			extendOffset = 0;
		}
		this.perspectiveBounds.x = this.perspectiveBounds.x - extendOffset;
		this.perspectiveBounds.y = this.perspectiveBounds.y - extendOffset;
		this.perspectiveBounds.width = this.perspectiveBounds.width + 2 * extendOffset;
		this.perspectiveBounds.height = this.perspectiveBounds.height + 2 * extendOffset;
		keepPerspectiveBounds();
	}
	
	/**
	 * This function makes sure the {@code Perspective} object 
	 * stays within the bounds of its container by returning it
	 * to the container if it breaches the container's bounds.
	 */
	private void keepPerspectiveBounds() {
		ArrayList<OutOfBoundsAlerts> flags = CameraUtilities.isContained(perspectiveBounds, containerBounds);
		CameraUtilities.fixBoundBreaches(perspectiveBounds, containerBounds, flags, false);
	}
	
}