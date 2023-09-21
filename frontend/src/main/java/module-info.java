module com.mycompany.gamershowcase {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.gamershowcase to javafx.fxml;
    exports com.mycompany.gamershowcase;
}
