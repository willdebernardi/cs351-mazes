/**
 * Main controller for the GUI.
 *
 * FXML is probably unnecessary, but perhaps additional features will be added
 * in the future.
 *
 * @author Christopher Medlin, Will DeBernardi
 */
package gui;

import generators.DepthFirstGenerator;
import generators.Kruskal;
import generators.MazeGenerator;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import maze.Direction;
import maze.Maze;
import maze.Vertex;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Controller implements Display {
    @FXML
    Canvas canvas;

    // cells are placed into this blocking queue by another thread, and drawn
    // using the animation timer. the first element of each pair is the cell to
    // be drawn, followed by the color to draw it in.
    BlockingQueue<Pair<Vertex, Color>> cellDrawingQueue;

    public Controller() {
        cellDrawingQueue = new ArrayBlockingQueue<>(2);
    }

    /**
     * Called when the controller starts (FXML)
     */
    public void initialize() {
        Thread t = new Thread(() -> {
            Kruskal generator = new Kruskal(
                    this, (m) -> {System.out.println("Finished!");}
            );
            generator.generate(50);
        });
        t.start();

        // black background
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!cellDrawingQueue.isEmpty()) {
                    try {
                        Pair<Vertex, Color> cell = cellDrawingQueue.take();
                        drawCell(cell.getKey(), cell.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();
    }

    @Override
    public void cellsChanged(Vertex... cells) {
        for (Vertex v : cells) {
            try {
                Pair<Vertex, Color> p = new Pair<>(v, Color.WHITE);
                this.cellDrawingQueue.put(p);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateSolver(String id, Vertex v) {
        // draw the solver's location
    }

    // draws the given cell. the center of the cell is 8x8, and each of the 4
    // borders of the cell are 8x2, making each cell 10x10
    private void drawCell(Vertex v, Color c) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        // upper left corner of cell
        int x = 10*v.getX(), y = 10*v.getY();
        gc.setFill(c);
        // fill the center square (the empty part inside of the cell)
        gc.fillRect(x + 2, y + 2, 8, 8);

        // draw the passageways accordingly (note that we are drawing on a black
        // background)
        if (!v.getEdge(Direction.DOWN).isWall()) {
            gc.fillRect(x+2, y+10, 8, 2);
        }
        if (!v.getEdge(Direction.RIGHT).isWall()) {
            gc.fillRect(x+10, y+2, 2, 8);
        }
        if (!v.getEdge(Direction.UP).isWall()) {
            gc.fillRect(x+2, y, 8, 2);
        }
        if (!v.getEdge(Direction.LEFT).isWall()) {
            gc.fillRect(x, y+2, 2, 8);
        }
    }
}
