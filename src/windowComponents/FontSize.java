package windowComponents;

/**
 * The {@code FontSize} enum class defines the
 * standard font sizes that can be used with
 * UI components.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public enum FontSize {
	/**
	 * This constructor constructs the standard large font size.
	 */
	LARGE(60),
	
	/**
	 * This constructor constructs the standard medium font size.
	 */
	MEDIUM(25),
	
	/**
	 * This constructor constructs the standard small font size.
	 */
	SMALL(16),
	
	/**
	 * This constructor constructs the standard textbox font size.
	 */
	TEXTBOX_SIZE(20),
	
	/**
	 * This constructor constructs the standard notes font size.
	 */
	NOTES_SIZE(12);
	
	/**
	 * The value of the font size.
	 */
	private int sizeValue;
	
	/**
	 * This constructor initializes the value of {@link #sizeValue}.
	 * 
	 * @param sizeValue  The value of the font size.
	 */
	private FontSize(int sizeValue) {
		this.sizeValue = sizeValue;
	}
	
	/**
	 * Getter for {@link #sizeValue}.
	 * 
	 * @return  The value of the font size.
	 */
	public int getValue() {
		return this.sizeValue;
	}
}