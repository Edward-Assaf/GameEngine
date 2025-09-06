package windows;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import deviceInputs.KeyHandler;
import main.GamePanel;

/**
 * The {@code Window} class will initialize
 * the only {@code Window} object, which will
 * contain every panel in the game, including
 * the game panel.
 * 
 * <p>It also provides the device's screen dimensions.</p>
 * 
 * <p>It implements {@link javax.swing.JFrame} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Window extends JFrame {
	/**
	 * The name of the main window.
	 */
	public final String windowName = "The Islander's Curse";
	
	/**
	 * The dimensions of the current user's device screen.
	 */
	public final static Dimension deviceScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * The resolution of the current user's device screen.
	 */
	public final static double deviceScreenResolution = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
	
	/**
	 * The width of the current user's device screen.
	 * It comes from {@code deviceScreenSize.getWidth()}.
	 */
	public final static int deviceScreenWidth = (int) deviceScreenSize.getWidth();
	
	/**
	 * The height of the current user's device screen.
	 * It comes from {@code deviceScreenSize.getHeight()}.
	 */
	public final static int deviceScreenHeight = (int) deviceScreenSize.getHeight();
	
	/**
	 * The ratio of the full screen size to the minimized screen size.
	 * It allows for a dynamic and suitable screen size for any display.
	 */
	public final static double RATIO = 1.3 / deviceScreenResolution;

	/**
	 * The window's minimized width is {@code deviceScreenWidth} divided by {@code ratio}.
	 */
	public final static int windowMinimizedWidth = (int) (deviceScreenWidth / RATIO);
	
	/**
	 * The window's minimized height is {@code deviceScreenHeight} divided by {@code ratio}.
	 */
	public final static int windowMinimizedHeight = (int) (deviceScreenHeight / RATIO);
	
	/**
	 * This determines the X value that centers the minimized window horizontally.
	 */
	public final static int minimizedWindowLocationX = (deviceScreenWidth - windowMinimizedWidth)/2;
	
	/**
	 * This determines the Y value that centers the minimized window vertically.
	 */
	public final static int minimizedWindowLocationY = (deviceScreenHeight - windowMinimizedHeight)/2;
	
	/**
	 * This determines the window state, true for full screen and false for minimized screen.
	 * It's false by default.
	 */
	public static boolean isFullScreen = false;
	
	/**
	 * This stacks the panels used for window design.
	 * @see {@link java.awt.CardLayout}
	 */
	private CardLayout cardLayout;
	
	/**
	 * The current panel to be displayed.
	 */
	public String currentCardLayoutPanel = "LOGIN";
	
	/**
	 * The main panel of the window that will contain and manage the panels stored in the {@code cardLayout}.
	 */
	private JPanel mainPanel;
	
	/**
	 * The {@link main.GamePanel} object that contains and runs the game loop.
	 */
	public GamePanel gamePanel = new GamePanel(this);
	
	// Initializing the panels.
	public LoginPanel loginPanel = new LoginPanel(this);
	public SignUpPanel signUpPanel = new SignUpPanel(this);
	public SignInPanel signInPanel = new SignInPanel(this);
	public MainPanel mainMenuPanel = new MainPanel(this);
	public PausePanel pausePanel = new PausePanel(this);
	public GameOverPanel gameOverPanel = new GameOverPanel(this);
	// End of initializing the panels.
	
	/**
	 * The default constructor of {@link Window} class.
	 * It initializes the {@link Window} object.
	 */
	public Window() {
		// Setting up the window.
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(minimizedWindowLocationX, minimizedWindowLocationY);
		setSize(windowMinimizedWidth, windowMinimizedHeight);
		setTitle(windowName + " - Login Menu");
		setFocusable(true);
		
		// Initializing the main panel with its card layout.
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		// Adding the panels to the main panel with their unique key names.
		mainPanel.add(loginPanel, "LOGIN");
		mainPanel.add(signUpPanel, "SIGNUP");
		mainPanel.add(signInPanel, "SIGNIN");
		mainPanel.add(mainMenuPanel, "MAIN");
		mainPanel.add(gamePanel, "GAME");
		mainPanel.add(pausePanel, "PAUSE");
		mainPanel.add(gameOverPanel, "GAMEOVER");
		
		// Show the starting panel.
		cardLayout.show(mainPanel, "LOGIN");
		
		// Adding the panels to the window object.
		add(mainPanel);

		// Adding a Key Listener to the window object.
		addKeyListener(new KeyHandler(this));
	}
	
	/**
	 * This function shows a panel in the window using the panel's key name,
	 * and updates the {@code currentCardLayoutPanel} value to be the current panel.
	 * @param panelName  The key name of the panel to show.
	 */
	public void showPanel(String panelName) {
		cardLayout.show(mainPanel, panelName);
		currentCardLayoutPanel = panelName;
	}
	
}