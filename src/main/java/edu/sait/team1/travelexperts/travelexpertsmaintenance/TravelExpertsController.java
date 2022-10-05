package edu.sait.team1.travelexperts.travelexpertsmaintenance;
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

        // TODO Loop through "tables" array to populate the ChoiceBox with choices of tables to edit

        // TODO Disable buttons until a table is chosen and an item selected

        // TODO Handle mouse click events on all buttons

        // TODO Create second form that will handle viewing/adding/updating entries

        // TODO Create delete function that is fired when "delete" is clicked. Prompt user to make sure.

        // TODO JAMES Replace " " with "" and "-" with "_" when referencing tables in a hashmap


        for (int i = 0; i < TABLE_LABELS.length; i++) {
            var table = TABLE_LABELS[i].replaceAll(" ", "").replaceAll("-", "_").toLowerCase();
            TABLE_NAMES.put(TABLE_LABELS[i], table);
            System.out.println(TABLE_LABELS[i]);
        }
        ObservableList tables = FXCollections.observableArrayList(Arrays.stream(TABLE_LABELS).toArray());
        choiceBoxTable.setItems(tables);

    }

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

    private HashMap<String, String> TABLE_NAMES = new HashMap<>();


}
