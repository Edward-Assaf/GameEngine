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
import main.Main;
import media.MediaResource;
import windowComponents.FontSize;
import windowComponents.FontStyle;
import windowComponents.TextBox;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

/**
 * The {@code SignInPanel} class initializes
 * the components of the Sign-In Menu.
 * 
 * <p>It implements {@link javax.swing.JPanel} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SignInPanel extends JPanel implements Scalable {
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
	 * An SQL statement that returns the names of users.
	 */
	public static final String USERS_STATEMENT = "SELECT user_name FROM accounts;";
	
	/**
	 * An updatable list of current users in the database.
	 */
	public static ArrayList<HashMap<String, Object>> users = Main.connector.getQueryResult(USERS_STATEMENT);
	
	/**
	 * A flag that is triggered (set to true) whenever users array is modified.
	 */
	public static boolean usersListUpdateFlag = true;
	
	/**
	 * The password box.
	 */
	private TextBox passwordBox;
	
	/**
	 * The original bounds of password box.
	 */
	private Rectangle passwordBoxBounds;
	
	/**
	 * A {@code JComboBox} object that will contain all
	 * usernames, and display them in a drop-down menu.
	 */
	private JComboBox<String> comboBox;
	
	/**
	 * The original bounds of comboBox.
	 */
	private Rectangle comboBoxBounds;
	
	/**
	 * The back button that navigates back to the Login Menu.
	 */
	public JButton backButton;
	
	/**
	 * The original bounds of back button.
	 */
	private Rectangle backButtonBounds;
	
	/**
	 * The Sign-In button that authenticates the Sign-In process.
	 */
	public JButton signInButton;
	
	/**
	 * The original bounds of Sign-In button.
	 */
	private Rectangle signInButtonBounds;
	
	/**
	 * Displays note messages depending on certain actions.
	 */
	private JLabel noteMessage;
	
	/**
	 * The original bounds of noteMessage.
	 */
	private Rectangle noteMessageBounds;
	
	/**
	 * The constructor of the {@code SignInPanel}, it initializes
	 * this menu and its components.
	 */
	public SignInPanel(Window window) {
		this.window = window;
		setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		setLayout(null);
		init();
		showStep();
	}

	/**
	 * This function initializes the components of the Sign-In Menu.
	 */
	private void init() {
		// Initializing the noteMessage label.
		noteMessage = new JLabel();
		noteMessage.setForeground(new Color(255, 0, 0));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
		noteMessage.setHorizontalAlignment(SwingConstants.CENTER);
		noteMessage.setBackground(new Color(255, 255, 255));
		noteMessage.setOpaque(false);
		noteMessage.setBounds(50, 425, 300, 25);
		noteMessage.setBorder(null);
		noteMessageBounds = noteMessage.getBounds();
		// End of initializing the noteMessage label.
		
		// Initializing the comboBox.
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		comboBox.setEditable(true);
		comboBox.setSelectedItem(null);
		comboBox.setToolTipText("Enter your username or choose it from the drop-down menu");
		comboBox.setBounds(90, 250, 220, 50);
		comboBoxBounds = comboBox.getBounds();
		// End of initializing the comboBox.
		
		// Initializing the password box.
		passwordBox = new TextBox("Enter Your Password","",this);
		passwordBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		passwordBox.setBounds(90, 320, 220, 50);
		passwordBox.setToolTipText("Password field");
		passwordBoxBounds = passwordBox.getBounds();
		// End of initializing the password box.
		
		// Initializing the Sign-In button.
		signInButton = new JButton("Sign In");
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				String username;
				if (comboBox.getSelectedItem() == null) {
					username = "";
				}
				else {
					username = comboBox.getSelectedItem().toString();
				}
		        String password = passwordBox.getText().toString();
		        if (!DatabaseUtilities.usernameExistsInDatabase(username)) {
		        	AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		        	if (username.length() <= 18) {
		        		noteMessage.setText("User '" + username + "' doesn't exist!");
		        	}
		        	else {
		        		noteMessage.setText("User '" + username.substring(0, 15) + "...' doesn't exist!");
		        	}
		        	if (passwordBox.getText().trim().isEmpty()) {
		        		passwordBox.setText(passwordBox.getPlaceHolder());
		        	}
		        	return;
		        }
		        if (!DatabaseUtilities.getPasswordOfUser(username).equals(password)) {
		        	AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		        	noteMessage.setText("Incorrect Password! Try again!");
		        	if (passwordBox.getText().trim().isEmpty()) {
		        		passwordBox.setText(passwordBox.getPlaceHolder());
		        	}
		        	return;
		        }
		        DatabaseUtilities.currentUser = username;
		        AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
		        window.setTitle(window.windowName + " - Main Menu");
		        passwordBox.setText(passwordBox.getPlaceHolder());
		        window.showPanel("MAIN");
			}
		});
		signInButton.setBounds(210, 390, 100, 25);
		signInButton.setForeground(new Color(255, 255, 255));
		signInButton.setBackground(new Color(0, 128, 255));
		signInButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		signInButtonBounds = signInButton.getBounds();
		// End of initializing the Sign-In button.
		
		// Initializing the back button.
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				noteMessage.setText("");
				if (passwordBox.getText().trim().isEmpty()) {
	        		passwordBox.setText(passwordBox.getPlaceHolder());
	        	}
				window.showPanel("LOGIN");
				window.setTitle(window.windowName + " - Login Menu");
			}
		});
		backButton.setBounds(90, 390, 100, 25);
		backButton.setForeground(new Color(255, 255, 255));
		backButton.setBackground(new Color(0, 128, 255));
		backButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		backButtonBounds = backButton.getBounds();
		// End of initializing the back button.
		
		// Adding a mouse click listener to check for out-of-textbox-bounds clicks.
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				Point eventPoint = e.getPoint();
				if (!passwordBoxBounds.contains(eventPoint) && passwordBox.getText().trim().isEmpty()) {
					passwordBox.setText(passwordBox.getPlaceHolder());
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
	 * This function shows the components of Sign-In
	 * Menu by adding them to the Sign-In Panel.
	 */
	private void showStep() {
		removeAll();
		add(noteMessage);
		add(comboBox);
		add(passwordBox);
		add(signInButton);
		add(backButton);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function gets the current users' names from the database.
	 * It extracts them from the {@code users} ArrayList.
	 * 
	 * @return A {@code String[]} array that contains the users' names.
	 */
	String[] getUsernames() {
		String[] usernames = new String[users.size()];
	    for (int i = 0; i < users.size(); i++) {
	    	usernames[i] = (String) users.get(i).get("user_name");
	    }
	    return usernames;
	}
	
	/**
	 * This function updates the comboBox's list.
	 */
	public void update() {    
	    if (usersListUpdateFlag == true) {
	    	comboBox.removeAllItems();
    		for (String user : getUsernames()) {
    			comboBox.addItem(user);
    		}
    		usersListUpdateFlag = false;
	    }
	}
	
	@Override
	public void scaleUpFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		passwordBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, (int)(FontSize.TEXTBOX_SIZE.getValue()*Window.RATIO)));
		comboBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, (int)(FontSize.TEXTBOX_SIZE.getValue()*Window.RATIO)));
		signInButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)( FontSize.SMALL.getValue()*Window.RATIO)));
		backButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)( FontSize.SMALL.getValue()*Window.RATIO)));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, (int)(FontSize.NOTES_SIZE.getValue()*Window.RATIO)));
	}
	
	@Override
	public void scaleDownFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		passwordBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		comboBox.setFont(new Font(FontStyle.TEMPUS_SANS_ITC.getValue(), Font.PLAIN, FontSize.TEXTBOX_SIZE.getValue()));
		signInButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN,  FontSize.SMALL.getValue()));
		backButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN,  FontSize.SMALL.getValue()));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
	}
	
	@Override
	public void scaleComponents() {
		WindowUtilities.scale(title, titleBounds);
		WindowUtilities.scale(backgroundImage, backgroundImageBounds);
		WindowUtilities.scale(passwordBox, passwordBoxBounds);
		WindowUtilities.scale(comboBox, comboBoxBounds);
		WindowUtilities.scale(backButton, backButtonBounds);
		WindowUtilities.scale(signInButton, signInButtonBounds);
		WindowUtilities.scale(noteMessage, noteMessageBounds);
		if (Window.isFullScreen) {
			backgroundImage.setIcon(resizedBackgroundImage);
		}
		else {
			backgroundImage.setIcon(minimizedBackgroundImage);
		}
	}
}