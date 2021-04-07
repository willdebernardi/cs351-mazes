package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Settings {
    private  static int windowSize;
    private static int cellSize;
    private static String generationAlgo;
    private static String solverAlgo;

    public Settings(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        windowSize = s.nextInt();
        cellSize = s.nextInt();
        generationAlgo = s.next();
        solverAlgo = s.next();
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public String getGenerationAlgo() {
        return generationAlgo;
    }

    public String getSolverAlgo() {
        return solverAlgo;
    }
}
