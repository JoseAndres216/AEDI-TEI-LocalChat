package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller{

   @FXML private TextField txtPuerto;

    private boolean esta_disponible(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            server.close();
        }catch(IOException e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    @FXML
    public void onIngresarbuttonaction(ActionEvent event) throws IOException{
        String txtpuerto = txtPuerto.getText();
        int puerto = Integer.parseInt(txtpuerto);
        if(esta_disponible(puerto)){
            System.out.println("El socket esta disponible");
            Persona p = new Persona(puerto);
            Parent view2 = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene MainScreen = new Scene(view2);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(MainScreen);
            window.show();
        }
        else{
            System.out.println("No disponible");
        }
    }

    public void onSalirbuttonaction(ActionEvent event) throws IOException{
        Platform.exit();
    }


}
