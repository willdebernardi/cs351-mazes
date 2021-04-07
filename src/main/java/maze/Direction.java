package maze;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction reverse() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        // shouldn't happen
        return null;
    }
}
