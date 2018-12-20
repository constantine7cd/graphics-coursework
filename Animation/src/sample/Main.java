package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root2 = FXMLLoader.load(getClass().getResource("sample.fxml"));

        window  = primaryStage;
        window.setTitle("Анимация алфавита дактиля");


        Scene scene = new Scene(root2, 1000, 700);

        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
