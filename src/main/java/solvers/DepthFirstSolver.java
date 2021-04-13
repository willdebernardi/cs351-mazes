package solvers;

import gui.Display;
import maze.Direction;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DepthFirstSolver extends MazeSolver {
    Stack<Vertex> stack;
    Set<Vertex> visited;

    /**
     * Create a new MazeSolver
     *
     * @param d the display to be updated during solving
     */
    public DepthFirstSolver(Display d) {
        super(d);
        this.stack = new Stack<>();
        this.visited = new HashSet<>();
    }

    /**
     * Create a new MazeSolver
     */
    public DepthFirstSolver() {
        this(null);
    }

    @Override
    public void solveFrom(Vertex start, Vertex exit) {
        stack.push(start);
        this.visited.add(start);
        Vertex current = null;

        Predicate<Vertex> unvisitedFilter = v -> !visited.contains(v);

        while (!stack.isEmpty()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            current = stack.pop();
            this.visit(current);
            if (current == exit) {
                return;
            }

            // filter into unvisited cells
            List<Vertex> unvisited = current.getReachableAdjacents()
                                            .stream()
                                            .filter(unvisitedFilter)
                                            .collect(Collectors.toList());

            if (!unvisited.isEmpty()) {
                stack.push(current);
                Vertex next = unvisited.get(0);
                // remove wall between the cells
                current = next;
                this.visited.add(current);
                this.stack.push(current);
            }
        }
    }
}
