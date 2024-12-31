package Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MultUserAccount extends Application {
    @Override
    public void start(Stage stage) throws Exception {
/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/login.fxml"));
        Parent root = loader.load();
        */
        Parent root = FXMLLoader.load(getClass().getResource("/UI/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
