package generators;

import gui.Display;
import maze.Edge;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;

import java.util.*;
import java.util.function.Consumer;

public class Kruskal extends MazeGenerator {
    List<Edge> edgeSet;

    public Kruskal(Display d, Consumer<Maze> onGenerationComplete) {
        super(d, onGenerationComplete);
        edgeSet = new LinkedList<>();
    }

    @Override
    public Maze makeMaze(int size) {
        Maze m = new Maze(size);

        for(Vertex cell : m.getCells()) {
            for(Edge edge : cell.getEdges()) {
                if(edge.getState() == MazeState.WALL) {
                    edgeSet.add(edge);
                }
            }
            Set<Vertex> singleCellSet = new HashSet<>();
            cell.setSelfSet(singleCellSet);
        }

        Collections.shuffle(edgeSet);
        // Really not a fan of this
        for(Edge e : edgeSet) {
            Set<Vertex> setOne = e.getStart().getSelfSet();
            Set<Vertex> setTwo = e.getEnd().getSelfSet();
            if(Collections.disjoint(setOne, setTwo)) {
                this.tearDown(e);
                // Create new set and merge previous two
                Set<Vertex> newSet = new HashSet<>();
                newSet.addAll(setOne);
                newSet.addAll(setTwo);
                e.getStart().setSelfSet(newSet);
                e.getEnd().setSelfSet(newSet);
            }
        }

        return m;
    }
}
