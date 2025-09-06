package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code MySQLConnector} class establishes the connection
 * with the database. And It executes queries and statements.
 *
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class MySQLConnector {
	/**
	 * An identifier that defines the JDBC driver for the database.
	 */
	public static final String localHostJConnectorURL = "jdbc:sqlite:the_islanders_curse.db";
	
	/**
	 * This constructor initializes the database.
	 */
	public MySQLConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			executeStatement(DatabaseUtilities.getAccountsTableCreateStatement());
			executeStatement(DatabaseUtilities.getUsersLevelsTableCreateStatement());
			executeStatement(DatabaseUtilities.getGameDataTableCreateStatement());
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Database JDBC driver could not be used");
	    }
	}
	
	/**
	 * This function takes a statement in SQL format, and executes it. The
	 * statement should NOT return any results, as it is not retrievable using
	 * this function. For that, use {@link #getQueryResult(String)}.
	 * 
	 * @param statement  The statement in SQL format to be executed.
	 */
	public void executeStatement(String statement) {
		try {
			Connection connection = DriverManager.getConnection(localHostJConnectorURL);
	        PreparedStatement statementExecuter = connection.prepareStatement(statement);
	        statementExecuter.execute();
	        statementExecuter.close();
	        connection.close();
		} catch(SQLException error) {
	        System.out.println("Statement execution in database failed");
	        System.out.println(statement);
		}
	}
	
	/**
	 * This function takes a query in SQL format, and runs it, and
	 * returns the result of the query. The query should NOT be a
	 * CRUD statement (or any statement that does not return a result
	 * when executed). For that, use {@link #executeStatement(String)}.
	 * 
	 * <p><b>Note:</b> The result is returned as an {@code ArrayList}, each
	 * element, in order, representing a row of the result. To get the value
	 * of some column at some row, do: {@code arrayList.get(rowIndex).get(columnName)}.</p>
	 * 
	 * @param query  The query in SQL format to be executed.
	 * 
	 * @return A {@link java.util.ArrayList} object that contains the results of the query.
	 */
	public ArrayList<HashMap<String, Object>> getQueryResult(String query) {
		try {
			// Establishing connections and initializing result array.
	        Connection connection = DriverManager.getConnection(localHostJConnectorURL);
	        PreparedStatement statementExecuter = connection.prepareStatement(query);
	        ArrayList<HashMap<String, Object>> finalResults = new ArrayList<>();
	        
	        // Getting result from database and getting its metadata (metadata is column names, count.. etc).
	        ResultSet queryResult = statementExecuter.executeQuery();
	        ResultSetMetaData queryResultMetaData = queryResult.getMetaData();
	        int columnCount = queryResultMetaData.getColumnCount();
	        
	        // Adding results one by one to the result array.
	        while (queryResult.next()) {
	        	HashMap<String, Object> row = new HashMap<>();
	        	for (int i = 1; i <= columnCount; i++) {
	        		row.put(queryResultMetaData.getColumnName(i), queryResult.getObject(i));
	        	}
	        	finalResults.add(row);
	        }
	        
	        // Closing connections and returning result array.
	        statementExecuter.close();
	        connection.close();
	        queryResult.close();
	        return finalResults;
		} catch (SQLException error) {
			System.out.println("Statement execution in database failed");
			System.out.println(query);
	        return null;
		}
	}
}