package edu.sait.team1.travelexperts.travelexpertsmaintenance.database;

import java.sql.*;

/**
 * DatabaseConnection.java
 * Helper functions to access our database information more conveniently.
 * James B., Ali H., Trevor P., Evan D.
 * Fall 2022
 */
public class DatabaseConnection {

    /** Define our connection string. */
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/travelexperts";

    /** Define our username and password. */
    private static final String USER = "admin";
    private static final String PASS = "password";

    /**
     * -> getConnection
     * @return Connection to our database by using our defined arguments.
     * James B.
     */
    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * -> executeStatement()
     * Executes the prepared statement and returns the ResultSet.
     * @param statement
     * @return ResultSet from our executed SQL.
     * James B.
     */
    public static ResultSet executeStatement(String statement) {
        try {
            /** Define a new PreparedStatement. */
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);

            /** Execute the prepared statement. */
            preparedStatement.execute();

            /** Return the result set */
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * -> executeStatement()
     * Executes the prepared statement after doing replacement and returns the ResultSet.
     * @param statement
     * @param parameters
     * @return ResultSet from our executed SQL.
     * James B.
     */
    public ResultSet executeStatement(String statement, String[] parameters) {
        try {

            /** Define a new PreparedStatement. */
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);

            /** Iterate through the parameters and replace the correlating '?'. */
            for (int i = 0; i <= parameters.length; i++) {
                preparedStatement.setString(i, parameters[i]);
            }

            /** Execute the prepared statement. */
            preparedStatement.execute();

            /** Return the result set */
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
