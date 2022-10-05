package edu.sait.team1.travelexperts.travelexpertsmaintenance.database;

import java.sql.*;

public class DatabaseConnection {

    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/sonoo";
    private final String USER = "user";
    private final String PASS = "password";

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_STRING, USER, PASS);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet executeStatement(String statement) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);
            preparedStatement.execute();
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeStatement(String statement, String[] parameters) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(statement);
            for (int i = 0; i <= parameters.length; i++) {
                preparedStatement.setString(i, parameters[i]);
            }
            preparedStatement.execute();
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
