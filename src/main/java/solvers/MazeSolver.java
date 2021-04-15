/**
 * Solves a maze while updating a Display.
 *
 * @author Christopher Medlin, Will DeBernardi
 */
package solvers;

import gui.Display;
import javafx.scene.paint.Color;
import maze.Direction;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;

public abstract class MazeSolver {
    private Display display;
    private Vertex lastVisited;
    private Direction dir;

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
            if (lastVisited != null) {
                this.display.updateSolver(v, lastVisited);
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

    public void initDirection(Vertex startVertex) {
        Direction entranceDirection = null;

        Vertex current = startVertex;
        if(current.getEdge(Direction.LEFT).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.LEFT).getState() == MazeState.EXIT) {
            entranceDirection = Direction.LEFT;
        } else if (current.getEdge(Direction.UP).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.UP).getState() == MazeState.EXIT) {
            entranceDirection = Direction.UP;
        } else if (current.getEdge(Direction.RIGHT).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.RIGHT).getState() == MazeState.EXIT) {
            entranceDirection = Direction.RIGHT;
        } else if (current.getEdge(Direction.DOWN).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.DOWN).getState() == MazeState.EXIT) {
            entranceDirection = Direction.DOWN;
        }
        this.dir = entranceDirection.reverse();
    }

    public Direction getCurrentDirection() {
        return this.dir;
    }

    /**
     * Turns the solver in the given direction.
     *
     * @param d the direction
     */
    public void turn(Direction d) {
       switch (d) {
           case UP:
               break;
           case RIGHT:
               this.dir = this.dir.turnRight();
               break;
           case LEFT:
               this.dir = this.dir.turnLeft();
               break;
           case DOWN:
               this.dir = this.dir.reverse();
               break;
       }
    }
}
