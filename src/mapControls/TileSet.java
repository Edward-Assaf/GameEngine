package mapControls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import media.MediaResource;

/**
 * The {@code TileSet} class defines the necessary
 * definitions to represent a tileset which is used
 * to paint tiles in the layers of a sub-map.
 * 
 * @see LevelLayer
 * @see Level
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class TileSet {
	/**
	 * The name of this tileset.
	 */
	private String name;
	
	/**
	 * The image of this tileset.
	 */
	private BufferedImage image;
	
	/**
	 * The standard firstgid of this tileset.
	 */
	private int firstID;
	
	/**
	 * The number of different tile values this
	 * tileset represents. The range then is from
	 * ({@link #firstID} to {@link #firstID} + tileCount - 1}.
	 */
	private int tileCount;
	
	/**
	 * The list of different tiles this tileset has. This attribute
	 * is only initialized for tilesets used in the static layers.
	 */
	private Tile[] tiles;
	
	/**
	 * This constructor constructs the {@code TileSet} object by providing
	 * the JSON object for the tileset.
	 * 
	 * @param tileSetObject  The JSON object of the tileset.
	 */
	public TileSet(JSONObject tileSetObject) {
		name = tileSetObject.getString("name");
		firstID = tileSetObject.getInt("firstgid");
		tileCount = tileSetObject.getInt("tilecount");
		
		// Getting the image of the tileset.
		String imageName = tileSetObject.getString("image");
		try {
			image = ImageIO.read(new File(new MediaResource(imageName).getResourceAbsolutePath()));
		} catch (IOException e) {
			image = null;
			System.out.println("Tileset " + name + "'s image file load failed");
		}
		
		// Initializing the tiles array.
		tiles = new Tile[tileCount];
		try {
			JSONArray tileObjects = tileSetObject.getJSONArray("tiles");
			for(int i = 0; i < tileCount; i++) {
				tiles[i] = new Tile(tileObjects.getJSONObject(i));
			}
		} catch(JSONException e) {
			System.out.println("Tileset " + name + "'s tiles[] array initialization ignored");
		}
	}
	
	/**
	 * This function checks if a tile value belongs
	 * to this tileset.
	 * 
	 * @param tileValue  The value of the tile.
	 * @return  A boolean, true if it belongs to the tileset, and false otherwise.
	 */
	public boolean hasIndex(int tileValue) {
		return (tileValue >= firstID) && (tileValue < (firstID + tileCount));
	}
	
	/**
	 * Getter for {@link #image}.
	 * 
	 * @return  The image of the tileset.
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Getter for {@link #name}.
	 * 
	 * @return  The name of the tileset.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for {@link #firstID}.
	 * 
	 * @return  The standard firstgid of the tileset.
	 */
	public int getFirstID() {
		return firstID;
	}
	
	/**
	 * This function returns the tile ID of a
	 * tile's value by subtracting the standard
	 * firstgid from it, to shift indices to 0.
	 * 
	 * @param value  The value of the tile.
	 * 
	 * @return  The corresponding tile ID of the tile, -1 if it doesn't exist.
	 */
	public int getTileID(int value) {
		if (hasIndex(value)) {
			return value - firstID;
		}
		return -1;
	}
	
	/**
	 * This function is used to obtain the i'th {@link Tile}
	 * object in the {@link #tiles} array. This must only be
	 * used with tilesets that are used in static layers.
	 * 
	 * @param i  The index of the tile in {@link #tiles}.
	 * 
	 * @return  The i'th {@code Tile} object in {@link #tiles}.
	 */
	public Tile getTileObject(int i) {
		return tiles[i];
	}
	
}
