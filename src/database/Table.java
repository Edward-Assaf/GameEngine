package database;

import java.util.ArrayList;

/**
 * The {@code Table} class represents the information
 * of a database table.
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class Table {
	/**
	 * The name of the table.
	 */
	private String tableName;
	
	/**
	 * An array of columns that this table contains.
	 */
	private ArrayList<Column> tableColumns = new ArrayList<>();
	
	/**
	 * An array of constraints that this table contains.
	 */
	private ArrayList<String> tableConstraints = new ArrayList<>();
	
	/**
	 * A constructor to initialize the name of the table.
	 * 
	 * @param tableName  The name of the table.
	 */
	public Table(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * This function takes a {@link database.Column} object, then adds it
	 * to the table if it doesn't already exists.
	 * 
	 * @param column  The {@code column} to be added.
	 */
	public void addColumn(Column column) {
		if (tableColumns.contains(column)) {
			return;
		}
		tableColumns.add(column);
	}
	
	/**
	 * This function takes a constraint, then adds it
	 * to the table if it doesn't already exists.
	 * 
	 * @param constraint  The constraint to be added.
	 */
	public void addConstraint(String constraint) {
		if (tableConstraints.contains(constraint)) {
			return;
		}
		tableConstraints.add(constraint);
	}
	
	/**
	 * This function takes a {@link database.Column} object, then removes it
	 * from the table if it exists.
	 * 
	 * @param column  The {@code column} to be removed.
	 */
	public void dropColumn(Column column) {
		tableColumns.remove(column);
	}
	
	/**
	 * This function takes a constraint, then removes it
	 * from the table if it exists.
	 * 
	 * @param constraint  The constraint to be removed.
	 */
	public void dropConstraint(String constraint) {
		tableConstraints.remove(constraint);
	}
	
	/**
	 * This function builds the CREATE statement of the table
	 * in SQL format, then returns it as a {@code String}.
	 * 
	 * @return A {@code String}, the CREATE statement.
	 */
	public String getCreateStatement() {
		// Initializations.
		String statement = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
		int columnsSize = tableColumns.size();
		int constraintsSize = tableConstraints.size();
		
		// Adding column signatures to the CREATE statement.
		for(int i = 0; i < columnsSize; i++) {
			statement += tableColumns.get(i).toString();
			if (i < columnsSize - 1) {
				statement += ",";
			}
		}
		
		// Adding constraint strings to the CREATE statement.
		if(constraintsSize > 0) {
			statement += ",";
		}
		for(int i = 0; i < constraintsSize; i++) {
			statement += tableConstraints.get(i);
			if (i < constraintsSize - 1) {
				statement += ",";
			}
		}
		
		// Closing and returning the CREATE statement.
		statement += ");";
		return statement;
	}
}