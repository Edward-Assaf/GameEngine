package windowComponents;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSlider;

import camera.CameraUtilities;

/**
 * The {@code SliderBar} class defines the
 * slider window component. It implements
 * {@link javax.swing.JSlider} functionality.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SliderBar extends JSlider {
	/**
	 * The inner interface {@code CustomPressedStateImplementer}
	 * allows for the implementation of customized and user-defined
	 * functions that will be called inside the {@code SliderBar}
	 * object's {@code mousePressed} function, in the default listener
	 * provided in its constructor.
	 * 
	 * <p>The default mouse listener provided overrides {@code mousePressed}
	 * to allow the slider component's thumb to move to where
	 * the mouse cursor is whenever it is pressed. And the purpose
	 * of this interface is to allow the customizability in what
	 * happends when {@code mousePressed} is triggered (by optionally
	 * adding function implementations to be called when {@code mousePressed}
	 * is triggered).</p>
	 * 
	 * <p>Currently, this interface allows the customizability of
	 * one function, {@code ratioImplementer}. This function takes
	 * the ratio of the slider (0.6 for example means the thumb is
	 * 60% of the way between the slider's min and max value), and
	 * does something with it.</p>
	 * 
	 * <p>You can set your user-defined implementation of this interface's
	 * {@code ratioImplementer} function using {@code SliderBar}'s
	 * {@code setCustomImplementer} function which takes an instance
	 * of this interface with a custom override of {@code ratioImplementer}.
	 * The custom implementation will be automatically called whenever
	 * {@code mousePressed} is triggered and the ratio will be
	 * automatically passed to the custom implementation of {@code ratioImplementer}.</p>
	 */
	public static interface CustomPressedStateImplementer {
		abstract void ratioImplementer(double ratio);
	}
	
	/**
	 * The instance of the {@code CustomPressedStateImplementer}
	 * inner interface which will store user-defined custom
	 * implementations of this interface's functions, to be
	 * called within the {@code SliderBar}'s {@code mousePressed}
	 * function.
	 * 
	 * <p>See above this interface's documentation.</p>
	 */
	private CustomPressedStateImplementer customImplementer;
	
	/**
	 * This constructor constructs a slider window component
	 * which can have a minimum and maximum value, as well as
	 * an initial value for its thumb. It also adds a default
	 * mouse listener.
	 * 
	 * <p>The default mouse listener provided overrides {@code mousePressed}
	 * to allow the slider component's thumb to move to where
	 * the mouse cursor is whenever it is pressed.</p>
	 * 
	 * @param min  The minimum value for the slider.
	 * @param max  The maximum value for the slider.
	 * @param value  The initial value for the slider.
	 */
	public SliderBar(int min, int max, int value) {
		super(min, max, value);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				double ratio = e.getX() / (double) (getBounds().width);
				if (customImplementer != null) {
					customImplementer.ratioImplementer(ratio);
				}
				setValue((int) CameraUtilities.lerp(getMinimum(), getMaximum(), ratio));
			}
		});
	}
	
	/**
	 * This function allows you to add a customized and user-defined
	 * implementation of {@code CustomPressedStateImplementer}'s
	 * functions. These functions will be called within this object's
	 * default mouse listener, when {@code mousePressed} is triggered.
	 * 
	 * <p>The default mouse listener provided for this class
	 * overrides {@code mousePressed} to allow the slider component's
	 * thumb to move to where the mouse cursor is whenever it
	 * is pressed.</p>
	 * 
	 * @param customImplementer  The instance of the interface which implements its functions.
	 */
	public void setCustomImplementer(CustomPressedStateImplementer customImplementer) {
		this.customImplementer = customImplementer;
	}
	
}
