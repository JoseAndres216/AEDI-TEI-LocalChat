package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootL = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent rootMS = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Login");
        Scene login = new Scene(rootL);
        primaryStage.setScene(login);

        primaryStage.setTitle("LocalChat by Jose Andres Rodriguez");
        Scene mainscreen = new Scene(rootMS);
        primaryStage.setScene(mainscreen);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
