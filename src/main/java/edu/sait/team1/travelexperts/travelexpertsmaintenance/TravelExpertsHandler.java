package edu.sait.team1.travelexperts.travelexpertsmaintenance;

import edu.sait.team1.travelexperts.travelexpertsmaintenance.database.DatabaseConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * TravelExpertsHandler.java
 * Functions for handling different events encountered in the application.
 * James B.
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
    public void setSelectionOnChange(ChoiceBox choiceBox, TableView tv){

        /** Gets the database table name from the Name/Label HashMap. */
        String name = getTableNames().get(choiceBox.getValue());

        /** Executes a prepared statement that selects every entity from the chosen table. */
        ResultSet rs = DatabaseConnection.executeStatement("SELECT * FROM " + name);

        /** Updates the TableView with the ResultSet. */
        try {
            updateTableView(rs, tv);
        } catch (SQLException ex) {
            System.out.println("SQL Error Code: " + ex.getErrorCode() + " / " + ex.getSQLState());
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

        if(rs != null) {

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
     * -> setButtonsState()
     * Sets the state of all buttons in an array.
     * @param disabled
     * @param buttons
     * James B.
     */
    public void setButtonState(boolean disabled, Button[] buttons) {
        for (Button button: buttons) {
            button.setDisable(disabled);
        }
    }

    /**
     * -> createNewView()
     * Creates a new view with a certain function.
     * @param function
     * @param tableName
     * @param columns
     * @param selectedItem
     * @param choiceBoxTable
     * @param tableViewTable
     * James B.
     */
    public void createNewView(String function, String tableName, ObservableList<? extends TableColumn<?,?>> columns, ObservableList<?> selectedItem, ChoiceBox choiceBoxTable, TableView tableViewTable) {

        /**
         * Create a new cancel alert
         */
        Alert confirmCancel = new Alert(Alert.AlertType.WARNING);
        ButtonType yesConfirmCancel = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noConfirmCancel = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirmCancel.setTitle("Confirm Cancel");
        confirmCancel.setContentText("Are you sure you want to cancel?");
        confirmCancel.getButtonTypes().setAll(yesConfirmCancel, noConfirmCancel);

        /**
         * Create a new save alert
         */
        Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType yesConfirmSave = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noConfirmSave = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirmSave.setTitle("Confirm Save");
        confirmSave.setContentText("Confirm save? Ensure all information is correct.");
        confirmSave.getButtonTypes().setAll(yesConfirmSave, noConfirmSave);

        /**
         * Change the text of the save alert if it is a delete function
         */
        if(function.equalsIgnoreCase("delete")) {
            confirmSave.setTitle("Confirm Delete");
            confirmSave.setContentText("Confirm delete? This cannot be undone!");
        }

        /**
         * Create a new stage
         */
        Stage stage = new Stage();
        stage.setTitle("Travel Experts - " + tableName);
        stage.setResizable(false);

        /**
         * Create 35% - 65% column constraints for the grid pane
         */
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(35);
        columnConstraints2.setPercentWidth(65);

        /**
         * Create a new grid view
         */
        GridPane pane = new GridPane();
        pane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        pane.setPadding(new Insets(10));
        pane.setPrefWidth(380);
        pane.setPrefHeight(260);
        pane.setVgap(10);

        /**
         * Loop through the columns and add the appropriate labels and text fields
         */
        for (int i = 0; i < columns.size(); i++) {
            String labelText = columns.get(i).getText();
            Label label = new Label(labelText);
            TextField text = new TextField();
            text.setId(labelText);
            /**
             * If the function is add, add text to the text fields
             */
            if(!function.equalsIgnoreCase("add") && selectedItem != null && selectedItem.get(i) != null) {
                text.setText(selectedItem.get(i).toString());
            }
            /**
             * If the function is view, set the text fields to un-editable
             */
            if (i == 0 || function.equalsIgnoreCase("view") || function.equalsIgnoreCase("delete")) {
                text.setEditable(false);
            }
            /**
             * Add the labels and text fields to the grid pane
             */
            pane.add(label, 0, i);
            pane.add(text, 1, i);
        }

        /**
         * Create a new save button
         */
        Button buttonSave = new Button("Save");
        if(function.equalsIgnoreCase("view")) {
            buttonSave.setDisable(true);
        } else if (function.equalsIgnoreCase("delete")) {
            buttonSave.setText("Delete");
        }
        buttonSave.setStyle("-fx-background-radius: 0");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Loop through the text fields and add them to an array, then execute SQL statements
             * @param actionEvent
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                confirmSave.showAndWait().ifPresent(type -> {
                    if(type.equals(yesConfirmSave)) {
                        //TODO SAVE
                        ArrayList<TextField> fields = new ArrayList<>();
                        for (int i = 0; i < pane.getChildren().size(); i++) {
                            Node field = pane.getChildren().get(i);
                            if(field instanceof TextField) {
                                fields.add((TextField) field);
                            }
                        }

                        if(!function.equalsIgnoreCase("view")) {/** Make the necessary replacements, ' ' with '', '-' with '_'. */
                            String table = tableName.replaceAll(" ", "").replaceAll("-", "_").toLowerCase();
                            String statement = "";
                            String sqlInsert = "";
                            String sqlValues = "";
                            if (function.equalsIgnoreCase("add")) {
                                for (int i = 1; i < fields.size(); i++) {
                                    sqlInsert += fields.get(i).getId() + ", ";
                                    sqlValues += "'" + fields.get(i).getText() + "', ";
                                }
                                statement = "INSERT INTO " + table + " (" + sqlInsert + ") VALUES (" + sqlValues + ");";
                            }
                            else if (function.equalsIgnoreCase("edit")) {
                                String editSQL = "";
                                String id = "";
                                String idValue = "";
                                for (int i = 0; i < fields.size(); i++) {
                                    if(i == 0) {
                                        id = fields.get(i).getId();
                                        idValue = fields.get(i).getText();
                                    }
                                    editSQL += fields.get(i).getId() + " = '" + fields.get(i).getText() + "', ";
                                }
                                statement = "UPDATE " + table + " SET " + editSQL.substring(0, editSQL.length() - 2) + " WHERE " + id + " = " + idValue + ";";
                            }
                            else if(function.equalsIgnoreCase("delete")) {
                                String id = "";
                                String idValue = "";
                                if(fields.get(0).getId() != null && fields.get(0).getText() != null) {
                                    id = fields.get(0).getId();
                                    idValue = fields.get(0).getText();
                                }
                                statement = "DELETE FROM " + table + " WHERE " + id + " = " + idValue + ";";
                            }

                            DatabaseConnection.executeStatement(statement.replaceAll(", \\)", ")"));
                            System.out.println(statement.replaceAll(", \\)", ")"));
                        }
                        setSelectionOnChange(choiceBoxTable, tableViewTable);
                        stage.close();
                    } else {
                        confirmSave.close();
                    }
                });
            }
        });

        Button buttonCancel = new Button("Cancel");
        buttonCancel.setStyle("-fx-background-radius: 0");
        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!function.equalsIgnoreCase("view")) {
                    confirmCancel.showAndWait().ifPresent(type -> {
                        if (type.equals(yesConfirmCancel)) {
                            stage.close();
                        } else {
                            confirmCancel.close();
                        }
                    });
                } else {
                    stage.close();
                }
            }
        });

        Label table = new Label(" " + tableName);
        table.setStyle("-fx-font: 20 arial;");

        HBox menu = new HBox();
        menu.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menu.getChildren().add(buttonSave);
        menu.getChildren().add(buttonCancel);
        menu.getChildren().add(table);

        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setLayoutY(40);

        VBox anchor = new VBox();
        anchor.setPrefWidth(400);
        anchor.setPrefHeight(300);
        anchor.getChildren().add(menu);
        anchor.getChildren().add(scrollPane);

        Scene scene = new Scene(anchor, 400, 260);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * -> setButtonsState()
     * Sets the state of a single button.
     * @param disabled
     * @param button
     * James B.
     */
    public void setButtonState(boolean disabled, Button button) {
        button.setDisable(disabled);
    }

    public void handleViewEvent(ObservableList<? extends TableColumn<?,?>> columns, String tableName, ObservableList<?> selectedItem, ChoiceBox choiceBoxTable, TableView tableViewTable) {
        createNewView("view", tableName, columns, selectedItem, choiceBoxTable, tableViewTable);
    }

    public void handleAddEvent(ObservableList<? extends TableColumn<?,?>> columns, String tableName, ChoiceBox choiceBoxTable, TableView tableViewTable) {
        createNewView("add", tableName, columns, null, choiceBoxTable, tableViewTable);
    }

    public void handleEditEvent(ObservableList<? extends TableColumn<?,?>> columns, String tableName, ObservableList<?> selectedItem, ChoiceBox choiceBoxTable, TableView tableViewTable) {
        createNewView("edit", tableName, columns, selectedItem, choiceBoxTable, tableViewTable);
    }

    public void handleDeleteEvent(ObservableList<? extends TableColumn<?,?>> columns, String tableName, ObservableList<?> selectedItem, ChoiceBox choiceBoxTable, TableView tableViewTable) {
        createNewView("delete", tableName, columns, selectedItem, choiceBoxTable, tableViewTable);
    }
}
