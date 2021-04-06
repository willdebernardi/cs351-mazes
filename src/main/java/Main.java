import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("main.fxml")
        );
        // TODO width and height must be determined by settings!
        Scene s = new Scene(loader.load(), 300, 300);

        primaryStage.setScene(s);
    }
}
