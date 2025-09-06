package windows;

/**
 * The {@code Scalable} interface implements
 * the necessary functionalities a window or
 * panel needs to be scalable (i.e. subject to
 * full-screen-toggle action scaling).
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public interface Scalable {
	/**
	 * This function should handle scaling up the font
	 * sizes of individual components by {@code Window.RATIO}.
	 */
	public abstract void scaleUpFontSizes();
	
	/**
	 * This function should handle scaling down the font
	 * sizes of individual components to original size.
	 */
	public abstract void scaleDownFontSizes();
	
	/**
	 * This function should handle the scaling operation of
	 * individual components in the window or panel.
	 */
	public abstract void scaleComponents();
}