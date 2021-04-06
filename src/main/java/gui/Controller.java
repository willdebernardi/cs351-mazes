package gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import maze.Vertex;

public class Controller implements Display {
    @FXML
    Canvas canvas;

    @Override
    public void cellChanged(Vertex... cells) {
        // draw the cells
    }

    @Override
    public void updateSolver(String id, Vertex v) {
        // draw the solver's location
    }
}
