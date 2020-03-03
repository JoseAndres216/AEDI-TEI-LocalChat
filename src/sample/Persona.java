package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Persona{
    private int puerto;

    public void setpuerto(int puerto){
        this.puerto = puerto;
    }

    public void enviar_mensaje(String Mensaje, int puerto_meta){
        try {
            Socket client = new Socket("127.0.0.1", puerto_meta);
            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(Mensaje);
            writer.flush();
            client.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void escuchar(){
        boolean active = true;
        try{
            ServerSocket server = new ServerSocket(this.puerto);
            while(active){
                Socket entrante = server.accept();
                BufferedReader lector = new BufferedReader(new InputStreamReader(entrante.getInputStream()));
                String mensaje_recibido = lector.readLine();
                System.out.println("Mensaje: " + mensaje_recibido);
                entrante.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
