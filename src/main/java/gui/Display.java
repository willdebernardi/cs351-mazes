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
package gui;

import javafx.scene.paint.Color;
import maze.Vertex;

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
     * Called by a maze solver when its location in the maze has changed.
     *
     * @param id a unique id corresponding to the maze solver. used in solving
     *           algorithms that are multithreaded, such as random mouse, such
     *           that each thread can be uniquely identified
     * @param v the newly visited cell
     */
    public abstract void updateSolver(String id, Vertex v);
}
