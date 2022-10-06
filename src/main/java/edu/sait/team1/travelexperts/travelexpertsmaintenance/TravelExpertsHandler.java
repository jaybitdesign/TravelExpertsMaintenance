package edu.sait.team1.travelexperts.travelexpertsmaintenance;

import edu.sait.team1.travelexperts.travelexpertsmaintenance.database.DatabaseConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelExpertsHandler {

    public void setSelectionOnChange(ChoiceBox choiceBox, TableView tv) {
        String name = TravelExpertsController.getTableNames().get(choiceBox.getValue());
        ResultSet rs = DatabaseConnection.executeStatement("SELECT * FROM " + name);
        try {
            updateTable(rs, tv);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTable(ResultSet rs, TableView tv) throws SQLException {

        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        tv.getColumns().clear();

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    if(param.getValue().get(j) != null) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            tv.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }

        tv.getItems().clear();

        while(rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        tv.setItems(data);
    }
}
