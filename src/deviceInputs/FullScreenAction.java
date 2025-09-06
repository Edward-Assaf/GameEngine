package deviceInputs;

import javax.swing.JFrame;

import windows.Window;
import windows.WindowUtilities;

/**
 * The {@code FullScreenAction} class is responsible
 * for defining the action that occurs when toggling
 * full-screen mode is requested.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class FullScreenAction {
	/**
	 * The window that the action should be performed on.
	 */
	private Window actionWindow;
	
	/**
	 * The default constuctor of {@link FullScreenAction} class. It
	 * specifies which window the action should be performed on.
	 * 
	 * @param actionWindow  The window for the action to be performed on.
	 */
	public FullScreenAction(Window actionWindow) {
		this.actionWindow = actionWindow;
	}
	
	/**
	 * This function. when called, toggles the full-screen mode by
	 * flipping the value of {@code isFullScreen} and performing
	 * necessary scaling operations.
	 */
	public void performAction() {
		Window.isFullScreen = !Window.isFullScreen;
		actionWindow.dispose();
		if (Window.isFullScreen) {
			actionWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		else {
			actionWindow.setExtendedState(JFrame.NORMAL);
			actionWindow.setLocation(Window.minimizedWindowLocationX, Window.minimizedWindowLocationY);
			actionWindow.setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		}
		WindowUtilities.scale(actionWindow.loginPanel);
		WindowUtilities.scale(actionWindow.signUpPanel);
		WindowUtilities.scale(actionWindow.signInPanel);
		WindowUtilities.scale(actionWindow.mainMenuPanel);
		WindowUtilities.scale(actionWindow.pausePanel);
		WindowUtilities.scale(actionWindow.gameOverPanel);
		actionWindow.setVisible(true);
	}

}
