package maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private ArrayList<Vertex> cells;

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
            }
        }
    }

    public void addEdge(Vertex v1, Vertex v2, MazeState state, Direction direction) {
        v1.addEdge(v2, state, direction);
        v2.addEdge(v1, state, direction);
    }
}
