package generators;

import gui.Display;
import maze.Edge;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;
import utility.Partition;

import java.util.*;
import java.util.function.Consumer;

/**
 * An implementation of the Kruskal maze generation algorithm
 * @author Will DeBernardi
 */
public class Kruskal extends MazeGenerator {
    /**
     * Creates a new maze generator
     * @param d - display to be updated
     * @param onGenerationComplete - called when generation is complete
     */
    public Kruskal(Display d, Consumer<Maze> onGenerationComplete) {
        super(d, onGenerationComplete);
    }

    public Kruskal() {
        this(null, null);
    }

    /**
     * Generates the maze based upon the constraints of the algorithm
     * @param size - size of the maze to generate
     * @return - the generated maze
     */
    @Override
    public Maze makeMaze(int size) {
        Set<Edge> edgeSet = new HashSet<>();
        Maze m = new Maze(size);
        Partition<Vertex> partition = new Partition<>(m.getCells());

        for(Vertex cell : m.getCells()) {
            for(Edge edge : cell.getEdges()) {
                if(edge.getState() == MazeState.WALL) {
                    edgeSet.add(edge);
                }
            }
        }

        ArrayList<Edge> walls = new ArrayList<>(edgeSet);
        Collections.shuffle(walls);

        for(Edge e : walls) {
            if(partition.inSameSet(e.getStart(), e.getEnd())) {
                partition.join(e.getStart(), e.getEnd());
                this.tearDown(e);
            }
        }

        return m;
    }
}
