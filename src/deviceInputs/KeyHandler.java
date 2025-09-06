package deviceInputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import windows.Window;

/**
 * The {@code KeyHandler} class listens for key inputs,
 * and is used with a any component object that has
 * {@code setFocusable} set to true.
 * 
 * <p>It implements {@link java.awt.event.KeyListener} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class KeyHandler implements KeyListener {
	/**
	 * This is responsible for performing full-screen toggle whenever
	 * F11 is pressed to whatever window this key listener is added to.
	 */
	public FullScreenAction fullScreen;
	
	/**
	 * This constructor constructs a {@code KeyHandler} object and
	 * initializes {@link #fullScreen}.
	 * @param window
	 */
	public KeyHandler(Window window) {
		fullScreen = new FullScreenAction(window);
	}
	
	/**
	 * The {@link Set} of keys that are currently pressed.
	 */
	private static Set<Integer> pressedKeys = new HashSet<>();
	
	/**
	 * The {@link Set} of pressed keys that have been consumed once.
	 */
	private static Set<Integer> consumedKeys = new HashSet<>();
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_F11) {
			fullScreen.performAction();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
		consumedKeys.remove(e.getKeyCode());
	}
	
	/**
	 * This function checks if a key is pressed, and it checks the key exactly once.
	 * 
	 * @param keyCode  The integer key code for the key to check, see {@link java.awt.event.KeyEvent}.
	 * 
	 * @return True if the key is pressed (for the first time), false otherwise.
	 */
	public static boolean isKeyPressedOnce(int keyCode) {
		if (pressedKeys.contains(keyCode) && !consumedKeys.contains(keyCode)) {
			consumedKeys.add(keyCode);
			return true;
		}
		return false;
	}
	
	/**
	 * This function checks if a key is pressed.
	 * 
	 * @param keyCode  The integer key code for the key to check, see {@link java.awt.event.KeyEvent}.
	 * 
	 * @return  True if the key is pressed, false otherwise.
	 */
	public static boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}
	
}