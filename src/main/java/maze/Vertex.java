package maze;

import java.util.*;

public class Vertex {
    private int x, y;
    private HashMap<Direction, Edge> edges;
    private Set<Vertex> selfSet;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Edge getEdge(Direction d) {
        return edges.get(d);
    }

    public Set<Vertex> getSelfSet() {
        return selfSet;
    }

    public void setSelfSet(Set<Vertex> selfSet) {
        this.selfSet = selfSet;
    }

    /**
     * Returns every vertex attached to this one via edges.
     */
    public Collection<Vertex> getAdjacents() {
        HashSet<Vertex> vertices = new HashSet<>();

        for (Edge e : getEdges()) {
            if (e.getStart() == this && e.getEnd() != null) {
                vertices.add(e.getEnd());
            } else if (e.getStart() != null) {
                vertices.add(e.getStart());
            }
        }

        return vertices;
    }

    /**
     * Returns the edge connecting this vertex with the given vertex (if it
     * exists). If there is no such edge, return null
     */
    public Edge connectingEdge(Vertex v) {
        for (Edge e : getEdges()) {
            if (e.getStart() == v || e.getEnd() == v) {
                return e;
            }
        }
        return null;
    }
}
