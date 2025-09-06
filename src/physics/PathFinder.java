package physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;

import gameObjects.DynamicObject;
import gameObjects.GameObjectUtilities;
import mapControls.LevelLayer;
import mapControls.MapUtilities;
import mapControls.TileSet;

/**
 * The {@code PathFinder} class allows the
 * use A* algorithm to find the shortest path
 * between two points.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class PathFinder {
	/**
	 * The inner {@code Node} class defines a point
	 * that is used in calculating the shortest path.
	 * 
	 * <p>It implements {@link java.lang.Comparable} functionality.
	 */
	public static class Node implements Comparable<Node> {
		// Indices of the node (tile).
		public int x, y;
		
		// The distance and heuristic values of the node.
		public int g, h;
		
		// The previous (parent) node to this node in the path.
		public Node previous = null;
		
		// The constructor of the Node object.
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			g = 0;
			h = 0;
		}
		
		// Returns the total cost (distance + heuristic).
		public int totalCost() {
			return g + h;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Node)) {
				return false;
			}
			Node nodeObj = (Node) obj;
			return (x == nodeObj.x) && (y == nodeObj.y);
			
		}
		
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.totalCost(), o.totalCost());
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}
	
	/**
	 * The list of possible directions to explore in every step.
	 */
	private static final int[][] directions = {
			{0,-1}, // Up.
			{-1,0}, // Left.
			{1,0},  // Right.
			{0,1}   // Down.
	};
	
	/**
	 * This function checks if a Node is valid (exists in the grid
	 * and has no collision).
	 * 
	 * @param grid  The grid to check.
	 * @param x  The x value of the node.
	 * @param y  The y value of the node.
	 * 
	 * @return  A boolean, true if the node is valid, false otherwise.
	 */
	public static boolean isValid(boolean[][] grid, int x, int y) {
		return (x>=0) && (y>=0) && (x<grid.length) && (y<grid[0].length) && (grid[x][y]);
	}
	
	/**
	 * This function uses the city-block distance method
	 * to estimate the distance between two nodes.
	 * 
	 * @param a  The first node.
	 * @param b  The second node.
	 * 
	 * @return  The estimated distance between the two nodes.
	 */
	public static int heuristic(Node a, Node b){
		return (int)(Math.abs(a.x - b.x) + Math.abs(a.y - b.y));
	}
	
	/**
	 * This function takea a node and reconstructs the path by
	 * recursively iterating over the previous (parent) nodes
	 * until no previous (parent) is found.
	 * 
	 * @param destination  The node to reconstruct the path for.
	 * 
	 * @return  The reconstructed path.
	 */
	public static ArrayList<Node> reconstructPath(Node destination){
		ArrayList<Node> path = new ArrayList<Node>();
		while (destination.previous != null) {
			path.add(destination);
			destination = destination.previous;
		}
		path.add(destination);
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * This function finds the path between a source and a destination node,
	 * and it ignores the collision of specific tiles from a tileset.
	 * 
	 * @param source  The source node.
	 * @param destination  The destination node.
	 * @param ignoredTiles  The tileset to ignore the tile values of.
	 * 
	 * @return  The path between source and destination, empty list if the path is
	 * not found.
	 */
	public static ArrayList<Node> findPath(Node source, Node destination, TileSet ignoredTiles){
		boolean[][] grid = getStaticPathGrid();
		grid = getDynamicPathGrid(ignoredTiles, grid);

		PriorityQueue<Node> openList = new PriorityQueue<Node>();
		HashSet<Node> visitedList = new HashSet<Node>();
		HashMap<Node, Integer> nodeDistanceValues = new HashMap<>();
		
		source.g = 0;
		source.h = heuristic(source, destination);
		openList.add(source);
		nodeDistanceValues.put(source, source.g);
		
		while(!openList.isEmpty()) {
			Node currentNode = openList.poll(); //least heuristic node in the queue.
			if(currentNode.equals(destination)) {
				return reconstructPath(currentNode);
			}
			visitedList.add(currentNode);
			for (int[] direction : directions) {
				int currentX = currentNode.x + direction[0];
				int currentY = currentNode.y + direction[1];
				if(!isValid(grid, currentX, currentY)) {
					continue;
				}
				int newG = currentNode.g + 1;
				Node newNode = new Node(currentX, currentY);
				if(visitedList.contains(newNode)) {
					continue;
				}
				if (!openList.contains(newNode)) {
					newNode.g = newG;
					newNode.previous = currentNode;
					newNode.h = heuristic(newNode, destination);
					openList.add(newNode);
					nodeDistanceValues.put(newNode, newNode.g);
				}
				else if (nodeDistanceValues.containsKey(newNode) && (newG < nodeDistanceValues.get(newNode))) {
					newNode.g = newG;
					newNode.previous = currentNode;
					newNode.h = heuristic(newNode, destination);
					openList.add(newNode);
					nodeDistanceValues.put(newNode, newNode.g);
				}
			}	
		}
		return new ArrayList<Node>();
	}
	
	/**
	 * This function returnes a boolean grid that has value 'true'
	 * for wherever there is a static collision.
	 * 
	 * @return  The grid.
	 */
	public static boolean[][] getStaticPathGrid() {
		boolean[][] grid = new boolean[MapUtilities.level.getHeight()][MapUtilities.level.getWidth()];
		for(int i = 0; i < grid.length ; i++) {
			for(int j = 0; j < grid[0].length ; j++) {
				grid[i][j] = true;
			}
		}
		
		LevelLayer[] levelLayers = MapUtilities.level.getLevelLayers();
		for(LevelLayer layer : levelLayers) {	
			if(layer.getType().equals("static")) {
				for(int i=0;i<layer.getLayerData().length;i++) {
					for(int j=0;j<layer.getLayerData()[0].length;j++) {
						int tileValue = layer.getLayerDataElement(i, j);
						String tileTileSetName = layer.getTileSetName(tileValue);
						if (tileTileSetName == null) {
							continue;
						}
						TileSet tileTileSet = layer.getTileSet(tileTileSetName);
						int tileID = tileTileSet.getTileID(tileValue);
						String collisionState = tileTileSet.getTileObject(tileID).getCollisionState();
						if(collisionState.equals("half collision") || collisionState.equals("full collision")) {
							grid[i][j] = false;
						}
					}
				}
			}
		}
		return grid;
	}

	/**
	 * This function returnes a boolean grid that has value 'true'
	 * for wherever there is a dynamic collision.
	 * 
	 * @return  The grid.
	 */
	public static boolean[][] getDynamicPathGrid(TileSet ignoredTiles, boolean[][] grid){
		LevelLayer[] levelLayers = MapUtilities.level.getLevelLayers();
		for(LevelLayer layer : levelLayers) {
			if(layer.getType().equals("dynamic")) {
				for(int i=0;i<layer.getLayerData().length;i++) {
					for(int j=0;j<layer.getLayerData()[0].length;j++) {
						int tileValue = layer.getLayerDataElement(i, j);
						if(ignoredTiles.hasIndex(tileValue)) {
							continue;
						}
						DynamicObject object = GameObjectUtilities.getDynamicObjectWithTileValue(tileValue);
						if (object != null) {
							grid[i][j] = false;
						}
					}
				}
			}
		}
		return grid;
	}
}