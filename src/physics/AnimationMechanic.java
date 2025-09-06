package physics;

/**
 * The {@code AnimationMechanic} class
 * handles movement animation mechanics.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class AnimationMechanic {
	/**
	 * The number of update calls to wait before rendering movement animation.
	 */
	private final int RENDERS_PER_SPRITE_MOVING;
	
	/**
	 * The number of update calls to wait before rendering standing animation.
	 */
	private final int RENDERS_PER_SPRITE_STANDING;
	
	/**
	 * The number of update calls waited for movement animation.
	 */
	private int spriteCounterMoving = 0;
	
	/**
	 * The number of update calls waited for standing animation.
	 */
	private int spriteCounterStanding = 0;
	
	/**
	 * The flag to determine if the object is moving upwards.
	 */
	public boolean upDirectionFlag = false;
	
	/**
	 * The flag to determine if the object is moving downwards.
	 */
	public boolean downDirectionFlag = false;
	
	/**
	 * The flag to determine if the object is moving left.
	 */
	public boolean leftDirectionFlag = false;
	
	/**
	 * The flag to determine if the object is moving right.
	 */
	public boolean rightDirectionFlag = false;
	
	/**
	 * The standard first gid of the animated object.
	 */
	private final int mainTileIndex;
	
	/**
	 * This constructor constructs an {@code AnimationMechanic} object.
	 * 
	 * @param moveRender   The number of update calls to wait before rendering movement animation.
	 * @param standRender  The number of update calls to wait before rendering standing animation.
	 * @param mainTileIndex  The standard first gid of the animated object.
	 */
	public AnimationMechanic(int moveRender, int standRender, int mainTileIndex) {
		RENDERS_PER_SPRITE_MOVING = moveRender;
		RENDERS_PER_SPRITE_STANDING = standRender;
		this.mainTileIndex = mainTileIndex;
	}
	
	/**
	 * This function returns the next tile index to go to
	 * from a specified tile index.
	 * 
	 * @param tileIndex  The tile index to go from.
	 * 
	 * @return  The tile index to go to.
	 */
	public int getNextTileIndex(int tileIndex) {
		if (!positionStateUpdated()) {
			if (moveRightRange(tileIndex)) {
				return mainTileIndex + 8;
			}
			if  (moveLeftRange(tileIndex)) {
				return mainTileIndex + 16;
			}
			if (moveUpRange(tileIndex)) {
				return mainTileIndex + 24;
			}
			if (moveDownRange(tileIndex)) {
				return mainTileIndex;
			}
			return getTileIndexAcrossAnimation_2(tileIndex);
		}
		if (rightDirectionFlag) { 
			if (moveRightRange(tileIndex)) {
				return getTileIndexAcrossAnimation_4(tileIndex);
			}
			return mainTileIndex + 12;
		}
		if (leftDirectionFlag) { 
			if (moveLeftRange(tileIndex)) {
				return getTileIndexAcrossAnimation_4(tileIndex);
			}
			return mainTileIndex + 20;
		}
		if (upDirectionFlag) {
			if (moveUpRange(tileIndex)) {
				return getTileIndexAcrossAnimation_4(tileIndex);
			}
			return mainTileIndex + 28;
		}
		if (downDirectionFlag) { 
			if (moveDownRange(tileIndex)) {
				return getTileIndexAcrossAnimation_4(tileIndex);
			}
			return mainTileIndex + 4;
		}
		return tileIndex;
	}
	
	/**
	 * This function checks if any of the movement flags is true.
	 * 
	 * @return A boolean, true if any flag is true, false otherwise.
	 */
	public boolean positionStateUpdated() {
		return upDirectionFlag || downDirectionFlag || leftDirectionFlag || rightDirectionFlag;
	}
	
	/**
	 * This function resets the movement flags to default (false).
	 */
	public void resetPositionFlags() {
		upDirectionFlag = false;
		downDirectionFlag = false;
		leftDirectionFlag = false;
		rightDirectionFlag = false;
	}
	
	/**
	 * This function checks if a tile value indicates that the
	 * object is going upwards.
	 * 
	 * @param value  The tile value.
	 * 
	 * @return  A boolean, true if the object is going upwards, false otherwise.
	 */
	private boolean moveUpRange(int value) {
		return (value >= mainTileIndex + 28) && (value < mainTileIndex + 32);
	}
	
	/**
	 * This function checks if a tile value indicates that the
	 * object is going downwards.
	 * 
	 * @param value  The tile value.
	 * 
	 * @return  A boolean, true if the object is going downwards, false otherwise.
	 */
	private boolean moveDownRange(int value) {
		return (value >= mainTileIndex + 4) && (value < mainTileIndex + 8);
	}
	
	/**
	 * This function checks if a tile value indicates that the
	 * object is going left.
	 * 
	 * @param value  The tile value.
	 * 
	 * @return  A boolean, true if the object is going left, false otherwise.
	 */
	private boolean moveLeftRange(int value) {
		return (value >= mainTileIndex + 20) && (value < mainTileIndex + 24);
	}
	
	/**
	 * This function checks if a tile value indicates that the
	 * object is going right.
	 * 
	 * @param value  The tile value.
	 * 
	 * @return  A boolean, true if the object is going right, false otherwise.
	 */
	private boolean moveRightRange(int value) {
		return (value >= mainTileIndex + 12) && (value < mainTileIndex + 16);
	}
	
	/**
	 * This function is responsible for animating movement. A tile
	 * index is provided and the next tile index to go to is returned.
	 * 
	 * @param tileIndex  The tile index to go from.
	 * 
	 * @return  The tile index to go to.
	 */
	private int getTileIndexAcrossAnimation_4(int tileIndex) {
		if (spriteCounterMoving == RENDERS_PER_SPRITE_MOVING) {
			spriteCounterMoving = 0;
			if ((tileIndex % 4) == 2) {
				return tileIndex - 3;
			}
			return tileIndex + 1;
		}
		spriteCounterMoving++;
		return tileIndex;
	}
	
	/**
	 * This function is responsible for standing movement. A tile
	 * index is provided and the next tile index to go to is returned.
	 * 
	 * @param tileIndex  The tile index to go from.
	 * 
	 * @return  The tile index to go to.
	 */
	private int getTileIndexAcrossAnimation_2(int tileIndex) {
		if (spriteCounterStanding == RENDERS_PER_SPRITE_STANDING) {
			spriteCounterStanding = 0;
			if ((tileIndex % 4) == 0) {
				return tileIndex - 1;
			}
			return tileIndex + 1;
		}
		spriteCounterStanding++;
		return tileIndex;
	}
	
}
