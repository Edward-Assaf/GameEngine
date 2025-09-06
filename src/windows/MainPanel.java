package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import audio.AudioUtilities;

import database.DatabaseUtilities;
import main.Main;
import mapControls.MapUtilities;
import media.MediaResource;
import media.MediaUtilities;
import windowComponents.FontSize;
import windowComponents.FontStyle;
import windowComponents.SliderBar;
import windowComponents.SliderBarUI;

/**
 * The {@code MainPanel} class initializes
 * the components of the Main Menu.
 * 
 * <p>It implements {@link javax.swing.JPanel} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Scalable {
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
	 * The New Game button that starts a new game.
	 */
	private JButton newGameButton;
	
	/**
	 * The original bounds of the New Game button.
	 */
	private Rectangle newGameButtonBounds;
	
	/**
	 * The Load Game button that loads an old game.
	 */
	private JButton loadGameButton;
	
	/**
	 * The original bounds of the Load Game button.
	 */
	private Rectangle loadGameButtonBounds;
	
	/**
	 * The Settings button that navigates to the Settings Menu.
	 */
	private JButton settingsButton;
	
	/**
	 * The original bounds of the Settings button.
	 */
	private Rectangle settingsButtonBounds;
	
	/**
	 * The Quit button that quits the game.
	 */
	private JButton quitButton;
	
	/**
	 * The original bounds of the Quit button.
	 */
	private Rectangle quitButtonBounds;
	
	/**
	 * The About-Us button that navigates to the About-Us Menu.
	 */
	private JButton aboutUsButton;
	
	/**
	 * The original bounds of the About-Us button.
	 */
	private Rectangle aboutUsButtonBounds;
	
	/**
	 * The Sign-Out button that logs-out from the current
	 * account and navigates back to the Login Menu.
	 */
	private JButton signOutButton;
	
	/**
	 * The original bounds of the Sign-Out button.
	 */
	private Rectangle signOutButtonBounds;
	
	/**
	 * The Settings button that navigates to the Settings Menu.
	 */
	private JButton settingsBackButton;
	
	/**
	 * The original bounds of the Settings button.
	 */
	private Rectangle settingsBackButtonBounds;
	
	/**
	 * The label components that displays
	 * the "Sound Effects" label.
	 */
	private JLabel soundEffectsLabel;
	
	/**
	 * The original bounds of the Sound Effects label.
	 */
	private Rectangle soundEffectsLabelBounds;
	
	/**
	 * The label components that displays
	 * the "Music" label.
	 */
	private JLabel musicLabel;
	
	/**
	 * The original bounds of the Music label.
	 */
	private Rectangle musicLabelBounds;
	
	/**
	 * The label components that displays
	 * the author Batoul name label.
	 */
	private JLabel authorBatoulLable;
	
	/**
	 * The original bounds of the author Batoul name label.
	 */
	private Rectangle authorBatoulLableBounds;
	
	/**
	 * The label components that displays
	 * the author Edward name label.
	 */
	private JLabel authorEdwardLable;
	
	/**
	 * The original bounds of the author Edward name label.
	 */
	private Rectangle authorEdwardLableBounds;
	
	/**
	 * The label components that displays
	 * the author Batoul id label.
	 */
	private JLabel authorBatoulIdLable;
	
	/**
	 * The original bounds of the author Batoul id label.
	 */
	private Rectangle authorBatoulIdLableBounds;
	
	/**
	 * The label components that displays
	 * the author Edward id label.
	 */
	private JLabel authorEdwardIdLable;
	
	/**
	 * The original bounds of the author Edward id label.
	 */
	private Rectangle authorEdwardIdLableBounds;
	
	/**
	 * The label components that displays
	 * the authors label.
	 */
	private JLabel authorsLabel;
	
	/**
	 * The original bounds of the authors label.
	 */
	private Rectangle authorsLabelBounds;
	
	/**
	 * The About-Us Back button that navigates back to the Main Menu.
	 */
	private JButton aboutUsBackButton;
	
	/**
	 * The original bounds of the About-Us Back button.
	 */
	private Rectangle aboutUsBackButtonBounds;
	
	/**
	 * Displays note messages depending on certain actions.
	 */
	private JLabel noteMessage;
	
	/**
	 * The original bounds of noteMessage.
	 */
	private Rectangle noteMessageBounds;
	
	/**
	 * The slider that controls the volume of sound effects.
	 */
	private SliderBar controllerSFX;
	
	/**
	 * The original bounds of the sound effects volume controller.
	 */
	private Rectangle controllerSFXBounds;
	
	/**
	 * The slider that controls the volume of music.
	 */
	private SliderBar controllerMusic;
	
	/**
	 * The original bounds of the music volume controller.
	 */
	private Rectangle controllerMusicBounds;
	
	/**
	 * The constructor of the {@code MainPanel}, it initializes
	 * this menu and its components.
	 */
	public MainPanel(Window window) {
		this.window = window;
		setSize(Window.windowMinimizedWidth, Window.windowMinimizedHeight);
		setLayout(null);
		init();
		showMainStep();
	}

	/**
	 * This function initializes the components of the Main Menu.
	 */
	private void init() {
		// Initializing the New Game button.
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				AudioUtilities.AUDIO_RESOURCES.get("backgroundMusic").terminate();
				AudioUtilities.AUDIO_RESOURCES.get("gameMusic").playIndefinitely();
				
				MediaUtilities.copyResourceWithTag(
					new MediaResource(DatabaseUtilities.ORIGINAL_LEVEL_JSON_NAME),
					DatabaseUtilities.currentUser
				);
				DatabaseUtilities.markNewGameFieldPressed(DatabaseUtilities.currentUser);
				DatabaseUtilities.resetGameDataForUser(DatabaseUtilities.currentUser);
				MapUtilities.refreshLevelData();
				MapUtilities.resetCamera();
				
				window.setTitle(window.windowName);
				window.showPanel("GAME");
			}
		});
		newGameButton.setForeground(new Color(255, 255, 255));
		newGameButton.setBackground(new Color(0, 128, 255));
		newGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		newGameButton.setToolTipText("New Game Button");
		newGameButton.setBounds(89, 202, 220, 50);
		newGameButtonBounds = newGameButton.getBounds();
		// End of initializing the New Game button.
		
		// Initializing the noteMessage label.
		noteMessage = new JLabel();
		noteMessage.setForeground(new Color(255, 0, 0));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
		noteMessage.setHorizontalAlignment(SwingConstants.CENTER);
		noteMessage.setBackground(new Color(255, 255, 255));
		noteMessage.setOpaque(false);
		noteMessage.setBounds(269, 272, 447, 50);
		noteMessage.setBorder(null);
		noteMessageBounds = noteMessage.getBounds();
		// End of initializing the noteMessage label.
		
		// Initializing the Load Game button.
		loadGameButton = new JButton("Load Game");
		loadGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
		        
		        String checkStatement = "SELECT * FROM users_levels WHERE user_name = '"
		          + DatabaseUtilities.currentUser + "';";
		        ArrayList<HashMap<String, Object>> checkResult = Main.connector.getQueryResult(checkStatement);
		        
		        if (checkResult.size() == 0) {
		          noteMessage.setText("Can't load game! There's no saved game!");
		          AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		          return;
		        }
		        else if (!MediaUtilities.resourceExists((String) checkResult.get(0).get("level_path"))) {
		          noteMessage.setText("Can't load game! There's no saved game!");
		          AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		          return;
		        }
		        else if (checkResult.get(0).get("new_game_pressed").equals("NO")) {
		          noteMessage.setText("Can't load game! There's no saved game!");
		          AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
		          return;
		        }
		        
		        AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
		        AudioUtilities.AUDIO_RESOURCES.get("backgroundMusic").terminate();
		        AudioUtilities.AUDIO_RESOURCES.get("gameMusic").playIndefinitely();
		        
		        MapUtilities.refreshLevelData();
		        window.setTitle(window.windowName);
		        window.showPanel("GAME");
			}
		});
		loadGameButton.setForeground(new Color(255, 255, 255));
		loadGameButton.setBackground(new Color(0, 128, 255));
		loadGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		loadGameButton.setToolTipText("Load Game Button");
		loadGameButton.setBounds(89, 272, 220, 50);
		loadGameButtonBounds = loadGameButton.getBounds();
		// End of initializing the Load Game button.
		
		// Initializing the Settings button.
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				window.setTitle(window.windowName + " - Main Menu - Settings");
				showSettingsStep();
			}
		});
		settingsButton.setForeground(new Color(255, 255, 255));
		settingsButton.setBackground(new Color(0, 128, 255));
		settingsButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		settingsButton.setToolTipText("Settings Button");
		settingsButton.setBounds(89, 342, 220, 50);
		settingsButtonBounds = settingsButton.getBounds();
		// End of initializing the Settings button.
		
		// Initializing the Quit button.
		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
				System.out.println("Quitting The Game...");
				System.exit(0);
			}
		});
		quitButton.setForeground(new Color(255, 255, 255));
		quitButton.setBackground(new Color(0, 128, 255));
		quitButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		quitButton.setToolTipText("Quit Button");
		quitButton.setBounds(89, 412, 220, 50);
		quitButtonBounds = quitButton.getBounds();
		// End of initializing the Quit button.
		
		// Initializing the About-Us button.
		aboutUsButton = new JButton("About Us");
		aboutUsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				System.out.println("Navigating To The About-Us Menu...");
				window.setTitle(window.windowName + " - Main Menu - About Us");
				showAboutUsStep();
			}
		});
		aboutUsButton.setForeground(new Color(255, 255, 255));
		aboutUsButton.setBackground(new Color(0, 128, 255));
		aboutUsButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		aboutUsButton.setToolTipText("About-Us Button");
		aboutUsButton.setBounds(828, 484, 131, 29);
		aboutUsButtonBounds = aboutUsButton.getBounds();
		// End of initializing the About-Us button.
		
		// Initializing the Sign-Out button.
		signOutButton = new JButton("Sign Out");
		signOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteMessage.setText("");
				AudioUtilities.AUDIO_RESOURCES.get("cancelSound").playOnce();
				System.out.println("Navigating To The Login Menu...");
				DatabaseUtilities.currentUser = "";
				window.setTitle(window.windowName + " - Login Menu");
				window.showPanel("LOGIN");
			}
		});
		signOutButton.setForeground(new Color(255, 255, 255));
		signOutButton.setBackground(new Color(0, 128, 255));
		signOutButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		signOutButton.setToolTipText("Sign-Out Button");
		signOutButton.setBounds(828, 444, 131, 29);
		signOutButtonBounds = signOutButton.getBounds();
		// End of initializing the Sign-Out button.
		
		// Initializing the Settings Back Button button.
		settingsBackButton = new JButton("Back");
		settingsBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				System.out.println("Navigating To The Main Menu...");
				window.setTitle(window.windowName + " - Main Menu");
				showMainStep();
			}
		});
		settingsBackButton.setForeground(new Color(255, 255, 255));
		settingsBackButton.setBackground(new Color(0, 128, 255));
		settingsBackButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		settingsBackButton.setToolTipText("Back Button");
		settingsBackButton.setBounds(63, 435, 178, 47);
		settingsBackButtonBounds = settingsBackButton.getBounds();
		// End of initializing the Settings Back Button button.
		
		// Initializing the Sound Effects Bar Label.
		soundEffectsLabel = new JLabel();
		soundEffectsLabel.setForeground(new Color(255, 255, 255));
		soundEffectsLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		soundEffectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		soundEffectsLabel.setText("Sound Effects");
		soundEffectsLabel.setOpaque(false);
		soundEffectsLabel.setBounds(175, 163, 200, 47);
		soundEffectsLabel.setBorder(null);
		soundEffectsLabelBounds = soundEffectsLabel.getBounds();
		// End of initializing the Sound Effects Bar Label.
		
		// Initializing the Music Bar Label.
		musicLabel = new JLabel();
		musicLabel.setForeground(new Color(255, 255, 255));
		musicLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		musicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		musicLabel.setText("Music");
		musicLabel.setOpaque(false);
		musicLabel.setBounds(175, 236, 200, 47);
		musicLabel.setBorder(null);
		musicLabelBounds = musicLabel.getBounds();
		// End of initializing the Music Bar Label.
		
		// Initializing the author Batoul Label.
		authorBatoulLable = new JLabel();
		authorBatoulLable.setForeground(new Color(255, 255, 255));
		authorBatoulLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorBatoulLable.setHorizontalAlignment(SwingConstants.CENTER);
		authorBatoulLable.setText("Batoul Khaleel");
		authorBatoulLable.setOpaque(false);
		authorBatoulLable.setBounds(149, 244, 306, 70);
		authorBatoulLable.setBorder(null);
		authorBatoulLableBounds = authorBatoulLable.getBounds();;
		// End of initializing the author Batoul Label.
		
		// Initializing the author Edward Label.
		authorEdwardLable = new JLabel();
		authorEdwardLable.setForeground(new Color(255, 255, 255));
		authorEdwardLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorEdwardLable.setHorizontalAlignment(SwingConstants.CENTER);
		authorEdwardLable.setText("Edward Assaf");
		authorEdwardLable.setOpaque(false);
		authorEdwardLable.setBounds(529, 244, 306, 70);
		authorEdwardLable.setBorder(null);
		authorEdwardLableBounds = authorEdwardLable.getBounds();
		// End of initializing the author Edward Label.
		
		//Initializing the author Batoul Id Label.
		authorBatoulIdLable = new JLabel();
		authorBatoulIdLable.setForeground(new Color(255, 255, 255));
		authorBatoulIdLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorBatoulIdLable.setHorizontalAlignment(SwingConstants.CENTER);
		authorBatoulIdLable.setText("6261");
		authorBatoulIdLable.setOpaque(false);
		authorBatoulIdLable.setBounds(149, 325, 306, 70);
		authorBatoulIdLable.setBorder(null);
		authorBatoulIdLableBounds = authorBatoulIdLable.getBounds();
		// End of initializing the author Batoul Id Label.
		
		// Initializing the author Edward Id Label.
		authorEdwardIdLable = new JLabel();
		authorEdwardIdLable.setForeground(new Color(255, 255, 255));
		authorEdwardIdLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorEdwardIdLable.setHorizontalAlignment(SwingConstants.CENTER);
		authorEdwardIdLable.setText("6682");
		authorEdwardIdLable.setOpaque(false);
		authorEdwardIdLable.setBounds(529, 325, 306, 70);
		authorEdwardIdLable.setBorder(null);
		authorEdwardIdLableBounds = authorEdwardIdLable.getBounds();
		// End of initializing the author Edward Id Label.
		
		// Initializing the authors Label.
		authorsLabel = new JLabel();
		authorsLabel.setForeground(new Color(255, 255, 255));
		authorsLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		authorsLabel.setText("Authors :");
		authorsLabel.setOpaque(false);
		authorsLabel.setBounds(347, 163, 306, 70);
		authorsLabel.setBorder(null);
		authorsLabelBounds = authorsLabel.getBounds();
		// End of initializing the authors Label.
		
		// Initializing the About-Us back button.
		aboutUsBackButton = new JButton("Back");
		aboutUsBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				window.setTitle(window.windowName + " - Main Menu");
				showMainStep();
			}
		});
		aboutUsBackButton.setForeground(new Color(255, 255, 255));
		aboutUsBackButton.setBackground(new Color(0, 128, 255));
		aboutUsBackButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		aboutUsBackButton.setToolTipText("Sign-Out Button");
		aboutUsBackButton.setBounds(828, 484, 131, 29);
		aboutUsBackButtonBounds = aboutUsBackButton.getBounds();
		// End of initializing the About-Us back button.
		
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
		
		// Initializing the sound effects volume controller.
		controllerSFX = new SliderBar(0, 100, (int) (100 * AudioUtilities.DEFAULT_AUDIO_VOLUME));
		controllerSFX.setOpaque(false);
		controllerSFX.setPaintTicks(false);
		controllerSFX.setPaintLabels(false);
		controllerSFX.setBounds(421, 140, 358, 96);
		controllerSFXBounds = controllerSFX.getBounds();
		controllerSFX.setUI(new SliderBarUI(controllerSFX));
		controllerSFX.setCustomImplementer(new SliderBar.CustomPressedStateImplementer() {
			@Override
			public void ratioImplementer(double ratio) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				AudioUtilities.setAudioResourcesVolume_SFX(ratio);
			}
		});
		// End of initializing the sound effects volume controller.
		
		// Initializing the music volume controller.
		controllerMusic = new SliderBar(0, 100, (int) (100 * AudioUtilities.DEFAULT_AUDIO_VOLUME));
		controllerMusic.setOpaque(false);
		controllerMusic.setPaintTicks(false);
		controllerMusic.setPaintLabels(false);
		controllerMusic.setBounds(421, 252, 358, 20);
		controllerMusicBounds = controllerMusic.getBounds();
		controllerMusic.setUI(new SliderBarUI(controllerMusic));
		controllerMusic.setCustomImplementer(new SliderBar.CustomPressedStateImplementer() {
			@Override
			public void ratioImplementer(double ratio) {
				AudioUtilities.AUDIO_RESOURCES.get("clickSound").playOnce();
				AudioUtilities.setAudioResourcesVolume_Music(ratio);
			}
		});
		// End of initializing the music volume controller.
	}
	
	/**
	 * This function shows the components of Main
	 * Menu by adding them to the Main Panel.
	 */
	private void showMainStep() {	
		removeAll();
		add(newGameButton);
		add(loadGameButton);
		add(settingsButton);
		add(quitButton);
		add(aboutUsButton);
		add(signOutButton);
		add(noteMessage);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function shows the components of Settings
	 * Menu by adding them to the Main Panel.
	 */
	private void showSettingsStep() {
		removeAll();
		add(settingsBackButton);
		add(controllerSFX);
		add(controllerMusic);
		add(soundEffectsLabel);
		add(musicLabel);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	/**
	 * This function shows the components of About-Us
	 * Menu by adding them to the Main Panel.
	 */
	private void showAboutUsStep() {
		removeAll();
		add(authorBatoulLable);
		add(authorEdwardLable);
		add(authorBatoulIdLable);
		add(authorEdwardIdLable);
		add(authorsLabel);
		add(aboutUsBackButton);
		add(title);
		add(backgroundImage);
		revalidate();
		repaint();
	}
	
	@Override
	public void scaleUpFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.LARGE.getValue()*Window.RATIO)));
		newGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		loadGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		settingsButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		quitButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		aboutUsButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		signOutButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
		settingsBackButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		soundEffectsLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		musicLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, (int)(FontSize.NOTES_SIZE.getValue()*Window.RATIO)));
		authorBatoulLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		authorEdwardLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		authorBatoulIdLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		authorEdwardIdLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		authorsLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, (int)(FontSize.MEDIUM.getValue()*Window.RATIO)));
		aboutUsBackButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, (int)(FontSize.SMALL.getValue()*Window.RATIO)));
	}
	
	@Override
	public void scaleDownFontSizes() {
		title.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.LARGE.getValue()));
		newGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		loadGameButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		settingsButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		quitButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		aboutUsButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		signOutButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
		settingsBackButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		soundEffectsLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		musicLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		noteMessage.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.BOLD, FontSize.NOTES_SIZE.getValue()));
		authorBatoulLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorEdwardLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorBatoulIdLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorEdwardIdLable.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		authorsLabel.setFont(new Font(FontStyle.KRISTEN_ITC.getValue(), Font.PLAIN, FontSize.MEDIUM.getValue()));
		aboutUsBackButton.setFont(new Font(FontStyle.CASCADIA_CODE.getValue(), Font.PLAIN, FontSize.SMALL.getValue()));
	}
	
	@Override
	public void scaleComponents() {
		WindowUtilities.scale(title, titleBounds);
		WindowUtilities.scale(backgroundImage, backgroundImageBounds);
		WindowUtilities.scale(newGameButton, newGameButtonBounds);
		WindowUtilities.scale(loadGameButton, loadGameButtonBounds);
		WindowUtilities.scale(settingsButton, settingsButtonBounds);
		WindowUtilities.scale(quitButton, quitButtonBounds);
		WindowUtilities.scale(aboutUsButton, aboutUsButtonBounds);
		WindowUtilities.scale(controllerMusic, controllerMusicBounds);
		WindowUtilities.scale(controllerSFX, controllerSFXBounds);
		WindowUtilities.scale(signOutButton, signOutButtonBounds);
		WindowUtilities.scale(settingsBackButton, settingsBackButtonBounds);
		WindowUtilities.scale(soundEffectsLabel, soundEffectsLabelBounds);
		WindowUtilities.scale(musicLabel, musicLabelBounds);
		WindowUtilities.scale(noteMessage, noteMessageBounds);
		WindowUtilities.scale(authorBatoulLable, authorBatoulLableBounds);
		WindowUtilities.scale(authorEdwardLable, authorEdwardLableBounds);
		WindowUtilities.scale(authorBatoulIdLable, authorBatoulIdLableBounds);
		WindowUtilities.scale(authorEdwardIdLable, authorEdwardIdLableBounds);
		WindowUtilities.scale(authorsLabel, authorsLabelBounds);
		WindowUtilities.scale(aboutUsBackButton, aboutUsBackButtonBounds);
		if (Window.isFullScreen) {
			backgroundImage.setIcon(resizedBackgroundImage);
		}
		else {
			backgroundImage.setIcon(minimizedBackgroundImage);
		}
	}
}