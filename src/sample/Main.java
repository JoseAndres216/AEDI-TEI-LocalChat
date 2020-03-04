package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * La clase Main solamente se encarga de inicializar la interfaz grafica
 */
public class Main extends Application {

    /**
     * El metodo start inicializa la interfaz grafica
     * @param primaryStage la pantalla en la que se dibuja la interfaz
     * @throws Exception en caso de algun error
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootL = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene login = new Scene(rootL);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");
        primaryStage.setScene(login);
        primaryStage.show();
    }

    /**
     * El metodo main solamente llama al metodo start
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
