package maze;

import generators.DepthFirstGenerator;
import generators.Kruskal;
import generators.MazeGenerator;
import solvers.DepthFirstSolver;
import solvers.MazeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Reads provided file and sets corresponding variables
 *
 * Assumes that the provided file is correctly formatted
 *
 * @author Will DeBernardi, Christopher Medlin
 */
public class Settings {
    private int windowSize;
    private int cellSize;
    private String generationAlgo;
    private String solverAlgo;

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

    public MazeGenerator getGenerationAlgo() {
        int size = windowSize/cellSize;
        if (this.generationAlgo.equals("dfs")) {
            return new DepthFirstGenerator();
        } else if (this.generationAlgo.equals("kruskal")) {
            return new Kruskal();
        }

        throw new IllegalStateException("Invalid generator setting.");
    }

    public MazeSolver getSolverAlgo() {
        if (this.solverAlgo.equals("df")) {
            return new DepthFirstSolver();
        }

        throw new IllegalStateException("Invalid solver setting.");
    }
}
