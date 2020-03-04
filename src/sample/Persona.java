package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Persona{
    private int puertoactual;
    private int puertodestino;

    public void setPuertoactual(int puertoactual){
        this.puertoactual = puertoactual;
    }
    public void setPuertodestino(int puertodestino){ this.puertodestino = puertodestino; }

    public void enviar_mensaje(String Mensaje){
        try {
            Socket client = new Socket("127.0.0.1", this.puertodestino);
            System.out.println("2");
            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(Mensaje);
            System.out.println("3");
            writer.flush();
            client.close();
            System.out.println("4");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void escuchar(){
        boolean active = true;
        try{
            ServerSocket server = new ServerSocket(this.puertoactual);
            while(active){
                Socket entrante = server.accept();
                BufferedReader lector = new BufferedReader(new InputStreamReader(entrante.getInputStream()));
                String mensaje_recibido = lector.readLine();
                System.out.println("Mensaje que se debia mostrar: " + mensaje_recibido);
                Controller c = new Controller();
                c.RefrescarConversacion(mensaje_recibido);
                System.out.println("6");
                entrante.close();
                System.out.println("7");
            }
            System.out.println("8");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
