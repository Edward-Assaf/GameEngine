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
import main.GamePanel;
import mapControls.MapUtilities;
import media.MediaResource;
import windowComponents.FontSize;
import windowComponents.FontStyle;

/**
 * The {@code PausePanel} class initializes
 * the components of the Pause Menu.
 * 
 * <p>It implements {@link javax.swing.JPanel} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PausePanel extends JPanel implements Scalable{
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
	 * "Paused" title in this panel. 
	 */
	private JLabel title;
	
	/**
	 * The original bounds of the title.
	 */
	private Rectangle titleBounds;
	
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
	private JButton quitButton;
	
	/**
	 * The original bounds of the Quit Game button.
	 */
	private Rectangle quitButtonBounds;
	
	/**
	 * The Continue Game button that resumes the game and ends pausing.
	 */
	private JButton continueButton;
	
	/**
	 * The original bounds of the Continue Game button.
	 */
	private Rectangle continueButtonBounds;
	
	/**
	 * The constructor of the {@code PausePanel}, it initializes
	 * this menu and its components.
	 */
	public PausePanel(Window window) {
		this.window = window;
		setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		setLayout(null);
		init();
		showPauseStep();
	}
	
	/**
	 * This function initializes the components of the Pause Menu.
	 */
	private void init() {
		// Initializing the Quit Game button.
		quitButton = new JButton("Quit Game");
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
				GamePanel.gamePaused = false;
				MapUtilities.saveData();
				window.setTitle(window.windowName + " - Main Menu");
				window.showPanel("MAIN");
				AudioUtilities.AUDIO_RESOURCES.get("gameMusic").terminate();
				AudioUtilities.AUDIO_RESOURCES.get("backgroundMusic").playIndefinitely();
			}
		});
		quitButton.setForeground(new Color(255, 255, 255));
		quitButton.setBackground(new Color(0, 128, 255));
		quitButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		quitButton.setToolTipText("Quit Game Button");
		quitButton.setBounds(170, 258, 248, 50);
		quitButtonBounds = quitButton.getBounds();
		// End of initializing the Quit Game button.
		
		// Initializing the Continue Game button.
		continueButton = new JButton("Continue Game");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				GamePanel.gamePaused = false;
				window.setTitle(window.windowName);
				window.showPanel("GAME");
				
			}
		});
		continueButton.setForeground(new Color(255, 255, 255));
		continueButton.setBackground(new Color(0, 128, 255));
		continueButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		continueButton.setToolTipText("Continue Game Button");
		continueButton.setBounds(536, 258, 248, 50);
		continueButtonBounds = continueButton.getBounds();
		// End of initializing the Continue Game button.
		
		// Initializing the title.
		title = new JLabel();
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setText("Paused");
		title.setOpaque(false);
		title.setBounds(107, 57, 762, 97);
		title.setBorder(null);
		titleBounds = title.getBounds();
		// End of initializing the title.
		
		// Initializing the background image.
		backgroundImage = new JLabel("");
		backgroundImage.setIcon(minimizedBackgroundImage);
		backgroundImage.setBounds(0, 0, Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		backgroundImageBounds = backgroundImage.getBounds();
		// End of initializing the background image.
	}
	
	/**
	 * This function shows the components of Pause
	 * Menu by adding them to the Pause Panel.
	 */
	private void showPauseStep() {
		removeAll();
		add(quitButton);
		add(continueButton);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}

	@Override
	public void scaleUpFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		quitButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		continueButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
	}

	@Override
	public void scaleDownFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		quitButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		continueButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
	}

	@Override
	public void scaleComponents() {
		WindowUtilities.scale(title, titleBounds);
		WindowUtilities.scale(backgroundImage, backgroundImageBounds);
		WindowUtilities.scale(quitButton, quitButtonBounds);
		WindowUtilities.scale(continueButton, continueButtonBounds);
		if (Window.isFullScreen) {
			backgroundImage.setIcon(resizedBackgroundImage);
		}
		else {
			backgroundImage.setIcon(minimizedBackgroundImage);
		}
	}
}