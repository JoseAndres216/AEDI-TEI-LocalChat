package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.*;

public class Controller{

   @FXML private TextField txtPuertoActual;
   @FXML private TextField txtMensaje;
   @FXML private TextField txtPuertoDestino;
   @FXML private TextField txtConversacion;

   private Persona p = new Persona();
   private int puertoactual;
   private String [] conversacion = new String [100];

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            p.escuchar();
        }
    });

    private boolean esta_disponible(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            server.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    private boolean esta_ocupado(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            server.close();
        }catch(IOException e){
            return true;
        }
        return false;
    }

    @FXML
    public void onIngresarbuttonaction(ActionEvent event) throws IOException{
        String txtpuertoactual = txtPuertoActual.getText();
        int puertoactual = 0;
        if(txtpuertoactual != "" && Integer.parseInt(txtpuertoactual)>=0 && Integer.parseInt(txtpuertoactual)<=65535){
            puertoactual = Integer.parseInt(txtpuertoactual);
            if(esta_disponible(puertoactual)){
                p.setPuertoactual(puertoactual);
                t1.start();
                this.puertoactual = puertoactual;
                JOptionPane.showMessageDialog(null, "Ahora estas disponible en el puerto: " + puertoactual);
                txtPuertoDestino.setEditable(true);
                txtPuertoDestino.setDisable(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "El puerto no esta disponible, por favor ingrese un nuevo puerto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, digite un puerto valido, Ej:12345");
        }
    }

    public void onVerificarbuttonaction(ActionEvent event) throws IOException{
        String txtpuertodestino = txtPuertoDestino.getText();
        int puertodestino = 0;
        if(txtpuertodestino != "" && Integer.parseInt(txtpuertodestino)>=0 && Integer.parseInt(txtpuertodestino)<=65535){
            puertodestino = Integer.parseInt(txtpuertodestino);
            if(esta_ocupado(puertodestino) && puertodestino!=this.puertoactual){
                p.setPuertodestino(puertodestino);
                JOptionPane.showMessageDialog(null, "Ahora estas conectado con el puerto: " + puertodestino);
                txtMensaje.setEditable(true);
                txtMensaje.setDisable(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "El puerto no esta ocupado por otro usuario o estas intentando conversar contigo mismo :'(, por favor ingrese un nuevo puerto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, digite un puerto valido, Ej:12345");
        }
    }

    public void onEnviarbuttonaction(ActionEvent event) throws IOException{
        String mensaje = txtMensaje.getText();
        System.out.println("1");
        p.enviar_mensaje(mensaje);
    }

    public void RefrescarConversacion(String mensajenuevo){
        System.out.println("8");
        int i = 0;
        while(this.conversacion[i] != null){
            i++;
        }
        this.conversacion[i] = mensajenuevo;
        txtConversacion.setText("");
        i=0;
        String conversacion = "";
        while(this.conversacion[i]!=null){
            conversacion = conversacion  + this.conversacion[i] + "\n";
        }
        txtConversacion.setText(conversacion);
        return;
    }

    public void onInfobuttonaction(ActionEvent event) throws IOException{
        JOptionPane.showMessageDialog(null, "Guia de uso:\n1. Indique el puerto en el que se desae conectar\n2. Indique el puerto con el que se desea  comunicar" +
                "\n3. Escriba un mensaje y presione el boton 'Enviar' para comenzar a chatear");
    }

    public void onSalirbuttonaction(ActionEvent event) throws IOException{
        t1.stop();
        Platform.exit();
    }


}