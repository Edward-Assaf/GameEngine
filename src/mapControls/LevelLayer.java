package mapControls;

import java.awt.image.BufferedImage;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The {@code LevelLayer} class defines the necessary
 * definitions to represent a layer of a sub-map.
 * 
 * @see Level
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class LevelLayer {
	/**
	 * The level this layer belongs to.
	 */
	private Level level;
	
	/**
	 * The data of this layer. Each layer has a two-dimensional
	 * array of data written in its JSON object.
	 */
	private int[][] layerData;
	
	/**
	 * The type of this layer (static or dynamic).
	 */
	private String type;
	
	/**
	 * The subset of tilesets from the level's tilesets
	 * that this layer will use to paint its tiles.
	 */
	private TileSet[] layerTileSets;
	
	/**
	 * This constructor constructs the {@code LevelLayer} object by providing
	 * the JSON object for the layer, and the level it belongs to.
	 * 
	 * @param level  The level it belongs to.
	 * @param layerObject  The JSON object for the layer.
	 */
	public LevelLayer(Level level, JSONObject layerObject) {
		this.level = level;
		this.type = layerObject.getString("type");
		
		// Initializing layer tile sets.
		JSONArray layerTileSetArray = layerObject.getJSONArray("tilesets");
		layerTileSets = new TileSet[layerTileSetArray.length()];
		int k = 0;
		for (int i = 0; i < layerTileSetArray.length(); i++) {
			String name = layerTileSetArray.getJSONObject(i).getString("name");
			for (int j = 0; j < level.getTileSets().length; j++) {
				if (name.equals(level.getTileSets()[j].getName())) {
					layerTileSets[k++] = level.getTileSets()[j];
					break;
				}
			}
		}
		
		// Initializing the layer data array.
		JSONArray data1D = layerObject.getJSONArray("data");
		layerData = new int[level.getHeight()][level.getWidth()];
		for (int row = 0; row < level.getHeight(); row++) {
			for (int column = 0; column < level.getWidth(); column++) {
				int index = row * level.getWidth() + column;
				layerData[row][column] = data1D.getInt(index);
			}
		}
	}
	
	/**
	 * This function returns the tile ID of a
	 * tile's value by determining the tileset it
	 * belongs to and subtracting the standard firstgid
	 * from it, to shift indices to 0.
	 * 
	 * @param value  The value of the tile.
	 * 
	 * @return  The corresponding tile ID of the tile, -1 if it doesn't exist.
	 */
	public int getTileID(int value) {
		for (TileSet tileSet : layerTileSets) {
			if (tileSet.hasIndex(value)) {
				return value - tileSet.getFirstID();
			}
		}
		return -1;
	}
	
	/**
	 * This function finds the name of the tileset
	 * that a tile value belongs to.
	 * 
	 * @param value  The value of the tile.
	 * 
	 * @return  The name of the tileset, returns null if it doesn't exist.
	 */
	public String getTileSetName(int value) {
		for (TileSet tileSet : layerTileSets) {
			if (tileSet.hasIndex(value)) {
				return tileSet.getName();
			}
		}
		return null;
	}
	
	/**
	 * Getter for {@link #level}.
	 * 
	 * @return  The level this layer belongs to.
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * This function returns the [i,j] element
	 * of the {@link #layerData}.
	 * 
	 * @param i  The row.
	 * @param j  The column.
	 * 
	 * @return  The value at [i,j] from {@link #layerData}.
	 */
	public int getLayerDataElement(int i, int j) {
		return layerData[i][j];	
	}
	
	/**
	 * This function sets a value for the [i,j] element
	 * of the {@link #layerData}.
	 * 
	 * @param i  The row.
	 * @param j  The column.
	 * @param value  The value to be set.
	 */
	public void setLayerDataElement(int i, int j, int value) {
		layerData[i][j] = value;
	}
	
	/**
	 * Getter for {@link #type}.
	 * 
	 * @return  The type of the layer, static or dynamic.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * This function returns the image of the tileset
	 * with a specified name.
	 * 
	 * @param tileSetName  The name of the tileset.
	 * 
	 * @return  The image of the tileset, returns null if it doesn't exist.
	 */
	public BufferedImage getTileSetImage(String tileSetName) {
		for (TileSet tileSet : layerTileSets) {
			if (tileSetName.equals(tileSet.getName())) {
				return tileSet.getImage();
			}
		}
		return null;
	}
	
	/**
	 * This function returns the tileset with a specified name.
	 * 
	 * @param tileSetName  The name of the tileset.
	 * 
	 * @return  The tileset, returns null if it doesn't exist.
	 */
	public TileSet getTileSet(String tileSetName) {
		for (TileSet tileSet : layerTileSets) {
			if (tileSetName.equals(tileSet.getName())) {
				return tileSet;
			}
		}
		return null;
	}

	/**
	 * Getter for {@link #layerData}.
	 * 
	 * @return  The layer data array.
	 */
	public int[][] getLayerData(){
		return layerData;
	}
}
