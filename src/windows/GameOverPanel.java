package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import audio.AudioUtilities;
import database.DatabaseUtilities;
import gameObjects.DynamicObject;
import gameObjects.GameObjectUtilities;
import gameObjects.Monster;
import gameObjects.Player;
import media.MediaResource;
import windowComponents.FontSize;
import windowComponents.FontStyle;

/**
 * The {@code GameOverPanel} class initializes
 * the components of the Game Over Menu.
 * 
 * <p>It implements {@link javax.swing.JPanel} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GameOverPanel extends JPanel implements Scalable{
	/**
	 * A pointer to the window this panel is added to.
	 */
	private Window window;

	/**
	 * The background image (minimized version) to be
	 * used with {@code backgroundImage}.
	 */
	public static ImageIcon minimizedBackgroundImage = 
			new ImageIcon(new MediaResource("MainMenuBackgroundMinimized.jpg").getResourceAbsolutePath());
	
	/**
	 * The background image (full-screen version) to be
	 * used with {@code backgroundImage}.
	 */
	public static ImageIcon resizedBackgroundImage = 
			new ImageIcon(new MediaResource("MainMenuBackgroundResized.jpg").getResourceAbsolutePath());
	
	/**
	 * The title component that displays the
	 * "Game Over!" title in this panel. 
	 */
	private JLabel titleGameOver;
	
	/**
	 * The original bounds of the "Game Over!" title .
	 */
	private Rectangle titleGameOverBounds;
	
	/**
	 * The title component that displays the
	 * "You Win!" title in this panel. 
	 */
	private JLabel titleWinGame;
	
	/**
	 * The original bounds of the "You Won!" title .
	 */
	private Rectangle titleWinGameBounds;
	
	/**
	 * The label component that displays the background image
	 * of this panel. 
	 */
	private JLabel backgroundImage;
	
	/**
	 * The original bounds of the background image.
	 */
	private Rectangle backgroundImageBounds;
	
	/**
	 * The Quit Game button that quits the game.
	 */
	private JButton quitGameButton;
	
	/**
	 * The original bounds of the Quit Game button.
	 */
	private Rectangle quitGameButtonBounds;
	
	/**
	 * The Back To Main button that navigates back to the Main Menu.
	 */
	private JButton backToMainMenuButton;
	
	/**
	 * The original bounds of the Back To Main button.
	 */
	private Rectangle backToMainMenuButtonBounds;
	
	/**
	 * The constructor of the {@code GameOverPanel}, it initializes
	 * this menu and its components.
	 */
	public GameOverPanel(Window window) {
		this.window = window;
		setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		setLayout(null);
		init();
		showStep();
	}

	/**
	 * This function initializes the components of the Game Over Menu.
	 */
	private void init() {
		// Initializing the Quit Game button.
		quitGameButton = new JButton("Quit Game");
		quitGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
				System.out.println("Quitting The Game...");
				System.exit(0);
			}
		});
		quitGameButton.setForeground(new Color(255, 255, 255));
		quitGameButton.setBackground(new Color(0, 128, 255));
		quitGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		quitGameButton.setToolTipText("Quit Game Button");
		quitGameButton.setBounds(170, 258, 248, 50);
		quitGameButtonBounds = quitGameButton.getBounds();
		// End of initializing the Quit Game button.
		
		// Initializing the Continue Game button.
		backToMainMenuButton = new JButton("Back To Main");
		backToMainMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				AudioUtilities.AUDIO_RESOURCES.get("gameMusic").terminate();
				AudioUtilities.AUDIO_RESOURCES.get("backgroundMusic").playIndefinitely();
				window.setTitle(window.windowName + " - Main Menu");
				window.showPanel("MAIN");	
			}
		});
		backToMainMenuButton.setForeground(new Color(255, 255, 255));
		backToMainMenuButton.setBackground(new Color(0, 128, 255));
		backToMainMenuButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		backToMainMenuButton.setToolTipText("Back To Game Button");
		backToMainMenuButton.setBounds(536, 258, 248, 50);
		backToMainMenuButtonBounds = backToMainMenuButton.getBounds();
		// End of initializing the Continue Game button.
		
		// Initializing the Game Over title.
		titleGameOver = new JLabel();
		titleGameOver.setForeground(new Color(255, 255, 255));
		titleGameOver.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		titleGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		titleGameOver.setText("Game Over!");
		titleGameOver.setOpaque(false);
		titleGameOver.setBounds(107, 57, 762, 97);
		titleGameOver.setBorder(null);
		titleGameOverBounds = titleGameOver.getBounds();
		// End of initializing the Game Over title.
		
		// Initializing the Win Game title.
		titleWinGame = new JLabel();
		titleWinGame.setForeground(new Color(255, 255, 255));
		titleWinGame.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		titleWinGame.setHorizontalAlignment(SwingConstants.CENTER);
		titleWinGame.setText("You Won!");
		titleWinGame.setOpaque(false);
		titleWinGame.setBounds(107, 57, 762, 97);
		titleWinGame.setBorder(null);
		titleWinGameBounds = titleWinGame.getBounds();
		// End of initializing the Win Game title.
		
		// Initializing the background image.
		backgroundImage = new JLabel("");
		backgroundImage.setIcon(minimizedBackgroundImage);
		backgroundImage.setBounds(0, 0, Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		backgroundImageBounds = backgroundImage.getBounds();
		// End of initializing the background image.
	}
	
	/**
	 * This function shows the components of Game-Over-Step
	 * Menu by adding them to the Game Over Panel.
	 */
	private void showGameOverStep() {
		removeAll();
		add(quitGameButton);
		add(backToMainMenuButton);
		add(titleGameOver);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function shows the components of Win-Step
	 * Menu by adding them to the Game Over Panel.
	 */
	private void showWinStep() {
		removeAll();
		add(quitGameButton);
		add(backToMainMenuButton);
		add(titleWinGame);
		add(backgroundImage);
		revalidate();
		repaint();
	}

	/**
	 * This function shows the default-state components of Game Over
	 * Menu by adding them to the Game Over Panel.
	 */
	private void showStep() {
		removeAll();
		add(quitGameButton);
		add(backToMainMenuButton);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function updates the state of Game Over Panel (Winning or Losing).
	 */
	public void update() {
		int monstersCount = GameObjectUtilities.dynamicObjects.length - 1;
		int deadMonstersCount = 0;
		for (DynamicObject object : GameObjectUtilities.dynamicObjects) {
			if (object instanceof Player) {
				if (object.getHealth() == 0) {
					showGameOverStep();
					DatabaseUtilities.markGameOver(DatabaseUtilities.currentUser);
					return;
				}
			}
			else if (object instanceof Monster) {
				if (object.getHealth() == 0) {
					deadMonstersCount += 1;
				}
			}
		}
		if (monstersCount == deadMonstersCount) {
			showWinStep();
			DatabaseUtilities.markGameOver(DatabaseUtilities.currentUser);
		}
	}
	
	@Override
	public void scaleUpFontSizes() {
		titleGameOver.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		titleWinGame.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		quitGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		backToMainMenuButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
	}

	@Override
	public void scaleDownFontSizes() {
		titleGameOver.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		titleWinGame.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		quitGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		backToMainMenuButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
	}

	@Override
	public void scaleComponents() {
		WindowUtilities.scale(titleGameOver, titleGameOverBounds);
		WindowUtilities.scale(titleWinGame, titleWinGameBounds);
		WindowUtilities.scale(backgroundImage, backgroundImageBounds);
		WindowUtilities.scale(quitGameButton, quitGameButtonBounds);
		WindowUtilities.scale(backToMainMenuButton, backToMainMenuButtonBounds);
		if (Window.isFullScreen) {
			backgroundImage.setIcon(resizedBackgroundImage);
		}
		else {
			backgroundImage.setIcon(minimizedBackgroundImage);
		}
	}
}
