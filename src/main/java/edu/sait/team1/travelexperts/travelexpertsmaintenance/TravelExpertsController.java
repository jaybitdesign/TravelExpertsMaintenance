package edu.sait.team1.travelexperts.travelexpertsmaintenance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.*;

/**
 * TravelExpertsController.java
 * Directs the handling of events and defines FXML elements.
 * James B., Ali H., Trevor P., Evan D.
 * Fall 2022
 */
public class TravelExpertsController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonView;
    @FXML
    private ChoiceBox<?> choiceBoxTable;
    @FXML
    private TableView<?> tableViewTable;

    @FXML
    void initialize() {

        /** FXML assertions */
        assert buttonAdd != null : "fx:id=\"buttonAdd\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonDelete != null : "fx:id=\"buttonDelete\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonEdit != null : "fx:id=\"buttonEdit\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonExit != null : "fx:id=\"buttonExit\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonView != null : "fx:id=\"buttonView\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert choiceBoxTable != null : "fx:id=\"choiceBoxTable\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert tableViewTable != null : "fx:id=\"tableViewTable\" was not injected: check your FXML file 'travel-experts.fxml'.";

        /** Initialize the event handler. */
        TravelExpertsHandler handler = new TravelExpertsHandler();

        /** Map table names to their labels and display them to in the choice box. */
        handler.mapChoiceBox(choiceBoxTable);

        /** Disable all the buttons until a selection is made. */
        handler.disableButtons(new Button[] { buttonView, buttonAdd, buttonEdit, buttonDelete });

        /** Add a listener to the choice box to listen for any selections. */
        choiceBoxTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                handler.setSelectionOnChange(choiceBoxTable, tableViewTable);
                handler.disableButtons(new Button[] { buttonView, buttonAdd, buttonEdit, buttonDelete });
            }
        });

        /** Add a listener to the table view to listen for any selections. */
        tableViewTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observableValue, Object o, Object t1) {
                handler.enableButtons(new Button[] { buttonView, buttonAdd, buttonEdit, buttonDelete });
            }
        });

        /** Exit the program when the exit button is clicked. */
        buttonExit.setOnAction(actionEvent -> {
            System.exit(0);
        });

        // TODO CRUD views must be able to handle a dynamic amount of data
        // Will be passed two observable lists to loop through and generate fields, one for columns one for data
        // Assume there is a selection when the button is clicked

        // TODO empty function in the handler
        buttonView.setOnAction(actionEvent -> {
            // TODO View selected record
            handler.handleViewEvent(tableViewTable.getColumns(), tableViewTable.getSelectionModel().getSelectedItem());
        });

        // TODO empty function in the handler
        buttonAdd.setOnAction(actionEvent -> {
            // TODO Add new record
            handler.handleAddEvent(tableViewTable.getColumns(), tableViewTable.getSelectionModel().getSelectedItem());
        });

        // TODO empty function in the handler
        buttonEdit.setOnAction(actionEvent -> {
            // TODO Edit selected record
            handler.handleEditEvent(tableViewTable.getColumns(), tableViewTable.getSelectionModel().getSelectedItem());
        });

        // TODO empty function in the handler
        buttonDelete.setOnAction(actionEvent -> {
            // TODO Delete selected record
            handler.handleDeleteEvent(tableViewTable.getColumns(), tableViewTable.getSelectionModel().getSelectedItem());
        });
    }
}
