/**
 * Solves a maze while updating a Display.
 *
 * @author Christopher Medlin, Will DeBernardi
 */
package solvers;

import gui.Display;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.Vertex;

public abstract class MazeSolver {
    private Display display;
    private Vertex lastVisited;

    /**
     * Create a new MazeSolver
     *
     * @param d the display to be updated during solving
     */
    public MazeSolver(Display d) {
        this.display = d;
    }

    /**
     * Create a new MazeSolver
     */
    public MazeSolver() {
        this.display = null;
    }

    /**
     * Sends to the Display that the given cell (represented by the vertex) has
     * been visited.
     *
     * @param v the cell
     */
    public void visit(Vertex v) {
        if (this.display != null) {
            this.display.updateSolver("", v);
            // paint the last visited cell back to white
            if (lastVisited != null) {
                this.display.cellsChanged(Color.LIGHTGREEN, lastVisited);
            }
            this.lastVisited = v;
        }
    }

    /**
     * Solves the maze.
     *
     * @param m the maze to be solved.
     */
    public void solve(Maze m) {
        // draw exit.
        display.cellsChanged(Color.RED, m.getExit());
        this.solveFrom(m.getEntrance(), m.getExit());
    }

    /**
     * Solves from the given vertex as a starting point
     */
    public abstract void solveFrom(Vertex start, Vertex exit);

    /**
     * Sets the display to be updated during solving.
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * Returns the display.
     */
    protected Display getDisplay() {
        return this.display;
    }
}
