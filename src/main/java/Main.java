import generators.MazeGenerator;
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maze.Settings;
import solvers.MazeSolver;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Will handle using .getParameters() to take file from command line arg
        Settings settings = new Settings(new File("src/main/java/ex.txt"));
        int windowSize = settings.getWindowSize();
        int cellSize = settings.getCellSize();

        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("main.fxml")
        );
        // TODO width and height must be determined by settings!
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

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(() -> gen.generate(windowSize/cellSize));
    }
}
