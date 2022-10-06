package edu.sait.team1.travelexperts.travelexpertsmaintenance;

import edu.sait.team1.travelexperts.travelexpertsmaintenance.database.DatabaseConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * TravelExpertsHandler.java
 * Functions for handling different events encountered in the application.
 * James B., Ali H., Trevor P., Evan D.
 * Fall 2022
 */
public class TravelExpertsHandler {

    /** Define the list of tables that we would like to access. */
    private final String[] TABLE_LABELS = {
            "Affiliations",
            "Agencies",
            "Agents",
            "Booking Details",
            "Bookings",
            "Classes",
            "Credit Cards",
            "Customers",
            "Customers-Rewards",
            "Fees",
            "Packages",
            "Packages-Products-Suppliers",
            "Products",
            "Products-Suppliers",
            "Regions",
            "Rewards",
            "Supplier Contacts",
            "Suppliers",
            "Trip Types"
    };

    /** Define the table names HashMap to map SelectionBox Labels to database table names. */
    private static HashMap<String, String> TABLE_NAMES = new HashMap<>();

    /**
     * -> getTableNames()
     * @return the table names HashMap.
     * James B.
     */
    public static HashMap<String, String> getTableNames() { return TABLE_NAMES; }

    /**
     * -> setSelectionOnChange()
     * Retrieves database information and applies it with updateTable() whenever the ChoiceBox changes its selection.
     * @firedby TravelExpertsController
     * @param choiceBox
     * @param tv
     * James B.
     */
    public void setSelectionOnChange(ChoiceBox choiceBox, TableView tv) {

        /** Gets the database table name from the Name/Label HashMap. */
        String name = getTableNames().get(choiceBox.getValue());

        /** Executes a prepared statement that selects every entity from the chosen table. */
        ResultSet rs = DatabaseConnection.executeStatement("SELECT * FROM " + name);

        /** Updates the TableView with the ResultSet. */
        try {
            updateTableView(rs, tv);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * -> updateTable()
     * Updates the table and table columns with the information retrieved when making a selection.
     * @param rs
     * @param tv
     * @throws SQLException
     * James B.
     */
    public static void updateTableView(ResultSet rs, TableView tv) throws SQLException {

        /** Clear any existing columns & items. */
        tv.getColumns().clear();
        tv.getItems().clear();

        /** Create an empty ObservableList object. */
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        /** Loop through the ResultSet metadata with the column count to add the columns. */
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

            /** Finalize our iterator. */
            final int j = i;

            /** Create a new TableColumn object with the name determined by the iterator. */
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));

            /** Return a new SimpleStringProperty if not null, otherwise return a blank SimpleStringProperty. */
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    if(param.getValue().get(j) != null) { return new SimpleStringProperty(param.getValue().get(j).toString()); }
                    else { return new SimpleStringProperty(""); }
                }
            });

            /** Add the column to the TableView. */
            tv.getColumns().addAll(col);
        }

        /** Add new rows into the data object. */
        while(rs.next()) {

            /** Create a blank ObservableList. */
            ObservableList<String> row = FXCollections.observableArrayList();

            /** Iterate over each column to get its data. */
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

                /** Add the column data to the row. */
                row.add(rs.getString(i));
            }

            /** Add the row to the data object. */
            data.add(row);
        }

        /** Set the TableView's items to the data object. */
        tv.setItems(data);
    }

    /**
     * -> mapLabelsToNames()
     * Makes corrections to labels to make them equal to their table counterparts and map them both to a hash map.
     * Sets the ChoiceBox's values as the different table labels.
     * @param choiceBoxTable
     * James B.
     */
    public void mapChoiceBox(ChoiceBox<?> choiceBoxTable) {

        /** Loop through the table labels. */
        for (int i = 0; i < TABLE_LABELS.length; i++) {

            /** Make the necessary replacements, ' ' with '', '-' with '_'. */
            var table = TABLE_LABELS[i].replaceAll(" ", "").replaceAll("-", "_").toLowerCase();

            /** Add the table name to the hash map with its label as the key. */
            getTableNames().put(TABLE_LABELS[i], table);
        }

        /** Sets the choice box's data to the table labels. */
        ObservableList tables = FXCollections.observableArrayList(Arrays.stream(TABLE_LABELS).toArray());
        choiceBoxTable.setItems(tables);
    }

    /**
     * -> disableButtons()
     * Disables all buttons.
     * @param buttons
     * James B.
     */
    public void disableButtons(Button[] buttons) {
        for (Button button: buttons) {
            button.setDisable(true);
        }
    }

    /**
     * -> enableButtons()
     * Enables all buttons.
     * @param buttons
     * James B.
     */
    public void enableButtons(Button[] buttons) {
        for (Button button: buttons) {
            button.setDisable(false);
        }
    }

    /**
     * ->
     * > desc <
     * @param columns
     * @param selectedItem
     * > author <
     */
    public void handleViewEvent(ObservableList<? extends TableColumn<?,?>> columns, Object selectedItem) {

    }

    /**
     * ->
     * > desc <
     * @param columns
     * @param selectedItem
     * > author <
     */
    public void handleAddEvent(ObservableList<? extends TableColumn<?,?>> columns, Object selectedItem) {

    }

    /**
     * ->
     * > desc <
     * @param columns
     * @param selectedItem
     * > author <
     */
    public void handleEditEvent(ObservableList<? extends TableColumn<?,?>> columns, Object selectedItem) {

    }

    /**
     * ->
     * > desc <
     * @param columns
     * @param selectedItem
     * > author <
     */
    public void handleDeleteEvent(ObservableList<? extends TableColumn<?,?>> columns, Object selectedItem) {

    }
}
