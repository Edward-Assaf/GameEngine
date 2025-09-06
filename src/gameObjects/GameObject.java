package gameObjects;

import java.awt.Shape;

import mapControls.MapUtilities;
import mapControls.TileSet;

/**
 * The {@code GameObject} abstract class offers
 * a generalization of the main attributes of any
 * object that can be interacted with, statically or
 * dynamically.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public abstract class GameObject {	
	/**
	 * The tile set that is used to draw the game object.
	 */
	protected TileSet tileSet;
	
	/**
	 * The current tile value (within the data array of
	 * the layer this object lives in) that represents it.
	 */
	protected int currentTileValue;
	
	/**
	 * The current row position (in tiles) of the object.
	 */
	protected int rowPosition;
	
	/**
	 * The current column position (in tiles) of the object.
	 */
	protected int columnPosition;
	
	/**
	 * The size in pixels by which this object is drawn on the screen.
	 * It is equal to the tile size of the level it belongs to.
	 */
	protected int drawSize;
	
	/**
	 * The index of the layer in the layers array of the level
	 * this object belongs to.
	 */
	protected int layerID;
	
	/**
	 * This constructor constructs the basic structure of any
	 * game object, identifying its layer, tileset... etc
	 * The position is static and unsettable here.
	 * 
	 * @param layerID  The index of the layer in the level's layers array.
	 * @param tileSetName  The name of the tile set this object is drawn using.
	 */
	public GameObject(int layerID, String tileSetName) {
		this.layerID = layerID;
		this.tileSet = MapUtilities.level.getLevelLayers()[this.layerID].getTileSet(tileSetName);
		this.currentTileValue = this.tileSet.getFirstID();
		this.rowPosition = 0;
		this.columnPosition = 0;
		this.drawSize = MapUtilities.level.getTileSize();
	}
	
	/**
	 * Getter for {@link #currentTileValue}
	 * 
	 * @return  The current tile value representing this object.
	 */
	public int getCurrentTileValue() {
		return currentTileValue;
	}
	
	/**
	 * Getter for {@link #tileSet}
	 * 
	 * @return  The tileset this object is drawn using.
	 */
	public TileSet getTileSet() {
		return tileSet;
	}
	
	/**
	 * Getter for {@link #rowPosition}
	 * 
	 * @return  The current row position (in tiles) of the object.
	 */
	public int getRowPosition() {
		return rowPosition;
	}
	
	/**
	 * Getter for {@link #columnPosition}
	 * 
	 * @return  The current column position (in tiles) of the object.
	 */ 
	public int getColumnPosition() {
		return columnPosition;
	}
	
	/**
	 * This is a general function that should be implemented by any
	 * class inheriting from this class. This function should return
	 * an instance of that implements {@link java.awt.Shape} interface 
	 * representing the bounds of this object in pixels.
	 * 
	 * @return  A shape representing the bounds of this object in pixels.
	 */
	public abstract Shape getBounds();
	
}
