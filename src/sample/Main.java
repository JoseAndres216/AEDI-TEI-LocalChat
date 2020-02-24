package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login");
        Scene login = new Scene(root, 380, 200);
        primaryStage.setScene(login);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.out.println("Main.main");
        launch(args);
    }
}
