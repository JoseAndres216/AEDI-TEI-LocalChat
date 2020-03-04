package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La clase persona se encarga de definir el disenio de cada usuario y de permitir su comunicacion con otros usuarios
 */
public class Persona{
    private int puertoactual;
    private int puertodestino;
    private String[] conversacion = new String[100];

    /**
     * El metodo setPuertoactual ajusta el puerto donde se halla disponible la persona
     * @param puertoactual
     */
    public void setPuertoactual(int puertoactual){ this.puertoactual = puertoactual; }

    /**
     * El metodo set Puertodestino ajusta el puerto de la persona con la que se desea comunicar
     * @param puertodestino
     */
    public void setPuertodestino(int puertodestino){ this.puertodestino = puertodestino; }

    /**
     * El metodo getconversacion retorna la conversacion (mensajes recibidos) de la persona
     * @return
     */
    public String[] getConversacion(){ return this.conversacion; }

    /**
     * El metodo enviar_mensaje se encarga de comunicarse con el puerto meta y enviar el mensaje que recibe como parametro
     * @param Mensaje es el mesaje que se desea enviar
     */
    public void enviar_mensaje(String Mensaje){
        try {
            Socket client = new Socket("127.0.0.1", this.puertodestino);
            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(Mensaje);
            writer.flush();
            client.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * El metodo escuchar se aloja en un Thread en la clase controller que le permite estar escuchando en caso de que llegue un mensaje al puerto
     * en el que se encuentra disponible el usuario
     */
    public void escuchar(){
        boolean active = true;
        try{
            ServerSocket server = new ServerSocket(this.puertoactual);
            while(active){
                Socket entrante = server.accept();
                BufferedReader lector = new BufferedReader(new InputStreamReader(entrante.getInputStream()));
                String mensaje_recibido = lector.readLine();
                System.out.println("Mensaje que se debia mostrar: " + mensaje_recibido);
                int i = 0;
                while(this.conversacion[i]!=null){
                    i++;
                }
                conversacion[i]=mensaje_recibido;
                entrante.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
