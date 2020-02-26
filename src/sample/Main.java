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

        Scene login = new Scene(rootL);
        Scene mainscreen = new Scene(rootMS);

        primaryStage.setResizable(false);

        primaryStage.setTitle("Login");

        primaryStage.setScene(login);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
