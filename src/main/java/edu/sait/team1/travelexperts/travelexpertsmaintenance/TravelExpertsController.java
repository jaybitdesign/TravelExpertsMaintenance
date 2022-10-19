package edu.sait.team1.travelexperts.travelexpertsmaintenance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
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
    private TableView<ObservableList<?>> tableViewTable;

    /** Initialize the event handler. */
    TravelExpertsHandler handler = new TravelExpertsHandler();

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

        /** Map table names to their labels and display them to in the choice box. */
        handler.mapChoiceBox(choiceBoxTable);

        /** Disable all the buttons until a selection is made. */
        handler.setButtonState(true, new Button[] { buttonView, buttonAdd, buttonEdit, buttonDelete });

        /** Add a listener to the choice box to listen for any selections. */
        choiceBoxTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(observableValue != null) {
                    handler.setSelectionOnChange(choiceBoxTable, tableViewTable);
                    handler.setButtonState(true, new Button[] { buttonView, buttonEdit, buttonDelete });
                    handler.setButtonState(false, buttonAdd);
                }
            }
        });

        /** Add a listener to the table view to listen for any selections. */
        tableViewTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observableValue, Object o, Object t1) {
                if(observableValue != null) {
                    handler.setButtonState(false, new Button[] { buttonView, buttonEdit, buttonDelete });
                }
            }
        });

        /** Exit the program when the exit button is clicked. */
        buttonExit.setOnAction(actionEvent -> {
            System.exit(0);
        });

        buttonView.setOnAction(actionEvent -> {
            // TODO View selected record
            handler.handleViewEvent(tableViewTable.getColumns(), choiceBoxTable.getSelectionModel().getSelectedItem().toString(), tableViewTable.getSelectionModel().getSelectedItem(), choiceBoxTable, tableViewTable);
        });

        buttonAdd.setOnAction(actionEvent -> {
            // TODO Add new record
            handler.handleAddEvent(tableViewTable.getColumns(), choiceBoxTable.getSelectionModel().getSelectedItem().toString(), choiceBoxTable, tableViewTable);
        });

        buttonEdit.setOnAction(actionEvent -> {
            // TODO Edit selected record
            handler.handleEditEvent(tableViewTable.getColumns(), choiceBoxTable.getSelectionModel().getSelectedItem().toString(), tableViewTable.getSelectionModel().getSelectedItem(), choiceBoxTable, tableViewTable);
        });

        buttonDelete.setOnAction(actionEvent -> {
            // TODO Delete selected record
            handler.handleDeleteEvent(tableViewTable.getColumns(), choiceBoxTable.getSelectionModel().getSelectedItem().toString(), tableViewTable.getSelectionModel().getSelectedItem(), choiceBoxTable, tableViewTable);
        });
    }
}
