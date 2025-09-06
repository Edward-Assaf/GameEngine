package database;

/**
 * The {@code Column} class represents the information
 * of a database column.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Column {
	/**
	 * The name of the column
	 */
	private String columnName;
	
	/**
	 * The type of data the column stores.
	 */
	private String columnDataType;
	
	/**
	 * The constraints of the column
	 */
	private String coulmnConstraints;
	
	/**
	 * This constructor constructs the {@code Column} object
	 * with constraints.
	 * 
	 * @param columnName  The column's name
	 * @param columnDataType  The datatype this column stores
	 * @param coulmnConstraints  The column's constraints
	 */
	public Column(String columnName, String columnDataType, String coulmnConstraints) {
		this(columnName, columnDataType);
		this.coulmnConstraints = coulmnConstraints;
	}
	
	/**
	 * This constructor constructs the {@code Column} object
	 * without constraints.
	 * 
	 * @param columnName  The column's name
	 * @param columnDataType  The datatype this column stores
	 */
	public Column(String columnName, String columnDataType) {
		this.columnName = columnName;
		this.columnDataType = columnDataType;
		this.coulmnConstraints = "";
	}
	
	/**
	 * This overrided version of {@code toString} returns
	 * the column's signature in SQL format.
	 * 
	 * @return A {@code String}, the SQL column signature
	 */
	@Override
	public String toString() {
		return columnName + " " + columnDataType + " " + coulmnConstraints;	
	}
	
}