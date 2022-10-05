module edu.sait.team1.travelexperts.travelexpertsmaintenance {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens edu.sait.team1.travelexperts.travelexpertsmaintenance to javafx.fxml;
    exports edu.sait.team1.travelexperts.travelexpertsmaintenance;
}