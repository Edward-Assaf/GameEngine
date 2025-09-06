package mapControls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import gameObjects.DynamicObject;
import gameObjects.GameObjectUtilities;
import gameObjects.Monster;
import gameObjects.Player;
import media.MediaResource;

/**
 * The {@code Level} class defines the necessary
 * definitions to represent a sub-map (level).
 * 
 * @see LevelLayer
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Level {
	/**
	 * The JSON object corresponding to the JSON file that
	 * represents this sub-map.
	 */
	private JSONObject levelObject;
	
	/**
	 * This stores the static part of the sub-map as a cached
	 * image.
	 */
	private BufferedImage staticMap;
	
	/**
	 * The pixel size of every individual tile in this sub-map.
	 */
	private int tileSize;
	
	/**
	 * The number of tiles that span horizontally across the sub-map (width).
	 */
	private int width;
	
	/**
	 * The number of tiles that span vertically across the sub-map (height).
	 */
	private int height;
	
	/**
	 * The list of {@link LevelLayer} objects that represent the
	 * different layers of this sub-map.
	 */
	private LevelLayer[] layers;
	
	/**
	 * The list of {@link TileSet} objects that represent the
	 * different tilesets that can be used to draw tiles in this
	 * sub-map.
	 */
	private TileSet[] tileSets;
	
	/**
	 * This constructor constructs the {@code Level} object by providing
	 * the name of the JSON file. This constructor retrieves all of the
	 * attributes of the sub-map from the file.
	 * 
	 * @param levelJSONPath  The name of the JSON file, without path.
	 */
	public Level(String levelJSONPath) {
		String path = null;
		try {
			path = new String(Files.readAllBytes(Paths.get(new MediaResource(levelJSONPath).getResourceAbsolutePath())));
			levelObject = new JSONObject(path);
		} catch (IOException e) {
			System.out.println("Level JSON file load failed");
		}
		initializeLevelAttributes();
	}
	
	/**
	 * Getter for {@link #width}.
	 * 
	 * @return  The width of the sub-map (in tiles).
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Getter for {@link #height}.
	 * 
	 * @return  The height of the sub-map (in tiles).
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * This function returns the length of the {@link #layers} array.
	 * 
	 * @return  The layers count.
	 */
	public int getLayerCount() {
		return layers.length;
	}
	
	/**
	 * Getter for {@link #layers}.
	 * 
	 * @return  The layers of the sub-map.
	 */
	public LevelLayer[] getLevelLayers() {
		return layers;
	}
	
	/**
	 * Getter for {@link #tileSets}.
	 * 
	 * @return  The tilesets of the sub-map.
	 */
	public TileSet[] getTileSets() {
		return tileSets;
	}
	
	/**
	 * Getter for {@link #tileSize}.
	 * 
	 * @return  The size in pixels of the tiles in the sub-map.
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * This function gets a specific tile from the buffered
	 * image of a specific tileset, by specifying the section
	 * of the tileset's image to get the subimage from.
	 * 
	 * @param tileSet  The image of the tileset.
	 * @param tileX  The x position to get the subimage from.
	 * @param tileY  The y position to get the subimage from.
	 * @param tileSize  The size of the tile in the tileset.
	 * 
	 * @return  A {@link BufferedImage}, representing the sub-image.
	 * 
	 * @throws IOException
	 */
	public BufferedImage getTile(BufferedImage tileSet, int tileX, int tileY, int tileSize) throws IOException {
		return tileSet.getSubimage(tileX, tileY, tileSize, tileSize);
	}
	
	/**
	 * Getter for {@link #staticMap}.
	 * 
	 * @return  The static part of the sub-map.
	 */
	public BufferedImage getStaticMap() {
		return this.staticMap;
	}
	
	/**
	 * Getter for {@link #levelObject}.
	 * 
	 * @return  The JSON object corresponding to the JSON file that
	 * represents this sub-map.
	 */
	public JSONObject getLevelObject() {
		return levelObject;
	}
	
	/**
	 * This function initializes the attributes of the
	 * sub-map using the {@link #levelObject}.
	 */
	private void initializeLevelAttributes() {
		tileSize = levelObject.getInt("tilewidth");
		width = levelObject.getInt("width");
		height = levelObject.getInt("height");
		initializeTileSets();
		initializeLevelLayers();
		initializeStaticMap();
	}
	
	/**
	 * This function initializes {@link #tileSets} using the {@link #levelObject}.
	 */
	private void initializeTileSets() {
		JSONArray tileSetArray = levelObject.getJSONArray("tilesets");
		tileSets = new TileSet[tileSetArray.length()];
		for (int i = 0; i < tileSetArray.length(); i++) {
			tileSets[i] = new TileSet(tileSetArray.getJSONObject(i));
		}
	}
	
	/**
	 * This function initializes {@link #layers} using the {@link #levelObject}.
	 */
	private void initializeLevelLayers() {
		JSONArray layerObjects = levelObject.getJSONArray("layers");
		layers = new LevelLayer[layerObjects.length()];
		for (int i = 0; i < layerObjects.length(); i++) {
			layers[i] = new LevelLayer(this, layerObjects.getJSONObject(i));
		}
	}
	
	/**
	 * This function initializes the static map by iterating over
	 * the static layers and painting them into a {@link BufferedImage}
	 * object.
	 */
	private void initializeStaticMap() {
		staticMap = new BufferedImage(tileSize * width, tileSize * height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D painter = staticMap.createGraphics();
		for (LevelLayer layer : layers) {
			if (!layer.getType().equals("static")) {
				continue;
			}
			for (int row = 0; row < height; row++) {
				for (int column = 0; column < width; column++) {
					int tileID = layer.getTileID(layer.getLayerDataElement(row, column));
					if (tileID < 0) {
						continue;
					}
					String tileSetName = layer.getTileSetName(layer.getLayerDataElement(row, column));
					BufferedImage tileSetImage = layer.getTileSetImage(tileSetName);
					int tileX = (tileID % (tileSetImage.getWidth() / tileSize)) * tileSize;
					int tileY = (tileID / (tileSetImage.getWidth() / tileSize)) * tileSize;
					painter.drawImage(
						tileSetImage,
						column * tileSize, row * tileSize,
						(column + 1) * tileSize, (row + 1) * tileSize,
						tileX, tileY, tileX + tileSize, tileY + tileSize,
						null
					);
				}
			}
		}
		painter.dispose();
	}
	
	/**
	 * This function renders the dynamic layer by iterating over all dynamic
	 * objects and painting them in their current position in a {@link BufferedImage}.
	 * 
	 * @return A buffered image representing the current state of all dynamic objects.
	 */
	public BufferedImage getDynamicMap() {
		BufferedImage dynamicMap = new BufferedImage(tileSize * width, tileSize * height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D painter = dynamicMap.createGraphics();
		for (int i = 0; i < GameObjectUtilities.dynamicObjects.length; i++) {
			if (!GameObjectUtilities.dynamicObjectsStates[i]) {
				continue;
			}
			DynamicObject object = GameObjectUtilities.dynamicObjects[i];
			int tileID = object.getTileSet().getTileID(object.getCurrentTileValue());
			BufferedImage tileSetImage = object.getTileSet().getImage();
			int tileX = (tileID % (tileSetImage.getWidth() / tileSize)) * tileSize;
			int tileY = (tileID / (tileSetImage.getWidth() / tileSize)) * tileSize;
			painter.drawImage(
				tileSetImage,
				object.getColumnPosition() * tileSize + object.getColumnPixelOffset(),
				object.getRowPosition() * tileSize + object.getRowPixelOffset(),
				(object.getColumnPosition() + 1) * tileSize + object.getColumnPixelOffset(),
				(object.getRowPosition() + 1) * tileSize + object.getRowPixelOffset(),
				tileX, tileY, tileX + tileSize, tileY + tileSize,
				null
			);
			if (object instanceof Monster) {
				int monsterHeartCount = object.getHealth();
				for (int j = 0; j < monsterHeartCount; j++) {
					painter.drawImage(Player.heart, object.getBounds().x + j * 8 - 4, object.getBounds().y - 10, 8, 8 , null);
				}
			}
		}
		painter.dispose();
		return dynamicMap;
	}
}
