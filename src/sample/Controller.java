package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.*;

public class Controller{

    /**
     * Estas son algunas referencias a los textfields presentes en la interfaz grafica
     * con estas referencias se puede alterar el contenido de los textfiels
     */
   @FXML private TextField txtPuertoActual;
   @FXML private TextField txtMensaje;
   @FXML private TextField txtPuertoDestino;
   @FXML private TextField txtConversacion;

   private Persona p = new Persona();
   private int puertoactual;
   private String [] conversacion = new String [100];

    /**
     * El Thread t1 se encarga de asegurarse que el metodo escuchar() del usuario este siempre corriendo
     */
    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            p.escuchar();
        }
    });

    /**
     * *No funcionando*
     * El objetivo de este Thread era mantenerse refrescando el textfield de la conversacion, de modo que obtuviera la conversacion (atributo de la clase
     * persona) y la convirtiera a String para asi cargarla al textfield
     */
    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                String[] conversacion = p.getConversacion();
                String contenidoconversacion = "";
                int i = 0;
                while(conversacion[i]!=null) {
                    contenidoconversacion = contenidoconversacion + conversacion[i] + "\n";
                }
                txtConversacion.setText("");
                txtConversacion.setText(contenidoconversacion);
            }
        }
    });

    /**
     * El constructor de la clase Controller se encarga de inicializar el Thread t2 para mantenerse continuamente refrescando la conversacion
     */
    public Controller(){
        t2.start();
    }

    /**
     * El metodo esta_disponible se asegura que el puerto recibido como parametro se encuentra disponible para alojar una conexion
     * @param port es el puerto del que se quiere verificar su disponibilidad
     * @return Este metodo retorna true si el puerto se encuentra disponible y false si el puerto ya se encuentra ocupado
     */
    private boolean esta_disponible(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            server.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    /**
     * El metodo esta_ocupado se encarga de verificar si existe una conexion disponible en el puerto recibido por parametros, es decir que se encarga de
     * asegurar si en el puerto recibido hay algun usuario esperando un mensaje o interaccion
     * @param port es el puerto que se desea verificar si esta siendo ocupado o no
     * @return Este metodo retorna true si el puerto esta ocupado por otro usuario y false si se encuentra disponible
     */
    private boolean esta_ocupado(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            server.close();
        }catch(IOException e){
            return true;
        }
        return false;
    }

    /**
     * El metodo onIngresarbuttonaction se encarga de verificar si el puerto que desea ocupar el usuario es o no un puerto real y ademas verifica
     * si este puerto se halla disponible en el equipo. En caso de que no exista ninun error o fallo con el puerto se procedera a indicar al usuario
     * que su conexion se efectuo correctamente con el puerto indicado
     * @param event
     * @throws IOException
     */
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

    /**
     * El metodo onVerificarbuttonaction se encarga de virificar si el puerto con el que se quiere realizar la conexion y conversacion
     * actualmente se enecutra ocupado por otro usuario, en ese caso se procedera a indicar al usuario que su conexion fue correcta, por el
     * contrario se solicita al usuario reintentar con otro puerto
     * @param event
     * @throws IOException
     */
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

    /**
     * El metodo onEnviarbuttonaction se encarga de tomar el contenido del textfield txtMensaje y enviarlo emdiante el metodo enviar_mensaje
     * de la clase persona, al usuario con el que desea conversar
     * @param event
     * @throws IOException
     */
    public void onEnviarbuttonaction(ActionEvent event) throws IOException{
        String mensaje = txtMensaje.getText();
        p.enviar_mensaje(mensaje);
    }

    /**
     * El metodo onInfobuttonaction ayuda al usuario a comprender mejor el funcionamiento del programa, desplegando una lista de pasos a seguir
     * para poder utilizar autonomamente el software
     * @param event
     * @throws IOException
     */
    public void onInfobuttonaction(ActionEvent event) throws IOException{
        JOptionPane.showMessageDialog(null, "Guia de uso:\n1. Indique el puerto en el que se desae conectar\n2. Indique el puerto con el que se desea  comunicar" +
                "\n3. Escriba un mensaje y presione el boton 'Enviar' para comenzar a chatear");
    }

    /**
     * El metodo onSalirbuttonaction se encarga de cerrar la interfaz, el programa y los threads abiertos
     * @param event
     * @throws IOException
     */
    public void onSalirbuttonaction(ActionEvent event) throws IOException{
        t1.stop();
        t2.stop();
        Platform.exit();
    }
}