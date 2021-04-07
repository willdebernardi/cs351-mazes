import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maze.Settings;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Will handle using .getParameters() to take file from command line arg
        Settings settings = new Settings(new File("src/main/java/ex.txt"));
        int windowSize = settings.getWindowSize();

        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("main.fxml")
        );
        // TODO width and height must be determined by settings!
        Scene s = new Scene(loader.load(), windowSize, windowSize);

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
