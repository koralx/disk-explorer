module com.koral.diskexplorer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.koral.diskexplorer to javafx.fxml;
    exports com.koral.diskexplorer;
}