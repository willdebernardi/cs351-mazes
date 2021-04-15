package solvers;

import gui.Display;
import maze.Direction;
import maze.Edge;
import maze.MazeState;
import maze.Vertex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WallFollower extends MazeSolver{
    ExecutorService exec;
    boolean multithreaded;
    static boolean firstExec = true;

    public WallFollower(Display d, ExecutorService exec) {
        super(d);
        this.exec = exec;
        this.multithreaded = true;
    }

    public WallFollower(boolean multithreaded) {
        this(null, Executors.newFixedThreadPool(1000));
        this.multithreaded = multithreaded;
    }


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
