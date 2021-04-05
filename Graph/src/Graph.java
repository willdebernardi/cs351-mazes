import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    public void addVertex(MazeState state) {
        adjVertices.putIfAbsent(new Vertex(state), new ArrayList<>());
    }

    public void addEdge(MazeState stateOne, MazeState stateTwo) {
        Vertex vertexOne = new Vertex(stateOne);
        Vertex vertexTwo = new Vertex(stateTwo);
        adjVertices.get(vertexOne).add(vertexTwo);
        adjVertices.get(vertexTwo).add(vertexOne);
    }

    public void removeEdge(MazeState stateOne, MazeState stateTwo) {
        Vertex vertexOne = new Vertex(stateOne);
        Vertex vertexTwo = new Vertex(stateTwo);
        List<Vertex> listOne = adjVertices.get(vertexOne);
        List<Vertex> listTwo = adjVertices.get(vertexTwo);
        if(listOne != null) {
            listOne.remove(vertexTwo);
        }
        if(listTwo != null) {
            listTwo.remove(vertexOne);
        }
    }

    List<Vertex> getAdjVertcies(MazeState state) {
        return adjVertices.get(new Vertex(state));
    }
}
