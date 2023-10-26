module gamershowcase.gamershowcaseworking3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens gamershowcase.gamershowcaseworking3 to javafx.fxml;
    exports gamershowcase.gamershowcaseworking3;
}