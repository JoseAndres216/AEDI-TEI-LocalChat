package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerMS {

    @FXML
    private TextField txtPuertoActual;

    public void main(String [] args){
        txtPuertoActual.setText("Hola");
    }
}