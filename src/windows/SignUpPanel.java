package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import audio.AudioUtilities;
import database.DatabaseUtilities;
import deviceInputs.KeyHandler;
import media.MediaResource;
import windowComponents.FontSize;
import windowComponents.FontStyle;
import windowComponents.TextBox;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

/**
 * The {@code SignUpPanel} class initializes
 * the components of the Sign-Up Menu.
 * 
 * <p>It implements {@link javax.swing.JPanel} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SignUpPanel extends JPanel implements Scalable {
	/**
	 * A pointer to the window this panel is added to.
	 */
	public Window window;
	
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
	 * The original bounds of title.
	 */
	private Rectangle titleBounds;
	
	/**
	 * The label component that displays the background image
	 * of this panel.
	 */
	private JLabel backgroundImage;
	
	/**
	 * The original bounds of background image.
	 */
	private Rectangle backgroundImageBounds;
	
	/**
	 * A string that tracks the current step of the sign-up process
	 */
	private String currentStep;
	
	/**
	 * The username box.
	 */
	private TextBox usernameBox;
	
	/**
	 * The original bounds of username box.
	 */
	private Rectangle usernameBoxBounds;
	
	/**
	 * The password box.
	 */
	private TextBox passwordBox;
	
	/**
	 * The original bounds of password box.
	 */
	private Rectangle passwordBoxBounds;
	
	/**
	 * The verify-password box.
	 */
	private TextBox verifyPasswordBox;
	
	/**
	 * The original bounds of verify-password box.
	 */
	private Rectangle verifyPasswordBoxBounds;
	
	/**
	 * The username box's back button that navigates back to the Login Menu.
	 */
	public JButton usernameBox_backButton;
	
	/**
	 * The original bounds of username box's back button.
	 */
	private Rectangle usernameBox_backButton_bounds;
	
	/**
	 * The username box's next button that navigates to the password selection menu.
	 */
	public JButton usernameBox_nextButton;
	
	/**
	 * The original bounds of username box's next button.
	 */
	private Rectangle usernameBox_nextButton_bounds;
	
	/**
	 * The password box's back button that navigates back to the username selection Menu.
	 */
	public JButton passwordBox_backButton;
	
	/**
	 * The original bounds of password box's back button.
	 */
	private Rectangle passwordBox_backButton_bounds;
	
	/**
	 * The password box's next button that navigates to the verify-password menu.
	 */
	public JButton passwordBox_nextButton;
	
	/**
	 * The original bounds of password box's next button.
	 */
	private Rectangle passwordBox_nextButton_bounds;
	
	/**
	 * The verify-password box's back button that navigates back to the password selection Menu.
	 */
	public JButton verifyPasswordBox_backButton;
	
	/**
	 * The original bounds of verify password box's back button.
	 */
	private Rectangle verifyPasswordBox_backButton_bounds;
	
	/**
	 * The verify-password box's next button that navigates to the Main Menu.
	 */
	public JButton verifyPasswordBox_nextButton;
	
	/**
	 * The original bounds of verify password box's next button.
	 */
	private Rectangle verifyPasswordBox_nextButton_bounds;
	
	/**
	 * Displays note messages depending on certain actions.
	 */
	private JLabel noteMessage;
	
	/**
	 * The original bounds of noteMessage.
	 */
	private Rectangle noteMessageBounds;
	
	/**
	 * Displays note messages depending on certain actions.
	 */
	private JLabel noteMessage2;
	
	/**
	 * The original bounds of noteMessage2.
	 */
	private Rectangle noteMessage2Bounds;
	
	/**
	 * The constructor of the {@code SignUpPanel}, it initializes
	 * this menu and its components.
	 */
	public SignUpPanel(Window window) {
		this.window = window;
		setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		setLayout(null);
		init();
		showUsernameStep();		
	}
	
	/**
	 * This function initializes the components of the Sign-Up Menu.
	 */
	private void init() {
		// Initializing the noteMessage label.
		noteMessage = new JLabel();
		noteMessage.setForeground(new Color(255, 0, 0));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
		noteMessage.setHorizontalAlignment(SwingConstants.CENTER);
		noteMessage.setBackground(new Color(255, 255, 255));
		noteMessage.setOpaque(false);
		noteMessage.setBounds(20, 355, 360, 25);
		noteMessage.setBorder(null);
		noteMessageBounds = noteMessage.getBounds();
		// End of initializing the noteMessage label.
		
		// Initializing the noteMessage2 label.
		noteMessage2 = new JLabel();
		noteMessage2.setForeground(new Color(255, 0, 0));
		noteMessage2.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
		noteMessage2.setHorizontalAlignment(SwingConstants.CENTER);
		noteMessage2.setBackground(new Color(255, 255, 255));
		noteMessage2.setOpaque(false);
		noteMessage2.setBounds(20, 373, 360, 25);
		noteMessage2.setBorder(null);
		noteMessage2Bounds = noteMessage2.getBounds();
		// End of initializing the noteMessage2 label.
		
		// Initializing usernameStep:
		usernameBox = new TextBox("Choose a username","username",this);
		usernameBox.setBounds(90, 250, 220, 50);
		usernameBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		usernameBox.setToolTipText("Pick a 3-20 characters long username consisting of letters & numbers only");
		usernameBoxBounds = usernameBox.getBounds();
		
		usernameBox_backButton = new JButton("Back");
		usernameBox_backButton.setBounds(90, 320, 100, 25);
		usernameBox_backButton.setForeground(new Color(255, 255, 255));
		usernameBox_backButton.setBackground(new Color(0, 128, 255));
		usernameBox_backButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		usernameBox_backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				noteMessage.setText("");
				noteMessage2.setText("");
				window.showPanel("LOGIN");
				window.setTitle(window.windowName + " - Login Menu");
				usernameBox.setText(usernameBox.getPlaceHolder());
			}
		});
		usernameBox_backButton_bounds = usernameBox_backButton.getBounds();

		usernameBox_nextButton = new JButton("Next");
		usernameBox_nextButton.setBounds(210, 320, 100, 25);
		usernameBox_nextButton.setForeground(new Color(255, 255, 255));
		usernameBox_nextButton.setBackground(new Color(0, 128, 255));	
		usernameBox_nextButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		usernameBox_nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				noteMessage2.setText("");
				String username = usernameBox.getText().toString();
				if (DatabaseUtilities.usernameExistsInDatabase(username)) {
					AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
					noteMessage.setText("Username already exists! Try another one!");
					return;
				}
		        if (DatabaseUtilities.isValidUsername(username)) {
		        	AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
		        	showPasswordStep();
		        }
		        else {
		        	AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		        	noteMessage.setText("Only 3-20 characters long usernames are allowed");
		        	noteMessage2.setText("and usernames can only contain letters & numbers");
		        	usernameBox.setText(usernameBox.getPlaceHolder());
		        }
			}
		});
		usernameBox_nextButton_bounds = usernameBox_nextButton.getBounds();
		// End of initializing usernameStep.
		
		// Initializing passwordStep:
		passwordBox = new TextBox("Choose a password","password",this);
		passwordBox.setBounds(90, 250, 220, 50);
		passwordBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		passwordBox.setToolTipText("Pick a password that is 8-30 characters long");
		passwordBoxBounds = passwordBox.getBounds();
		
		passwordBox_backButton = new JButton("Back");
		passwordBox_backButton.setBounds(90, 320, 100, 25);
		passwordBox_backButton.setForeground(new Color(255, 255, 255));
		passwordBox_backButton.setBackground(new Color(0, 128, 255));
		passwordBox_backButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		passwordBox_backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				noteMessage.setText("");
				showUsernameStep();
				passwordBox.setText(passwordBox.getPlaceHolder());
			}
		});
		passwordBox_backButton_bounds = passwordBox_backButton.getBounds();
		
		passwordBox_nextButton = new JButton("Next");
		passwordBox_nextButton.setBounds(210, 320, 100, 25);
		passwordBox_nextButton.setForeground(new Color(255, 255, 255));
		passwordBox_nextButton.setBackground(new Color(0, 128, 255));
		passwordBox_nextButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		passwordBox_nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				String password = passwordBox.getText().toString();
		        if (DatabaseUtilities.isValidPassword(password)) {
		        	AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
		        	showVerifyPasswordStep();
		        }
		        else {
		        	AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		        	noteMessage.setText("Password must be 8-30 characters long!");
		        	passwordBox.setText(passwordBox.getPlaceHolder());
		        }
			}
		});
		passwordBox_nextButton_bounds = passwordBox_nextButton.getBounds();
		// End of initializing passwordStep.
		
		// Initializing verifyPasswordStep:
		verifyPasswordBox = new TextBox("Re-enter your password","verifyPassword",this);
		verifyPasswordBox.setBounds(90, 250, 220, 50);
		verifyPasswordBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		verifyPasswordBoxBounds = verifyPasswordBox.getBounds();
		
		verifyPasswordBox_backButton = new JButton("Back");
		verifyPasswordBox_backButton.setBounds(90, 320, 100, 25);
		verifyPasswordBox_backButton.setForeground(new Color(255, 255, 255));
		verifyPasswordBox_backButton.setBackground(new Color(0, 128, 255));
		verifyPasswordBox_backButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		verifyPasswordBox_backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				verifyPasswordBox.setText(verifyPasswordBox.getPlaceHolder());
				noteMessage.setText("");
				showPasswordStep();
			}
		});
		verifyPasswordBox_backButton_bounds = verifyPasswordBox_backButton.getBounds();
		
		verifyPasswordBox_nextButton = new JButton("Next");
		verifyPasswordBox_nextButton.setBounds(210, 320, 100, 25);
		verifyPasswordBox_nextButton.setForeground(new Color(255, 255, 255));
		verifyPasswordBox_nextButton.setBackground(new Color(0, 128, 255));
		verifyPasswordBox_nextButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		verifyPasswordBox_nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				if (!verifyPasswordBox.getText().equals(passwordBox.getText())) {
					AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
			          noteMessage.setText("Password mismatch! Try again!");
			          return;
		        }
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				
		        String username = usernameBox.getText();
		        String password = passwordBox.getText();
		        String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		        
		        DatabaseUtilities.insertUserDataInAccounts(username, password, date);
				DatabaseUtilities.insertNewJSONFileForUser(username);
				DatabaseUtilities.initializeGameDataForUser(username);
				
		        window.showPanel("LOGIN");
		        window.setTitle(window.windowName + " - Login Menu");
		        showUsernameStep();
		        usernameBox.setText(usernameBox.getPlaceHolder());
		        passwordBox.setText(passwordBox.getPlaceHolder());
		        verifyPasswordBox.setText(verifyPasswordBox.getPlaceHolder());
			}
		});
		verifyPasswordBox_nextButton_bounds = verifyPasswordBox_nextButton.getBounds();
		// End of initializing verifyPasswordStep.
		
		// Adding a mouse click listener to check for out-of-textbox-bounds clicks.
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				Point eventPoint = e.getPoint();
				if (currentStep == "username") {
					if (!usernameBoxBounds.contains(eventPoint) 
							&& usernameBox.getText().trim().isEmpty()) {
						usernameBox.setText(usernameBox.getPlaceHolder());
					}
				}
				else if (currentStep == "password") {
					if (!passwordBoxBounds.contains(eventPoint) 
							&& passwordBox.getText().trim().isEmpty()) {
						passwordBox.setText(passwordBox.getPlaceHolder());
					}
				}
				else if (currentStep == "verifyPassword") {
					if (!verifyPasswordBoxBounds.contains(eventPoint)
							&& verifyPasswordBox.getText().trim().isEmpty()) {
						verifyPasswordBox.setText(verifyPasswordBox.getPlaceHolder());
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		// End of adding a mouse click listener to check for out-of-textbox-bounds clicks.
		
		// Initializing the title.
		title = new JLabel();
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBackground(new Color(255, 255, 255));
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
	 * This function shows the username selection menu
	 * by adding its components to this panel.
	 */
	private void showUsernameStep() {
		currentStep = "username";
		removeAll();
		add(noteMessage2);
		add(noteMessage);
		add(usernameBox_backButton);
		add(usernameBox_nextButton);
		add(usernameBox);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function shows the password selection menu
	 * by adding its components to this panel.
	 */
	private void showPasswordStep() {
		currentStep = "password";
		removeAll();
		add(noteMessage);
		add(passwordBox_backButton);
		add(passwordBox_nextButton);
		add(passwordBox);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function shows the verify-password selection menu
	 * by adding its components to this panel.
	 */
	private void showVerifyPasswordStep() {
		currentStep = "verifyPassword";
		removeAll();
		add(noteMessage);
		add(verifyPasswordBox_backButton);
		add(verifyPasswordBox_nextButton);
		add(verifyPasswordBox);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	@Override
	public void scaleUpFontSizes() {
		title.setFont(new Font(
				FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		usernameBox.setFont(new Font(
				FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, (int)(FontSize.TEXTBOX_SIZE.getValue()*Window.RATIO)));
		usernameBox_backButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		usernameBox_nextButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		passwordBox.setFont(new Font(
				FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, (int)(FontSize.TEXTBOX_SIZE.getValue()*Window.RATIO)));
		passwordBox_backButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		passwordBox_nextButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		verifyPasswordBox.setFont(new Font(
				FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, (int)(FontSize.TEXTBOX_SIZE.getValue()*Window.RATIO)));
		verifyPasswordBox_backButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		verifyPasswordBox_nextButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		noteMessage.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, (int)(FontSize.NOTES_SIZE.getValue()*Window.RATIO)));
		noteMessage2.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, (int)(FontSize.NOTES_SIZE.getValue()*Window.RATIO)));
	}
	
	@Override
	public void scaleDownFontSizes() {
		title.setFont(new Font(
				FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		usernameBox.setFont(new Font(
				FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		usernameBox_backButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		usernameBox_nextButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		passwordBox.setFont(new Font(
				FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		passwordBox_backButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		passwordBox_nextButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		verifyPasswordBox.setFont(new Font(
				FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		verifyPasswordBox_backButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		verifyPasswordBox_nextButton.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		noteMessage.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
		noteMessage2.setFont(new Font(
				FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
	}
	
	@Override
	public void scaleComponents() {
		WindowUtilities.scale(title, titleBounds);
		WindowUtilities.scale(backgroundImage, backgroundImageBounds);
		WindowUtilities.scale(usernameBox, usernameBoxBounds);
		WindowUtilities.scale(passwordBox, passwordBoxBounds);
		WindowUtilities.scale(verifyPasswordBox, verifyPasswordBoxBounds);
		WindowUtilities.scale(usernameBox_backButton, usernameBox_backButton_bounds);
		WindowUtilities.scale(usernameBox_nextButton, usernameBox_nextButton_bounds);
		WindowUtilities.scale(passwordBox_backButton, passwordBox_backButton_bounds);
		WindowUtilities.scale(passwordBox_nextButton, passwordBox_nextButton_bounds);
		WindowUtilities.scale(verifyPasswordBox_backButton, verifyPasswordBox_backButton_bounds);
		WindowUtilities.scale(verifyPasswordBox_nextButton, verifyPasswordBox_nextButton_bounds);
		WindowUtilities.scale(noteMessage, noteMessageBounds);
		WindowUtilities.scale(noteMessage2, noteMessage2Bounds);
		if (Window.isFullScreen) {
			backgroundImage.setIcon(resizedBackgroundImage);
		}
		else {
			backgroundImage.setIcon(minimizedBackgroundImage);
		}
	}
	
	public void update() {
		if(currentStep.equals("username") && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ENTER)) {
			usernameBox_nextButton.doClick();
		}
		if(currentStep.equals("username") && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ESCAPE)) {
			usernameBox_backButton.doClick();
		}
		if(currentStep.equals("password") && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ENTER)) {
			passwordBox_nextButton.doClick();
		}
		if(currentStep.equals("password") && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ESCAPE)) {
			passwordBox_backButton.doClick();
		}
		if(currentStep.equals("verifyPassword") && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ENTER)) {
			verifyPasswordBox_nextButton.doClick();
		}
		if(currentStep.equals("verifyPassword") && KeyHandler.isKeyPressedOnce(KeyEvent.VK_ESCAPE)) {
			verifyPasswordBox_backButton.doClick();
		}
	}
}