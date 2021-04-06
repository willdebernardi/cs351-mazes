import java.util.ArrayList;

public class Graph {
    private  ArrayList<Vertex> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Vertex v1, Vertex v2, MazeState state, Direction direction) {
        v1.addEdge(v2, state, direction);
        v2.addEdge(v1, state, direction);
    }

}
