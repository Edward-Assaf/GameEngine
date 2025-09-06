package windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;


import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The {@code WindowUtilities} class offers helper definitions,
 * used to manage windows.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class WindowUtilities {
	/**
	 * This function takes a {@code Component} object, and a
	 * {@code Rectangle} object representing its original bounds
	 * in the minimized state.
	 * <p>It scales the component up or down, depending on the value of
	 * the variable {@code isFullScreen}.</p>
	 * 
	 * @param component  The component to be scaled.
	 * @param originalBounds  The component's original bounds.
	 */
	public static void scale(Component component, Rectangle originalBounds) {
		if (Window.isFullScreen) {
			int newX = (int)(originalBounds.x * Window.RATIO);
			int newY = (int)(originalBounds.y * Window.RATIO);
			int newWidth = (int)(originalBounds.width * Window.RATIO);
			int newHeight = (int)(originalBounds.height * Window.RATIO);
			component.setBounds(newX, newY, newWidth, newHeight);
		}
		else {
			component.setBounds(originalBounds);
		}
	}
	
	/**
	 * This function handles the scaling operation of a
	 * {@link Scalable} component by calling the functions
	 * it has implemented from the {@code Scalable} interface.
	 * This function is typically called whenever a change
	 * in the component's full-screen state occurs.
	 * 
	 * @param component  The scalable component to scale.
	 * 
	 * @see {@link deviceInputs.FullScreenAction}
	 */
	public static void scale(Scalable component) {
		if (Window.isFullScreen) {
			component.scaleUpFontSizes();
		}
		else {
			component.scaleDownFontSizes();
		}
		component.scaleComponents();
	}
	
	/*
	 * --------------------------------
	 *  To be completed in version 2.0
	 * --------------------------------
	 */
	public static void fadeIn(JPanel panel, int fadeSeconds) {
		Timer timer = new Timer(40, null);
		final int steps = fadeSeconds * 25;
		final float[] alpha = {0f};
		final float delta = 1f / steps;
		
		timer.addActionListener(e -> {
			alpha[0] += delta;
			if (alpha[0] >= 1f) {
				alpha[0] = 1f;
				timer.stop();
			}
			Color bag = panel.getBackground();
			panel.setBackground(new Color(bag.getRed(), bag.getGreen(), bag.getBlue(), alpha[0]));
		});
		timer.start();
	}
	
	/*
	 * --------------------------------
	 *  To be completed in version 2.0
	 * --------------------------------
	 */
	public static void fadeOut(JPanel panel, int fadeSeconds) {
		Timer timer = new Timer(40, null);
		final int steps = fadeSeconds * 25;
		final float[] alpha = {1f};
		final float delta = 1f / steps;
		
		timer.addActionListener(e -> {
			alpha[0] -= delta;
			if (alpha[0] <= 0f) {
				alpha[0] = 0f;
				timer.stop();
			}
			Color bag = panel.getBackground();
			System.out.println(alpha[0]);
			panel.setBackground(new Color(bag.getRed()/255f, bag.getGreen()/255f, bag.getBlue()/255f, alpha[0]));
		});
		timer.start();
	}
}
