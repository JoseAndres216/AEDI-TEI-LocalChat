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
import java.net.ServerSocket;

import javax.swing.*;

public class Controller{

   @FXML private TextField txtPuerto;
   @FXML private TextField txtPuertoActual;

   private Persona p = new Persona();

    private boolean esta_disponible(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            server.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    @FXML
    public void onIngresarbuttonaction(ActionEvent event) throws IOException{
        String txtpuerto = txtPuerto.getText();
        int puerto = 0;
        if(txtpuerto != "" && Integer.parseInt(txtpuerto)>=0 && Integer.parseInt(txtpuerto)<=65535){
            puerto = Integer.parseInt(txtpuerto);
            if(esta_disponible(puerto)){
                p.setpuerto(puerto);
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        p.escuchar();
                    }
                });
                t1.start();
                Parent view2 = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene MainScreen = new Scene(view2);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(MainScreen);
                window.show();
                JOptionPane.showMessageDialog(null, "Ahora estas disponible en el puerto: " + puerto);
            }
            else{
                JOptionPane.showMessageDialog(null, "El puerto no esta disponible, por favor ingrese un nuevo puerto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, digite un puerto valido, Ej:12345");
        }
    }

    public void onSalirbuttonaction(ActionEvent event) throws IOException{
        Platform.exit();
    }


}