package windowComponents;

/**
 * The {@code FontStyle} enum class defines the
 * standard font styles that can be used with
 * UI components.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public enum FontStyle {
	/**
	 * This constructor constructs the standard Cascadia font style.
	 */
	CASCADIA_CODE("Cascadia Code"),
	
	/**
	 * This constructor constructs the standard Kristen ITC font style.
	 */
	KRISTEN_ITC("Kristen ITC"),
	
	/**
	 * This constructor constructs the standard Tempus Sans ITC font style.
	 */
	TEMPUS_SANS_ITC("Tempus Sans ITC");
	
	/**
	 * The name of the font style.
	 */
	private String styleValue;
	
	/**
	 * This constructor initializes the name of {@link #styleValue}.
	 * 
	 * @param styleValue  The name of the font style.
	 */
	private FontStyle (String styleValue){
		this.styleValue = styleValue;
	}
	
	/**
	 * Getter for {@link #styleValue}.
	 * 
	 * @return  The value of the font style.
	 */
	public String getValue() {
		return this.styleValue;
	}
}