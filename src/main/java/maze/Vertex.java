package maze;

import java.util.*;
import java.util.function.Predicate;
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
     * Returns every vertex that has not been visited, according to a set of
     * visited vertices.
     */
    public List<Vertex> getUnvisitedAdjacents(Set<Vertex> visited) {
        Predicate<Vertex> unvisitedFilter = v -> !visited.contains(v);
        List<Vertex> unvisited = getReachableAdjacents()
                                 .stream()
                                 .filter(unvisitedFilter)
                                 .collect(Collectors.toList());
        return unvisited;
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

    /**
     * Returns whether one of the edges of this vertex has an edge with the
     * given MazeState. This can be used to check if a vertex counts as an
     * entrance/exit.
     */
    public boolean hasEdge(MazeState state) {
        for (Edge e : getEdges()) {
            if (e.getState() == state) return true;
        }
        return false;
    }
}
