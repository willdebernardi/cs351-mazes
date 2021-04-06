/**
 * Main controller for the GUI.
 *
 * FXML is probably unnecessary, but perhaps additional features will be added
 * in the future.
 *
 * @author Christopher Medlin, Will DeBernardi
 */
package gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Direction;
import maze.Vertex;

public class Controller implements Display {
    @FXML
    Canvas canvas;

    @Override
    public void cellChanged(Vertex... cells) {
        for (Vertex v : cells) {
            drawCell(v);
        }
    }

    @Override
    public void updateSolver(String id, Vertex v) {
        // draw the solver's location
    }

    // draws the given cell. the center of the cell is 8x8, and each of the 4
    // borders of the cell are 8x2, making each cell 10x10
    private void drawCell(Vertex v) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        // black background
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // upper left corner of cell
        int x = 10*v.getX(), y = 10*v.getY();
        gc.setFill(Color.WHITE);
        // fill the center square (the empty part inside of the cell)
        gc.fillRect(x + 2, y + 2, 8, 8);

        // draw the passageways accordingly (note that we are drawing on a black
        // background)
        if (!v.getEdge(Direction.DOWN).isWall()) {
            gc.fillRect(x+2, y+8, 8, 2);
        }
        if (!v.getEdge(Direction.RIGHT).isWall()) {
            gc.fillRect(x+8, y+2, 2, 8);
        }
        if (!v.getEdge(Direction.UP).isWall()) {
            gc.fillRect(x+2, y, 2, 8);
        }
        if (!v.getEdge(Direction.LEFT).isWall()) {
            gc.fillRect(x, y+2, 8, 2);
        }
    }
}
