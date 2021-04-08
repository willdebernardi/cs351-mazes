package maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Edge getEdge(Direction d) {
        return edges.get(d);
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
     * Returns every vertex attached to this one via an edge that is not a wall
     * or boundary
     */
    public Collection<Vertex> getReachableAdjacents() {
        Collection<Edge> edges = this.getEdges()
                                     .stream()
                                     .filter(e -> !e.isWall())
                                     .collect(Collectors.toList());
        HashSet<Vertex> vertices = new HashSet<>();

        for (Edge e : edges) {
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
