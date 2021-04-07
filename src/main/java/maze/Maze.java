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

        for(int i = 0; i <= n; i++) {
            for(int j = 0; j <= n; j++) {
                Vertex vertex = new Vertex(i,j);
                cells.add(vertex);

                if(i == 0) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.UP);
                } else {
                    Vertex upVertex = cells.get(cells.size() - n + j);
                    addEdge(vertex, upVertex, MazeState.WALL, Direction.UP);
                }

                if(i == n) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.DOWN);
                }

                if(j == 0) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.LEFT);
                } else {
                    Vertex leftVertex = cells.get(cells.size() - 1);
                    addEdge(vertex, leftVertex, MazeState.WALL, Direction.LEFT);
                }

                if(j == n) {
                    vertex.addEdge(null, MazeState.BOUNDARY, Direction.RIGHT);
                }

                // make upper left corner the exit of the maze
                if (i == 0 && j == 0) {
                    vertex.getEdge(Direction.UP).setState(MazeState.EXIT);
                    this.exit = vertex;
                } else if (i == n && j == n) {
                    vertex.getEdge(Direction.DOWN).setState(MazeState.ENTRANCE);
                    this.entrance = vertex;
                }
            }
        }
    }

    public void addEdge(Vertex v1, Vertex v2, MazeState state, Direction direction) {
        v1.addEdge(v2, state, direction);
        v2.addEdge(v1, state, direction);
    }

    /**
     * Returns the vertex corresponding to the entrance of this maze.
     *
     * @return the entrance
     */
    public Vertex getEntrance() {
        return this.entrance;
    }
}
