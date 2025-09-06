package mapControls;

import java.awt.Polygon;
import java.awt.Rectangle;

import org.json.JSONObject;

/**
 * The {@code Tile} class represents a kind of tile
 * in a {@link TileSet}, and stores its information.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Tile { 
	/**
	 * The ID of this tile.
	 */
	private int tileId;
	
	/**
	 * The state of this tile (Full Collision, Half Collision, No Collision).
	 */
	private String isCollision;
	
	/**
	 * The set of points that define the collision bounds of this tile.
	 */
	private Polygon tilePolygon;
	
	/**
	 * This constructor constructs the {@code Tile} object
	 * from the JSONObject of this tile.
	 * 
	 * @param tile  The JSONObject of this tile.
	 */
	public Tile(JSONObject tile) {
		tileId = tile.getInt("id");
		isCollision = tile.getString("isCollision");
		tilePolygon = convertPolygon(tile);
	}
	
	/**
	 * This function converts the collision bound points of this
	 * tile from the JSONObject into a {@link java.awt.Polygon} object.
	 * 
	 * @param tile  The JSONObject of this tile.
	 * 
	 * @return  A {@code Polygon} object representing the collision bound points
	 * of this tile if {@link #isCollision} is 'half collision', null otherwise.
	 */
	private Polygon convertPolygon(JSONObject tile) {
		if (isCollision.equals("half collision")) {
			int npoints = tile.getJSONArray("polygon").length(); //The number of points in this polygon.
			int[] xpoints = new int[npoints];
			int[] ypoints = new int[npoints];
			double x, y;
			for (int i = 0; i < npoints; i++) {
				x = tile.getJSONArray("polygon").getJSONObject(i).getInt("x");
				y = tile.getJSONArray("polygon").getJSONObject(i).getInt("y");
				xpoints[i] = (int) Math.round(x);
				ypoints[i] = (int) Math.round(y);
			}
			return new Polygon(xpoints, ypoints, npoints);
		}
		return null;
	}
	
	/**
	 * This function checks if this tile intersects a rectangle bound.
	 * 
	 * @param bounds  The rectangle bound to check.
	 * @param tileRow  The row of this tile.
	 * @param tileColumn  The column of this tile.
	 * @param tileSize  The size of this tile.
	 * 
	 * @return  A boolean, true if colliding, false otherwise.
	 */
	public boolean isColliding(Rectangle bounds, int tileRow, int tileColumn, int tileSize) {
		if(isCollision.equals("full collision")) {
			return true;
		}
		else if(isCollision.equals("half collision")) {
			if(getAbsolutePolygon(tileRow, tileColumn, tileSize).intersects(bounds)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function converts the points of the tile from tile coordinates
	 * to absolute coordinates.
	 * 
	 * @param tileRow  The row of this tile.
	 * @param tileColumn  The column of this tile.
	 * @param tileSize  The size of this tile.
	 * 
	 * @return  A polygon, the points in absolute coordinates.
	 */
	private Polygon getAbsolutePolygon(int tileRow, int tileColumn, int tileSize) {
		Polygon absoluteTilePolygon = new Polygon(tilePolygon.xpoints, tilePolygon.ypoints, tilePolygon.npoints);
		for (int i = 0; i < absoluteTilePolygon.npoints; i++) {
			absoluteTilePolygon.xpoints[i]+= tileColumn * tileSize;
			absoluteTilePolygon.ypoints[i]+= tileRow * tileSize;
		}
		return absoluteTilePolygon;
	}
	
	/**
	 * Getter for {@link #tileId}.
	 * 
	 * @return  The ID of the tile.
	 */
	public int getId() {
		return tileId;
	}
	
	/**
	 * Getter for {@link #isCollision}.
	 * 
	 * @return  The collision state of the tile.
	 */
	public String getCollisionState() {
		return isCollision;
	}
	
	/**
	 * Getter for {@link #tilePolygon}.
	 * 
	 * @return  The set of points that define the collision bounds of this tile.
	 */
	public Polygon getPolygon() {
		return tilePolygon;
	}

}
