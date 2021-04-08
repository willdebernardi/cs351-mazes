package maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private ArrayList<Vertex> cells;
    private Vertex exit;
    private Vertex entrance;

    /**
     * Initializes a new empty maze, in which each cell is surrounded by walls.
     *
     * @param n the size of the maze (n x n)
     */
    public Maze(int n) {
        cells = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Vertex vertex = new Vertex(j,i);
                cells.add(vertex);

                if(i == 0) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.UP);
                } else {
                    Vertex upVertex = cells.get(cells.size() - n - 1);
                    connect(vertex, upVertex, Direction.UP);
                }

                if(i == n-1) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.DOWN);
                }

                if(j == 0) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.LEFT);
                } else {
                    Vertex leftVertex = cells.get(cells.size() - 2);
                    connect(vertex, leftVertex, Direction.LEFT);
                }

                if(j == n-1) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.RIGHT);
                }

                // make upper left corner the exit of the maze
                if (i == 0 && j == 0) {
                    vertex.getEdge(Direction.UP).setState(MazeState.EXIT);
                    this.exit = vertex;
                } else if (i == n-1 && j == n-1) {
                    vertex.getEdge(Direction.DOWN).setState(MazeState.ENTRANCE);
                    this.entrance = vertex;
                }
            }
        }
    }

    // connects two vertices with a wall
    private void connect(Vertex v1, Vertex v2, Direction d) {
        v1.addEdge(v2, MazeState.WALL, d);
        v2.addEdge(v1, MazeState.WALL, d.reverse());
    }

    /**
     * Returns the vertex corresponding to the entrance of this maze.
     *
     * @return the entrance
     */
    public Vertex getEntrance() {
        return this.entrance;
    }

    public ArrayList<Vertex> getCells() {
        return cells;
    }
}
