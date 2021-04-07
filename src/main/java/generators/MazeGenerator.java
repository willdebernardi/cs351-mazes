/**
 * Generates a maze.
 *
 * @author Christopher Medlin, Will DeBernardi
 */
package generators;

import gui.Display;
import maze.Edge;
import maze.Maze;
import maze.MazeState;

import java.util.function.Consumer;

public abstract class MazeGenerator {
    private Consumer<Maze> onGenerationComplete;
    private Display display;

    /**
     * Creates a new MazeGenerator
     *
     * @param d the display to update during maze generation
     * @param onGenerationComplete calls with the maze upon the maze finishing
     *                             generation
     */
    public MazeGenerator(Display d, Consumer<Maze> onGenerationComplete) {
        this.display = d;
        this.onGenerationComplete = onGenerationComplete;
    }

    /**
     * Creates a maze according to this generator's algorithm.
     *
     * @return the newly created maze
     */
    public abstract Maze makeMaze(int size);

    /**
     * Generates the maze, and calls onGenerationComplete once finished.
     */
    public void generate(int size) {
        this.onGenerationComplete.accept(makeMaze(size));
    }

    /**
     * Removes the wall that is represented by e (by setting its maze state to
     * EMPTY), and notify the Display of the cells changed.
     *
     * @param e the wall to be teared down
     */
    public void tearDown(Edge e) {
        e.setState(MazeState.EMPTY);
        display.cellsChanged(e.getStart(), e.getEnd());
    }
}
