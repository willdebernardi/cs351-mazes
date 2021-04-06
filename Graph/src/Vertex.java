import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Vertex {
    private int x, y;
    private HashMap<Direction, Edge> edges;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        edges = new HashMap<>();
    }

    public void addEdge(Vertex endVertex, MazeState state, Direction direction) {
        edges.put(direction, new Edge(this, endVertex, state));
    }

    public Collection<Edge> getEdges() {
        return edges.values();
    }

}
