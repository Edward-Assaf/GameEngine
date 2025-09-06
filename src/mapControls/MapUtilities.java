package mapControls;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import camera.Perspective;
import database.DatabaseUtilities;
import gameObjects.DynamicObject;
import gameObjects.GameObjectUtilities;
import gameObjects.Monster;
import gameObjects.Player;
import main.GamePanel;
import main.Main;
import media.MediaResource;
import windows.Window;

/**
 * The {@code MapUtilities} class defines the necessary
 * definitions to deal with rendering the map with appropriate
 * camera settings.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class MapUtilities {
	/**
	 * The level to be rendered.
	 */
	public static Level level = new Level(DatabaseUtilities.ORIGINAL_LEVEL_JSON_NAME);
	
	/**
	 * The preffered tile width setting of the camera.
	 */
	public static final int CAMERA_WIDTH = 30;
	
	/**
	 * The preffered tile size setting of the camera.
	 */
	public static final int CAMERA_TILE_SIZE = Window.windowMinimizedWidth / CAMERA_WIDTH;
	
	/**
	 * The preffered tile height setting of the camera.
	 */
	public static final int CAMERA_HEIGHT = Window.windowMinimizedHeight / CAMERA_TILE_SIZE;
	
	/**
	 * The ratio of the camera width to the width of the level.
	 */
	public static final double CAMERA_WIDTH_RATIO = CAMERA_WIDTH / (double)(level.getWidth());
	
	/**
	 * The ratio of the camera height to the height of the level.
	 */
	public static final double CAMERA_HEIGHT_RATIO = CAMERA_HEIGHT / (double)(level.getHeight());
	
	/**
	 * The camera to be used with this level.
	 * @see camera.Perspective
	 */
	public static Perspective camera = new Perspective (
		new Rectangle(0, 0, level.getStaticMap().getWidth(), level.getStaticMap().getHeight()),
		new Rectangle(
			0, 0,
			(int) (CAMERA_WIDTH_RATIO * level.getStaticMap().getWidth()),
			(int) (CAMERA_HEIGHT_RATIO * level.getStaticMap().getHeight())
		)
	);
	
	/**
	 * This function renders this level using {@link GamePanel}'s
	 * painter (a {@code Graphics2D} object), and it only renders
	 * the section visible by the camera.
	 * 
	 * @param g  The painter provided by the game panel.
	 */
	public static void renderMap(Graphics2D g) {
		int width = (Window.isFullScreen)? Window.deviceScreenWidth : Window.windowMinimizedWidth;
		int height = (Window.isFullScreen)? Window.deviceScreenHeight : Window.windowMinimizedHeight;
		g.drawImage(
			level.getStaticMap().getSubimage(
				camera.getBounds().x,
				camera.getBounds().y,
				camera.getBounds().width,
				camera.getBounds().height
			),
			0, 0, width, height, null
		);
		g.drawImage(
			level.getDynamicMap().getSubimage(
				camera.getBounds().x,
				camera.getBounds().y,
				camera.getBounds().width,
				camera.getBounds().height
			),
			0, 0, width, height, null
		);
	}
	
	/**
	 * This function refreshes the {@link #level} object by looking
	 * for the user's JSON file name in the database and re-initializing
	 * the {@link #level} with it. It also re-initializes the array of
	 * dynamic objects and their states.
	 * 
	 * <p>When this function is called, any dynamic-layer-related update
	 * done in {@link GamePanel} is paused temporarily until the refreshing
	 * process is complete, to ensure consistency.</p>
	 */
	public static void refreshLevelData() {
		GamePanel.gameUpdatable = false;
		if (DatabaseUtilities.currentUser.equals("")) {
			return;
		}
		String statement = "SELECT level_path FROM users_levels WHERE user_name = '"
			+ DatabaseUtilities.currentUser + "';";
		String JSONFileName = (String) Main.connector.getQueryResult(statement).get(0).get("level_path");
		level = new Level(JSONFileName);
		GameObjectUtilities.dynamicObjects = new DynamicObject[] {
			new Player(2, "playerTileSet", 1, "player"),
			new Monster(2, "ninjaA", 1, "monster"),
			new Monster(2, "ninjaB", 1, "monster2")
		};
		GameObjectUtilities.dynamicObjectsStates = new boolean[GameObjectUtilities.dynamicObjects.length];
		for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
			int health = DatabaseUtilities.getGameDataAttributeValueForUser(
				DatabaseUtilities.currentUser, GameObjectUtilities.dynamicObjects[i].getName() + "Health"
			);
			if (health > 0) {
				GameObjectUtilities.dynamicObjectsStates[i] = true;
			}
			else {
				GameObjectUtilities.dynamicObjectsStates[i] = false;
			}
		}
		GamePanel.gameUpdatable = true;
	}

	/**
	 * This function resets the {@link #camera} to initial position.
	 */
	public static void resetCamera() {
		camera = new Perspective (
			new Rectangle(0, 0, level.getStaticMap().getWidth(), level.getStaticMap().getHeight()),
			new Rectangle(
				0, 0,
				(int) (CAMERA_WIDTH_RATIO * level.getStaticMap().getWidth()),
				(int) (CAMERA_HEIGHT_RATIO * level.getStaticMap().getHeight())
			)
		);
	}
	
	/**
	 * This function returns the current layer data of the dynamic
	 * layer in the form of a 1D array, to be written in the JSON
	 * file in the saving progress step (using {@link #saveData()}).
	 * 
	 * @return  The layer data array of the dynamic layer, in 1D form.
	 */
	public static int[] getNewLayerData() {
		int[][] data2D = level.getLevelLayers()[2].getLayerData();
		int[] data1D = new int[level.getWidth() * level.getHeight()];
		int index = 0;
		for (int i = 0; i < level.getHeight(); i++) {
			for(int j = 0; j<level.getWidth(); j++) {
				data1D[index++] = data2D[i][j];
			}
		}
		return data1D;
	}
	
	/**
	 * This function returns a JSONArray object representing
	 * the updated 'objectsOffsets' JSONArray which is found
	 * in the dynamic layer's JSONObject. It is used in
	 * {@link #saveData()} to write the updated row and column
	 * offsets of every dynamic object upon saving.
	 * 
	 * @return  The updated 'objectsOffsets' JSONArray
	 */
	public static JSONArray getNewLayerObjectOffsets() {
		JSONArray objectOffsets = new JSONArray();
		for (DynamicObject object : GameObjectUtilities.dynamicObjects) {
			JSONObject json = new JSONObject();
			json.put("name", object.getName());
			json.put("row", object.getRowPixelOffset());
			json.put("column", object.getColumnPixelOffset());
			objectOffsets.put(json);
		}
		return objectOffsets;
	}
	
	/**
	 * This function is used to save the progress of the game
	 * for the current user. When this function is called, any
	 * dynamic-layer-related update done in {@link GamePanel}
	 * is paused temporarily until the writing process on the
	 * JSON file is complete, to ensure consistency.
	 */
	public static void saveData() {
		if (DatabaseUtilities.currentUser.equals("")) {
			return;
		}
		// Game is not updatable when in saving-progress mode.
		GamePanel.gameUpdatable = false;
		// Updating the JSON object with new data before writing it to the JSON file.
		JSONObject levelObject = level.getLevelObject();
		JSONObject layerObject = levelObject.getJSONArray("layers").getJSONObject(2);
		layerObject.remove("data");
		layerObject.put("data", getNewLayerData());
		layerObject.remove("objectsOffsets");
		layerObject.put("objectsOffsets", getNewLayerObjectOffsets());
		// Retrieving the JSON file name for the current user.
		String statement = "SELECT level_path FROM users_levels WHERE user_name = '"
			+ DatabaseUtilities.currentUser + "';";
		String JSONFileName = (String) Main.connector.getQueryResult(statement).get(0).get("level_path");
		// Writing the updated JSONObject to the JSON file, specified by the JSON file name of the user.
		try {
			FileWriter writer = new FileWriter(new MediaResource(JSONFileName).getResourceAbsolutePath());
			writer.write(levelObject.toString(4));
			writer.close();
		} catch (IOException e) {
			System.out.println("JSON file writing in save step failed");
		}
		// Saving the current values of dynamic objects to the database, then refreshing level data.
		DatabaseUtilities.updateGameDataAttributeValueForUser(DatabaseUtilities.currentUser);
		refreshLevelData();
		// After all processes are complete, the game can be updatable again.
		GamePanel.gameUpdatable = true;
	}

}