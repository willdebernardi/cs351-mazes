import generators.MazeGenerator;
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maze.Settings;
import solvers.MazeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main extends Application {
    private static Settings settings;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please give path to configuration file.");
        } else {
            try {
                settings = new Settings(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(1);
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = new Settings(new File("src/main/java/ex.txt"));
        int windowSize = settings.getWindowSize();
        int cellSize = settings.getCellSize();

        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("main.fxml")
        );

        Scene s = new Scene(loader.load(), windowSize, windowSize);
        Controller controller = loader.getController();
        controller.setSettings(settings);

        primaryStage.setScene(s);
        primaryStage.show();

        MazeGenerator gen = settings.getGenerationAlgo();
        MazeSolver solver = settings.getSolverAlgo();
        gen.setDisplay(controller);
        solver.setDisplay(controller);

        gen.setOnGenerationComplete(maze -> {
            solver.solve(maze);
        });

        new Thread(() -> gen.generate(windowSize/cellSize)).start();
    }
}
