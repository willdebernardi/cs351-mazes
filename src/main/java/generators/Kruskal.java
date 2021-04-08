package generators;

import gui.Display;
import maze.Edge;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;
import utility.Partition;

import java.util.*;
import java.util.function.Consumer;

public class Kruskal extends MazeGenerator {
    List<Edge> edgeSet;

    public Kruskal(Display d, Consumer<Maze> onGenerationComplete) {
        super(d, onGenerationComplete);
        edgeSet = new ArrayList<>();
    }

    @Override
    public Maze makeMaze(int size) {
        Maze m = new Maze(size);
        Partition<Vertex> partition = new Partition<>(m.getCells());

        for(Vertex cell : m.getCells()) {
            for(Edge edge : cell.getEdges()) {
                if(edge.getState() == MazeState.WALL) {
                    edgeSet.add(edge);
                }
            }
        }

        Collections.shuffle(edgeSet);

        for(Edge e : edgeSet) {
            if(!partition.inSameSet(e.getStart(), e.getEnd())) {
                edgeSet.remove(e);
                this.tearDown(e);
                partition.join(e.getStart(), e.getEnd());
            }
        }

        return m;
    }
}
