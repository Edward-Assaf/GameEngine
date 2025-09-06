package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import audio.AudioUtilities;
import media.MediaResource;
import windowComponents.FontSize;
import windowComponents.FontStyle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The {@code LoginPanel} class initializes
 * the components of the Login Menu.
 * 
 * <p>It implements {@link javax.swing.JPanel} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements Scalable {
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
	 * The title component that displays the name of
	 * the game in this panel. 
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
	 * The Sign-In button that navigates to the Sign-In Menu.
	 */
	private JButton signInButton;
	
	/**
	 * The original bounds of the Sign-In button.
	 */
	private Rectangle signInButtonBounds;
	
	/**
	 * The Sign-Up button that navigates to the Sign-Up Menu.
	 */
	private JButton signUpButton;
	
	/**
	 * The original bounds of the Sign-Up button.
	 */
	private Rectangle signUpButtonBounds;
	
	/**
	 * The constructor of the {@code LoginPanel}, it initializes
	 * this menu and its components.
	 */
	public LoginPanel(Window window) {
		this.window = window;
		setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		setLayout(null);
		init();
		showStep();
	}
	
	/**
	 * This function initializes the components of the Login Menu.
	 */
	private void init() {
		// Initializing the Sign-Up button.
		signUpButton = new JButton("Sign Up");
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				window.showPanel("SIGNUP");
				window.setTitle(window.windowName + " - Sign Up Menu");
			}
		});
		signUpButton.setForeground(new Color(255, 255, 255));
		signUpButton.setBackground(new Color(0, 128, 255));
		signUpButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		signUpButton.setToolTipText("Sign Up Button");
		signUpButton.setBounds(90, 250, 220, 50);
		signUpButtonBounds = signUpButton.getBounds();
		// End of initializing the Sign-Up button.
		
		// Initializing the Sign-In button.
		signInButton = new JButton("Sign In");
		signInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				window.showPanel("SIGNIN");
				window.setTitle(window.windowName + " - Sign In Menu");
			}
		});
		signInButton.setForeground(new Color(255, 255, 255));
		signInButton.setBackground(new Color(0, 128, 255));
		signInButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		signInButton.setToolTipText("Sign In Button");
		signInButton.setBounds(90, 320, 220, 50);
		signInButtonBounds = signInButton.getBounds();
		// End of initializing the Sign-In button.
		
		// Initializing the title.
		title = new JLabel();
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setText("The Islander's Curse");
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
	 * This function shows the components of Login
	 * Menu by adding them to the Login Panel.
	 */
	private void showStep() {
		add(signUpButton);
		add(signInButton);
		add(title);
		add(backgroundImage);
	}
	
	@Override
	public void scaleUpFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		signUpButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		signInButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
	}
	
	@Override
	public void scaleDownFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		signUpButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		signInButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
	}
	
	@Override
	public void scaleComponents() {
		WindowUtilities.scale(title, titleBounds);
		WindowUtilities.scale(backgroundImage, backgroundImageBounds);
		WindowUtilities.scale(signInButton, signInButtonBounds);
		WindowUtilities.scale(signUpButton, signUpButtonBounds);
		if (Window.isFullScreen) {
			backgroundImage.setIcon(resizedBackgroundImage);
		}
		else {
			backgroundImage.setIcon(minimizedBackgroundImage);
		}
	}

}
