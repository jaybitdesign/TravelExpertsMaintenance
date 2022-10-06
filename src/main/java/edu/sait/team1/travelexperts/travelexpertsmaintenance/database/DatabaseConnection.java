package edu.sait.team1.travelexperts.travelexpertsmaintenance.database;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;

public class DatabaseConnection {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String USER = "admin";
    private static final String PASS = "password";

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeStatement(String statement) {
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
