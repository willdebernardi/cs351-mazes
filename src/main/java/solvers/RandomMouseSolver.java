package solvers;

import gui.Display;
import maze.MazeState;
import maze.Vertex;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;

public class RandomMouseSolver extends MazeSolver {
    ExecutorService exec;
    Set<Vertex> visited;

    /**
     * Creates a new solver using the random mouse algorithm.
     *
     * @param d the display to be updated
     * @param exec the executor to use when creating a new RandomMouseSolver at
     *             an intersection.
     */
    public RandomMouseSolver(Display d, ExecutorService exec) {
        super(d);
        this.exec = exec;
        visited = new HashSet<>();
    }

    public RandomMouseSolver() {
        this(null, Executors.newFixedThreadPool(1000));
    }

    @Override
    public void solveFrom(Vertex start) {
        Vertex current = start;
        boolean deadEnd = false;

        while (!current.hasEdge(MazeState.EXIT)) {
            visit(current);
            List<Vertex> unvisited = current.getUnvisitedAdjacents(visited);
            Collections.shuffle(unvisited);
            // make the first element the next current cell for this solver;
            // make new solvers for the remaining elements.
            if (unvisited.size() > 0) {
                current = unvisited.get(0);
                for (int i = 1; i < unvisited.size(); i++) {
                    newMouse(unvisited.get(i));
                }
            } else {
                // dead end
                try {
                    deadEnd = true;
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        if (!deadEnd) {
            visit(current);
        }

        // exit has been found, shut down all threads!
        exec.shutdownNow();
    }

    private void newMouse(Vertex v) {
        Runnable r = () -> {
            RandomMouseSolver solver = new RandomMouseSolver(
                    getDisplay(), exec
            );
            solver.solveFrom(v);
        };
        if (!exec.isShutdown()) {
            exec.execute(r);
        }
    }
}
