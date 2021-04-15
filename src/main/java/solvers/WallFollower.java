package solvers;

import gui.Display;
import maze.Direction;
import maze.Edge;
import maze.MazeState;
import maze.Vertex;

import java.util.List;

public class WallFollower extends MazeSolver{

    public WallFollower(Display d) {
        super(d);
    }

    public WallFollower() {
        this(null);
    }


    @Override
    public void solveFrom(Vertex start, Vertex exit) {
        Vertex current = start;
        visit(current);
        Direction entranceDirection = null;
        if(current.getEdge(Direction.LEFT).getState() == MazeState.ENTRANCE) {
            entranceDirection = Direction.LEFT;
        } else if (current.getEdge(Direction.UP).getState() == MazeState.ENTRANCE) {
            entranceDirection = Direction.UP;
        } else if (current.getEdge(Direction.RIGHT).getState() == MazeState.ENTRANCE) {
            entranceDirection = Direction.RIGHT;
        } else if (current.getEdge(Direction.DOWN).getState() == MazeState.ENTRANCE) {
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
    }
}
