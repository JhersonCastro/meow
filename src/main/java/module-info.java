module com.versalles.emrms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.versalles.emrms to javafx.fxml;
    exports com.versalles.emrms;
}