package main;

import javax.swing.SwingUtilities;

import audio.AudioUtilities;
import database.MySQLConnector;
import media.MediaUtilities;
import windows.Window;

/**
 * The {@code Main} class is the entry
 * point of the program.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Main {
	/**
	 * This object is responsible of the communication between
	 * the program and the database.
	 */
	public static final MySQLConnector connector = new MySQLConnector();
  
	/**
	 * This variable specifies the run mode of the program,
	 * true for debug mode and false for external mode.
	 */
	public static final boolean ECLIPSE_RUN_MODE = true;
	
	public static void main(String[] args) {
		MediaUtilities.setupMediaResources();
		SwingUtilities.invokeLater(()->{
			new Window().setVisible(true);
		});
		AudioUtilities.AUDIO_RESOURCES.get("backgroundMusic").playIndefinitely();
	}

}