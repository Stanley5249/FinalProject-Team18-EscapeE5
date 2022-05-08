module com.team18.escapee5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.team18.escapee5 to javafx.fxml;
    exports com.team18.escapee5;
}