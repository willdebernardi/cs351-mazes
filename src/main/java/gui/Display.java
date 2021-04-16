package gui;

import javafx.scene.paint.Color;
import maze.Vertex;

/**
 * Interface for drawing to the GUI during maze generation and solving.
 *
 * It is an unenforced rule that methods overridden from this interface must be
 * thread-safe, as maze generation and solving occur on (potentially several)
 * different threads.
 *
 * @author Christopher Medlin, Will DeBernardi
 * @date 06 Apr 2021
 */
public abstract class Display {

    /**
     * This is called during maze generation when one or more cells have been
     * changed (for example, when a wall has been broken down between two cells)
     *
     * @param cells the cells that have been changed, whose newly updated form
     *              will be drawn on the GUI, using the coordinates stored in
     *              the Vertex object
     */
    public void cellsChanged(Vertex... cells) {
        cellsChanged(Color.WHITE, cells);
    }

    /**
     * This is called during maze generation when one or more cells have been
     * changed (for example, when a wall has been broken down between two cells)
     *
     * @param color the color to paint the cells
     * @param cells the cells that have been changed, whose newly updated form
     *              will be drawn on the GUI, using the coordinates stored in
     *              the Vertex object
     */
    public abstract void cellsChanged(Color color, Vertex... cells);

    /**
     * Updates the location of the solver on the GUI
     * @param visited - cell currently being visited
     * @param lastVisited - cell last visited
     */
    public abstract void updateSolver(Vertex visited, Vertex lastVisited);
}
