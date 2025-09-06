package database;

import java.util.ArrayList;
import java.util.HashMap;

import gameObjects.GameObjectUtilities;
import gameObjects.Monster;
import gameObjects.Player;
import main.Main;
import media.MediaResource;
import media.MediaUtilities;
import windows.SignInPanel;

/**
 * The {@code DatabaseUtilities} class offers helper definitions
 * and built-in functionalities, used to manage the database.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class DatabaseUtilities {
	/**
	 * A {@code String} that determines the allowed characters in a username.
	 */
	public static final String ALLOWED_CHARACTERS_IN_USERNAMES = "abcdefghijklmnopqrstuvwxyz0123456789";
	
	/**
	 * This {@code String} is used to track who the current user is.
	 */
	public static String currentUser = "";
	
	/**
	 * This {@code String} defines the basis for how any value found in
	 * 'level_path' column of the Users Levels table should be derived.
	 */
	public static final String ORIGINAL_LEVEL_JSON_NAME = "mapBatchOne.json";
  
	/**
	 * This function builds the SQL CREATE statement for the Accounts table.
	 *	 
	 * @return A {@code String}, the CREATE statement for the Accounts table in SQL format.
	 */
	public static String getAccountsTableCreateStatement() {
		Table accounts = new Table("accounts");
		accounts.addColumn(new Column("user_name","VARCHAR(20)","PRIMARY KEY"));
		accounts.addColumn(new Column("user_password","VARCHAR(30)","NOT NULL"));
		accounts.addColumn(new Column("register_date","DATE","NOT NULL"));
		accounts.addConstraint("CHECK(LENGTH(user_name) > 2)");
		accounts.addConstraint("CHECK(LENGTH(user_password) > 7)");
		return accounts.getCreateStatement();
	}
	
	/**
	 * This function builds the SQL CREATE statement for the Users Levels table.
	 *	 
	 * @return A {@code String}, the CREATE statement for the Users Levels table in SQL format.
	 */
	public static String getUsersLevelsTableCreateStatement() {
		Table usersLevels = new Table("users_levels");
		usersLevels.addColumn(new Column("user_name", "VARCHAR(20)","PRIMARY KEY"));
		usersLevels.addColumn(new Column("level_path","VARCHAR(30)","NOT NULL"));
		usersLevels.addColumn(new Column("new_game_pressed","VARCHAR(5)","DEFAULT 'NO'"));
		return usersLevels.getCreateStatement();
	}
	
	/**
	 * This function builds the SQL CREATE statement for the Game Data table.
	 *	 
	 * @return A {@code String}, the CREATE statement for the Game Data table in SQL format.
	 */
	public static String getGameDataTableCreateStatement() {
		Table gameData = new Table("game_data");
		gameData.addColumn(new Column("user_name", "VARCHAR(20)", "NOT NULL"));
		gameData.addColumn(new Column("attribute_name", "VARCHAR(20)", "NOT NULL"));
		gameData.addColumn(new Column("attribute_value", "INT", "NOT NULL"));
		return gameData.getCreateStatement();
	}
  
	/**
	 * This function takes a username, and builds a SELECT statement in SQL format
	 * to get the password of this username, using a {@link database.MySQLConnector} object.
	 * 
	 * @param username  The name of the user.
	 * 
	 * @return A {@code String}, the password of the specified user. If the user does
	 * not exist, null is returned instead.
	 */
	public static String getPasswordOfUser(String username) {
		if (!usernameExistsInDatabase(username)) {
			return null;
		}
		String statement = "SELECT user_password FROM accounts WHERE user_name = '" + username + "';";
		ArrayList<HashMap<String, Object>> result = Main.connector.getQueryResult(statement);
		return (String) result.get(0).get("user_password");
	}
  
	/**
	 * This function takes a username, then checks if this username is valid.
	 * The valid username should be 3-20 characters long,
	 * and contains letters & numbers only.
	 * 
	 * @param username  The name of the user.
	 * 
	 * @return A boolean value, true if the username is valid, false otherwise.
	 */
	public static boolean isValidUsername(String username) {
		if ((username.length() < 3) || (username.length() > 20)) {
			return false;
		}
		for (int i = 0; i < username.length(); i++) {
			if (ALLOWED_CHARACTERS_IN_USERNAMES.indexOf(username.toLowerCase().charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
  
	/**
	 * This function takes a password, then checks if this password is valid.
	 * The valid password should be 8-30 characters long.
	 * 
	 * @param password  A password.
	 * 
	 * @return  A boolean value, true if the password is valid, false otherwise.
	 */
	public static boolean isValidPassword(String password) {
		return ((password.length() >= 8) && (password.length() <= 30));
	}
  
	/**
	 * This function takes a username, then checks if it exists in the database.
	 * It creates a SELECT statement in SQL format to get the existing usernames
	 * in the database, using a {@link database.MySQLConnector} object, then checks
	 * if the user is among them.
	 * 
	 * @param username  A username.
	 * 
	 * @return  A boolean value, true if the username exists in the database, false otherwise.
	 */
	public static boolean usernameExistsInDatabase(String username) {
		String statement = "SELECT user_name FROM accounts WHERE user_name = '" + username + "';";
		ArrayList<HashMap<String, Object>> result = Main.connector.getQueryResult(statement);
		for (int i = 0; i < result.size(); i++) {
			if (((String)result.get(i).get("user_name")).equals(username)) {
				return true;
			}
		}
		return false;
	} 
	
	/**
	 * This function takes the data of some user (username, password & account
	 * creation date), and inserts that into the database in the Accounts table
	 * using a {@link database.MySQLConnector} object, only if the user doesn't
	 * already exist.
	 * 
	 * @param username  The username of the user.
	 * @param password  The password of the user.
	 * @param date  The date of the user's account creation.
	 */
	public static void insertUserDataInAccounts(String username, String password, String date) {
		if (usernameExistsInDatabase(username)) {
			return;
		}
		String statement = "INSERT INTO ACCOUNTS VALUES ('" + username +"','" 
			+ password + "','" + date + "');";
        Main.connector.executeStatement(statement);
        SignInPanel.users = Main.connector.getQueryResult(SignInPanel.USERS_STATEMENT);
        SignInPanel.usersListUpdateFlag = true;
	}
	
	/**
	 * This function copies the main JSON file defined by {@link #ORIGINAL_LEVEL_JSON_NAME}
	 * to create a fresh and editable JSON file for a user. The username of the user is used
	 * to make the name of the new file unique. These JSON file copies are used to store the
	 * progress of individual players.
	 * 
	 * @param username  The username of the user to create a JSON file copy for.
	 */
	public static void insertNewJSONFileForUser(String username) {
		String newJSONFileName = DatabaseUtilities.ORIGINAL_LEVEL_JSON_NAME.substring(
			0, DatabaseUtilities.ORIGINAL_LEVEL_JSON_NAME.length() - 5) + username + ".json";
		
		MediaUtilities.copyResourceWithTag(new MediaResource(ORIGINAL_LEVEL_JSON_NAME), username);
		
		String statement = "INSERT INTO users_levels(user_name, level_path) "
			+ "VALUES('" + username + "','" + newJSONFileName + "');";
		Main.connector.executeStatement(statement);
	}
	
	/**
	 * This function is used to track which players have clicked the 'New Game' button
	 * at least once. By calling this function and specifying a username, the 'new_game_pressed'
	 * field in the Users Levels table is marked with 'YES' for that user, signaling
	 * that they have created at least one 'New Game' before.
	 * 
	 * @param username  The username to set 'new_game_pressed' to 'YES' for.
	 */
	public static void markNewGameFieldPressed(String username) {
		if (!usernameExistsInDatabase(username)) {
			return;
		}
		String statement = "UPDATE users_levels SET new_game_pressed = 'YES' WHERE user_name = '"
			+ username + "';";
		Main.connector.executeStatement(statement);
	}
	
	/**
	 * This function is used to track which players finished or lost the game.
	 * By calling this function and specifying a username, the 'new_game_pressed'
	 * field in the Users Levels table is marked with 'NO' for that user, signaling
	 * that they have finished or lost game.
	 * 
	 * @param username  The username to set 'new_game_pressed' to 'NO' for.
	 */
	public static void markGameOver(String username) {
		if (!usernameExistsInDatabase(username)) {
			return;
		}
		String statement = "UPDATE users_levels SET new_game_pressed = 'NO' WHERE user_name = '"
			+ username + "';";
		Main.connector.executeStatement(statement);
	}
	
	/**
	 * This function initializes the Game Data table with the
	 * default values for a specified user. The 'attribute_name'
	 * field's values are retrieved from every individual dynamic
	 * object.
	 * 
	 * @param username  The username to initialize the Game Data table for.
	 */
	public static void initializeGameDataForUser(String username) {
		String statement = "";
		for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
			if (GameObjectUtilities.dynamicObjects[i] instanceof Player) {
			statement = "INSERT INTO game_data VALUES('" + username + "', '"
					+ GameObjectUtilities.dynamicObjects[i].getName() + "Health', 7);";
			}
			else if (GameObjectUtilities.dynamicObjects[i] instanceof Monster) {
				statement = "INSERT INTO game_data VALUES('" + username + "', '"
					+ GameObjectUtilities.dynamicObjects[i].getName() + "Health', 3);";
			}
			Main.connector.executeStatement(statement);
		}
	}
	
	/**
	 * This function resets Game Data table to the default state prior
	 * to any edit. It is done by going through all dynamic objects and
	 * resetting their field values in the table.
	 * 
	 * @param username  The user to reset the Game Data table for.
	 */
	public static void resetGameDataForUser(String username) {
		String statement = "";
		for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
			if (GameObjectUtilities.dynamicObjects[i] instanceof Player) {
				statement = "UPDATE game_data SET attribute_value = 7 WHERE (user_name = '" + username 
					+ "') AND (attribute_name = '" + GameObjectUtilities.dynamicObjects[i].getName() + "Health');";
			}
			else if (GameObjectUtilities.dynamicObjects[i] instanceof Monster) {
				statement = "UPDATE game_data SET attribute_value = 3 WHERE (user_name = '" + username 
					+ "') AND (attribute_name = '" + GameObjectUtilities.dynamicObjects[i].getName() + "Health');";
			}
			Main.connector.executeStatement(statement);
		}
	}
	
	/**
	 * This function updates the Game Data table for a specific user
	 * with the current values of the dynamic objects in the game.
	 * 
	 * @param username  The username to update the Game Data table for.
	 */
	public static void updateGameDataAttributeValueForUser(String username) {
		for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
			int health = GameObjectUtilities.dynamicObjects[i].getHealth();
			String statement = "UPDATE game_data SET attribute_value = " + health +
				" WHERE (user_name = '" + username + "') AND (attribute_name = '" + 
				GameObjectUtilities.dynamicObjects[i].getName() + "Health');";
			Main.connector.executeStatement(statement);
		}
	}
	
	/**
	 * This function returns the value of a specific attribute
	 * from the Game Data table for a specified user.
	 * 
	 * @param username  The username to get the attribute value for.
	 * @param attributeName  The attribute name to get the value of.
	 * 
	 * @return  The value of the specified attribute.
	 */
	public static int getGameDataAttributeValueForUser(String username, String attributeName) {
		String statement = "SELECT attribute_value FROM game_data WHERE (user_name = '" 
			+ username + "') AND (attribute_name = '" + attributeName + "');";
		ArrayList<HashMap<String, Object>> result = Main.connector.getQueryResult(statement);
		if (result.size() == 0) {
			System.out.println("Attribute not found");
			return -1;
		}
		return (int) result.get(0).get("attribute_value");
	}
}