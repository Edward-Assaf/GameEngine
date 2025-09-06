package gameObjects;

import java.awt.Point;
import java.awt.Rectangle;

import org.json.JSONArray;
import org.json.JSONObject;

import database.DatabaseUtilities;
import mapControls.LevelLayer;
import mapControls.MapUtilities;
import mapControls.TileSet;

/**
 * The {@code DynamicObject} abstract class is a special case of
 * {@link GameObject} and a generalization of the main attributes
 * and functionalities of an object with an <b>updatable</b>
 * state (animation or position).
 * 
 * <p>State update functionality is implemented by overriding
 * {@link #update()} in any sub-class of this class.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public abstract class DynamicObject extends GameObject {
	/**
	 * Some dynamic objects may represent live objects that
	 * can be eliminated when some condition is met, like hitpoints
	 * reaching 0, a specific event occuring... etc.
	 * 
	 * <p>The health variable has a generalized purpose of
	 * simulating that. If {@code health = 0} , the object is
	 * dead. For dynamic objects that have no 'live' property,
	 * set this to any non-zero value.</p>
	 */
	protected int health;
	
	/**
	 * A unique name that is given to every dynamic object. This
	 * name must match the value of the 'name' key of one of the
	 * elements of the 'objectsOffsets' JSONArray, present in the
	 * JSONObject of the dynamic layer this object belongs to.
	 * It is used to load {@link #rowPixelOffset} and {@link #columnPixelOffset}
	 * from the level's JSON file.
	 */
	protected String name;
	
	/**
	 * For an object to be updatable with pixel-accuracy while
	 * its parent class represents positions in a tile-based manner,
	 * an offset is needed to measure 'the difference between the
	 * original tile position and the real pixel position'.
	 * 
	 * <p>This attribute measures the offset for the row position.</p>
	 */
	protected int rowPixelOffset;
	
	/**
	 * For an object to be updatable with pixel-accuracy while
	 * its parent class represents positions in a tile-based manner,
	 * an offset is needed to measure 'the difference between the
	 * original tile position and the real pixel position'.
	 * 
	 * <p>This attribute measures the offset for the column position.</p>
	 */
	protected int columnPixelOffset;
	
	/**
	 * This constructor constructs the {@code DynamicObject} by constructing
	 * its static parent level first ({@link GameObject}), and then computing
	 * the initial position of the object using the object's layer information.
	 * 
	 * @param layerID  The index of the layer in the level's layers array.
	 * @param tileSetName  The name of the tile set this object is drawn using.
	 */
	public DynamicObject(int layerID, String tileSetName, String name) {
		super(layerID, tileSetName);
		Point tilePosition = getTilePosition();
		this.name = name;
		health = DatabaseUtilities.getGameDataAttributeValueForUser(DatabaseUtilities.currentUser, name + "Health");
		if (tilePosition == null) {
			this.rowPosition = 0;
			this.columnPosition = 0;
		}
		else {
			this.rowPosition = tilePosition.x;
			this.columnPosition = tilePosition.y;
		}
		// Getting the 'objectsOffsets' JSON Array.
		JSONObject levelObject = MapUtilities.level.getLevelObject();
		JSONArray layerArray = levelObject.getJSONArray("layers");
		JSONObject dynamicObjectlayer = layerArray.getJSONObject(layerID);
		JSONArray objectsOffsets = dynamicObjectlayer.getJSONArray("objectsOffsets");
		
		// Finding the 'objectsOffsets' element that has a matching name with this object.
		for (int i = 0; i < objectsOffsets.length(); i++) {
			if (objectsOffsets.getJSONObject(i).getString("name").equals(name)) {
				this.rowPixelOffset = objectsOffsets.getJSONObject(i).getInt("row");
				this.columnPixelOffset = objectsOffsets.getJSONObject(i).getInt("column");
				break;
			}
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(
			columnPosition * drawSize + columnPixelOffset,
			rowPosition * drawSize + rowPixelOffset,
			drawSize, drawSize
		);
	}
	
	/**
	 * Getter for {@link #rowPixelOffset}
	 * 
	 * @return  The difference between the object's original row position
	 * and the actual pixel position.
	 */
	public int getRowPixelOffset() {
		return rowPixelOffset;
	}
	
	/**
	 * Getter for {@link #columnPixelOffset}
	 * 
	 * @return  The difference between the object's original column position
	 * and the actual pixel position.
	 */
	public int getColumnPixelOffset() {
		return columnPixelOffset;
	}
	
	/**
	 * Getter for {@link #name}.
	 * 
	 * @return  The name of this object.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for {@link #health}.
	 * 
	 * @return  The health of this object.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Setter for {@link #health}.
	 * 
	 * @param health  The new value to set.
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * This function updates the position of the object by some amount,
	 * but only if the object doesn't collide with anything.
	 * 
	 * @param changeX  The amount, in pixels, to update the x position by.
	 * @param changeY  The amount, in pixels, to update the y position by.
	 */
	protected void updatePosition(int changeX, int changeY) {
		if (getCollisionCheck(changeX, changeY)) {
			return;
		}
		updateColumnPixelOffset(changeX);
		updateRowPixelOffset(changeY);
	}
	
	/**
	 * This function updates the object's row position by some amount.
	 * 
	 * @param change  The amount, in pixels, to update the y position by.
	 */
	private void updateRowPixelOffset(int change) {
		rowPixelOffset += change;
		if (Math.abs(rowPixelOffset) > (drawSize / 2)) { // Updates when half of the tile is pressed.
			rowPosition += (rowPixelOffset > 0)? 1 : -1;
			rowPixelOffset = (rowPixelOffset > 0)? 
				rowPixelOffset - drawSize : rowPixelOffset + drawSize;
		}
	}
	
	/**
	 * This function updates the object's column position by some amount.
	 * 
	 * @param change  The amount, in pixels, to update the x position by.
	 */
	private void updateColumnPixelOffset(int change) {
		columnPixelOffset += change;
		if (Math.abs(columnPixelOffset) > (drawSize / 2)) { // Updates when half of the tile is pressed.
			columnPosition += (columnPixelOffset > 0)? 1 : -1;
			columnPixelOffset = (columnPixelOffset > 0)? 
				columnPixelOffset - drawSize : columnPixelOffset + drawSize;
		}
	}
	
	/**
	 * This function computes the initial tile position of this object
	 * using the layer's information. It also re-computes the {@code currentTileValue}
	 * by searching for the tile value that represents this object in
	 * the layer.
	 * 
	 * @return  A point, x representing the row tile position and
	 * y representing the column tile position. If the object
	 * does not exist, null is returned.
	 */
	private Point getTilePosition() {
		for (int row = 0; row < MapUtilities.level.getHeight(); row++) {
			for (int column = 0; column < MapUtilities.level.getWidth(); column++) {
				if (tileSet.hasIndex(MapUtilities.level.getLevelLayers()[layerID].getLayerDataElement(row, column))) {
					currentTileValue = MapUtilities.level.getLevelLayers()[layerID].getLayerDataElement(row, column);
					return new Point(row, column);
				}
			}
		}
		return null;
	}
	
	/**
	 * This function is used to check if the object collides with any
	 * other object. The parameters of {@link #updatePosition(int, int)}
	 * are passed here to check for collision before using them to
	 * update the position of the object.
	 * 
	 * @param changeX  The amount that the x position is desired to be changed by
	 * if no collision were to occur.
	 * @param changeY  The amount that the y position is desired to be changed by
	 * if no collision were to occur.
	 * 
	 * @return A boolean, true if a collision is detected, and false otherwise.
	 */
	private boolean getCollisionCheck(int changeX, int changeY) {
		return getStaticCollisionCheck(changeX, changeY) || getDynamicCollisionCheck(changeX, changeY);
	}
	
	/**
	 * This function is used to check if the object collides with any
	 * static object. The parameters of {@link #updatePosition(int, int)}
	 * are passed here to check for collision before using them to
	 * update the position of the object.
	 * 
	 * @param changeX  The amount that the x position is desired to be changed by
	 * if no collision were to occur.
	 * @param changeY  The amount that the y position is desired to be changed by
	 * if no collision were to occur.
	 * 
	 * @return A boolean, true if a collision is detected, and false otherwise.
	 */
	private boolean getStaticCollisionCheck(int changeX, int changeY) {
		int columnCheck = (changeX > 0)? 1 : -1;
		int rowCheck = (changeY > 0)? 1 : -1;
		LevelLayer[] layers =  MapUtilities.level.getLevelLayers();
		for (int i = 0; i < layers.length; i++) {
			if (!layers[i].getType().equals("static")) {
				continue;
			}
			if ((changeX != 0) && (changeY != 0)) {
				if (staticCollisionInTile(i, rowPosition + rowCheck, columnPosition + columnCheck)) {
					return true;
				}
			}
			if ((changeX != 0) && staticCollisionInTile(i, rowPosition, columnPosition + columnCheck)) {
				return true;
			}
			if (changeY != 0) {
				if (staticCollisionInTile(i, rowPosition + rowCheck, columnPosition)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This function is used to check if the object collides with any
	 * dynamic object. The parameters of {@link #updatePosition(int, int)}
	 * are passed here to check for collision before using them to
	 * update the position of the object.
	 * 
	 * @param changeX  The amount that the x position is desired to be changed by
	 * if no collision were to occur.
	 * @param changeY  The amount that the y position is desired to be changed by
	 * if no collision were to occur.
	 * 
	 * @return A boolean, true if a collision is detected, and false otherwise.
	 */
	private boolean getDynamicCollisionCheck(int changeX, int changeY) {
		int columnCheck = (changeX > 0)? 1 : -1;
		int rowCheck = (changeY > 0)? 1 : -1;
		LevelLayer[] layers =  MapUtilities.level.getLevelLayers();
		for (int i = 0; i < layers.length; i++) {
			if (!layers[i].getType().equals("dynamic")) {
				continue;
			}
			if ((changeX != 0) && (changeY != 0)) {
				if (dynamicCollisionInTile(i, rowPosition + rowCheck, columnPosition + columnCheck)) {
					return true;
				}
			}
			if ((changeX != 0) && dynamicCollisionInTile(i, rowPosition, columnPosition + columnCheck)) {
				return true;
			}
			if ((changeY != 0) && dynamicCollisionInTile(i, rowPosition + rowCheck, columnPosition)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function checks if the object collides with any static object
	 * in a specified tile of a specified layer.
	 * 
	 * @param collisionLayerID  The layer to check collision with.
	 * @param i  The row position of the tile to check collision with.
	 * @param j  The column position of the tile to check collision with.
	 * 
	 * @return  A boolean, true if a collision occures, and false otherwise.
	 */
	private boolean staticCollisionInTile(int collisionLayerID, int i, int j) {
		if ((i < 0) || (i >= MapUtilities.level.getHeight())) {
			return true;
		}
		if ((j < 0) || (j >= MapUtilities.level.getWidth())) {
			return true;
		}
		LevelLayer collisionLayer = MapUtilities.level.getLevelLayers()[collisionLayerID];
		int collisionLayerElement = collisionLayer.getLayerDataElement(i, j);
		String collisionTileSetName = collisionLayer.getTileSetName(collisionLayerElement);
		if (collisionTileSetName == null) {
			return false;
		}
		TileSet collisionTileSet = collisionLayer.getTileSet(collisionTileSetName);
		int tileID = collisionTileSet.getTileID(collisionLayerElement);
		return collisionTileSet.getTileObject(tileID).isColliding(getBounds(), i, j, drawSize);
	}
	
	/**
	 * This function checks if the object collides with any dynamic object
	 * in a specified tile of a specified layer.
	 * 
	 * @param collisionLayerID  The layer to check collision with.
	 * @param i  The row position of the tile to check collision with.
	 * @param j  The column position of the tile to check collision with.
	 * 
	 * @return  A boolean, true if a collision occures, and false otherwise.
	 */
	private boolean dynamicCollisionInTile(int collisionLayerID, int i, int j) {
		if ((i < 0) || (i >= MapUtilities.level.getHeight())) {
			return true;
		}
		if ((j < 0) || (j >= MapUtilities.level.getWidth())) {
			return true;
		}
		LevelLayer collisionLayer = MapUtilities.level.getLevelLayers()[collisionLayerID];
		int collisionLayerElement = collisionLayer.getLayerDataElement(i, j);
		DynamicObject collisionObject = GameObjectUtilities.getDynamicObjectWithTileValue(collisionLayerElement);
		if (collisionObject == null) {
			return false;
		}
		return getBounds().intersects(collisionObject.getBounds());
	}
	
	/**
	 * This function should be implemented by any class inheriting
	 * from {@code DynamicObject}. Any animation-update-related or
	 * position-update-related functionalities should be done here.
	 */
	public abstract void update();
	
}
