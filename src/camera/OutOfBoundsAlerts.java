package camera;

/**
 * The {@code OutOfBoundsAlerts} enum class
 * defines the simple cases in which a rectangle
 * could breah the bounds of another rectangle.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public enum OutOfBoundsAlerts {
	/**
	 * This means a breach of bounds happened on the left side
	 * of some rectangular object's bounds.
	 */
	LEFT_BREACH,
	/**
	 * This means a breach of bounds happened on the right side
	 * of some rectangular object's bounds.
	 */
	RIGHT_BREACH,
	/**
	 * This means a breach of bounds happened on the upper side
	 * of some rectangular object's bounds.
	 */
	UPPER_BREACH,
	/**
	 * This means a breach of bounds happened on the lower side
	 * of some rectangular object's bounds.
	 */
	LOWER_BREACH,
}