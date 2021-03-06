package maze;

import generators.*;
import solvers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    private int fps;
    private String generationAlgo;
    private String solverAlgo;

    public Settings(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        windowSize = s.nextInt();
        cellSize = s.nextInt();
        generationAlgo = s.next();
        solverAlgo = s.next();

        if (s.hasNextInt()) {
            this.fps = s.nextInt();
        } else {
            this.fps = 60;
        }
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getFPS() {
        return fps;
    }

    public MazeGenerator getGenerationAlgo() {
        int size = windowSize/cellSize;
        switch (this.generationAlgo) {
            case "dfs":
                return new DepthFirstGenerator();
            case "kruskal":
                return new Kruskal();
            case "aldous":
                return new Aldous();
            case "prim":
                return new PrimsGenerator();
        }

        throw new IllegalStateException("Invalid generator setting.");
    }

    public MazeSolver getSolverAlgo() {
        switch (this.solverAlgo) {
            case "df":
                return new DepthFirstSolver();
            case "mouse_thread":
                return new RandomMouseSolver(true);
            case "mouse":
                return new RandomMouseSolver(false);
            case "wall":
                return new WallFollower(false);
            case "wall_thread":
                return new WallFollower(true);
            case "pledge":
                return new PledgeSolver();
        }

        throw new IllegalStateException("Invalid solver setting.");
    }
}
