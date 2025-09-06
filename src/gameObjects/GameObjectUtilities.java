package gameObjects;

/**
 * The {@code GameObjectUtilities} class offers helper
 * definitions used with game objects in general.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class GameObjectUtilities {
	/**
	 * An array that should store all the dynamic objects.
	 */
	public static DynamicObject[] dynamicObjects = new DynamicObject[] {
		new Player(2, "playerTileSet", 1, "player"),
		new Monster(2, "ninjaA", 1, "monster"),
		new Monster(2, "ninjaB", 1, "monster2")
	};
	
	/**
	 * An array of the states of all dynamic objects. True means
	 * the object is alive and false means the object is dead.
	 * <p>Example: {@code dynamicObjectsStates[i]} corresponds to the
	 * state of {@code dynamicObjects[i]}.</p>
	 */
	public static boolean[] dynamicObjectsStates = new boolean[] {
		true, true, true
	};
	
	/**
	 * This function updates all the dynamic objects by calling the 
	 * {@code update} function on all individual objects in {@link #dynamicObjects}.
	 * Only alive dynamic objects marked in {#link {@link #dynamicObjectsStates}
	 * are updated.
	 */
	public static void updateDynamicObjects() {
		for (int i = 0; i < dynamicObjects.length; i++) {
			if (dynamicObjectsStates[i]) {
				dynamicObjects[i].update();
			}
		}
	}
	
	/**
	 * This function finds the dynamic object in {@link #dynamicObjects}
	 * that has a specific tile value. The tile value passed does not
	 * need to be the object's current tile value; being within the
	 * set of tile values the object's tileset represents is enough.
	 * 
	 * @param tileValue  The tile value to search for a dynamic object that
	 * matches.
	 * 
	 * @return  The dynamic object that matches the provided tile value.
	 * If no match is found or dynamic object that matches is dead, null is returned.
	 */
	public static DynamicObject getDynamicObjectWithTileValue(int tileValue) {
		for (int i = 0; i < dynamicObjects.length; i++) {
			if (!dynamicObjectsStates[i]) {
				continue;
			}
			if (dynamicObjects[i].getTileSet().hasIndex(tileValue)) {
				return dynamicObjects[i];
			}
		}
		return null;
	}
	
}
