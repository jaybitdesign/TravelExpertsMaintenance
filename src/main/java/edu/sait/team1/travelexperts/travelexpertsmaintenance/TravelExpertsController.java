package edu.sait.team1.travelexperts.travelexpertsmaintenance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.*;

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
        assert buttonAdd != null : "fx:id=\"buttonAdd\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonDelete != null : "fx:id=\"buttonDelete\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonEdit != null : "fx:id=\"buttonEdit\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonExit != null : "fx:id=\"buttonExit\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert buttonView != null : "fx:id=\"buttonView\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert choiceBoxTable != null : "fx:id=\"choiceBoxTable\" was not injected: check your FXML file 'travel-experts.fxml'.";
        assert tableViewTable != null : "fx:id=\"tableViewTable\" was not injected: check your FXML file 'travel-experts.fxml'.";

        TravelExpertsHandler handler = new TravelExpertsHandler();

        // TODO Disable buttons until a table is chosen and an item selected

        // TODO Create second view that will handle viewing/adding/updating entries

        // TODO Create delete function that is fired when "delete" is clicked. Prompt user to make sure.

        // TODO JAMES DONE Handle change of selection on choice box
        choiceBoxTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                handler.setSelectionOnChange(choiceBoxTable, tableViewTable);
            }
        });

        // TODO Handle mouse click events on all buttons
        buttonExit.setOnAction(actionEvent -> {
            System.exit(0);
        });

        // TODO JAMES DONE Loop through "tables" array to populate the ChoiceBox with choices of tables to edit
        // TODO JAMES DONE Replace " " with "" and "-" with "_" when referencing tables in a hashmap
        handler.mapLabelsToNames(choiceBoxTable);

    }

}
