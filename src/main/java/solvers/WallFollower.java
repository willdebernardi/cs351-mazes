package solvers;

import gui.Display;
import maze.Direction;
import maze.Edge;
import maze.MazeState;
import maze.Vertex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An implementation of the Wall Follower solving algorithm,
 * can be ran multi-thread, or not.
 * @author Will DeBernardi, Christopher Medlin
 */
public class WallFollower extends MazeSolver{
    ExecutorService exec;
    boolean multithreaded;
    static boolean firstExec = true;

    /**
     * Constructor to create a new implementation of the Wall Follower algorithm
     * @param d    - Display to be updated
     * @param exec - the executor used when creating an additional thread
     */
    public WallFollower(Display d, ExecutorService exec) {
        super(d);
        this.exec = exec;
        this.multithreaded = true;
    }

    /**
     * Constructor for multi-threaded version of the algorithm
     * Contains a call to the above constructor
     * @param multithreaded - Boolean value for multithreading or not
     */
    public WallFollower(boolean multithreaded) {
        this(null, Executors.newFixedThreadPool(1000));
        this.multithreaded = multithreaded;
    }

    /**
     * Solves from a given vertex to the exit of the maze.
     * Or, in the case of when multithreaded, to the entrance of the maze.
     * @param start - Vertex from which the algorithm is began
     * @param exit  - Exit vertex of the maze
     */
    @Override
    public void solveFrom(Vertex start, Vertex exit) {
        if(multithreaded && firstExec) {
            newThread(exit, start);
            firstExec = false;
        }
        Vertex current = start;
        visit(current);
        Direction entranceDirection = null;
        if(current.getEdge(Direction.LEFT).getState() == MazeState.ENTRANCE ||
        current.getEdge(Direction.LEFT).getState() == MazeState.EXIT) {
            entranceDirection = Direction.LEFT;
        } else if (current.getEdge(Direction.UP).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.UP).getState() == MazeState.EXIT) {
            entranceDirection = Direction.UP;
        } else if (current.getEdge(Direction.RIGHT).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.RIGHT).getState() == MazeState.EXIT) {
            entranceDirection = Direction.RIGHT;
        } else if (current.getEdge(Direction.DOWN).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.DOWN).getState() == MazeState.EXIT) {
            entranceDirection = Direction.DOWN;
        }
        Direction direction = entranceDirection.reverse();

        while(current != exit) {
            if(current.getEdge(direction.turnRight()).getState() == MazeState.EMPTY) {
                direction = direction.turnRight();
            } else if(current.getEdge(direction).getState() == MazeState.EMPTY) {
            } else if (current.getEdge(direction.turnLeft()).getState() == MazeState.EMPTY) {
                direction = direction.turnLeft();
            } else {
                direction = direction.reverse();
            }

            Edge connectionEdge = current.getEdge(direction);
            Vertex next = current.getVertexFromEdge(connectionEdge);
            visit(next);
            current = next;
        }

        exec.shutdownNow();
    }

    /**
     * Creates a new instance of the algorithm on a separate thread
     * @param v     - Vertex to begin upon
     * @param exit  - Vertex to end on
     */
    public void newThread(Vertex v, Vertex exit) {
        Runnable r = () -> {
            WallFollower follower = new WallFollower(getDisplay(), exec);
            follower.solveFrom(v, exit);
        };
        if(!exec.isShutdown()) {
            exec.execute(r);
        }
    }
}
