import java.util.*;

public class Day22Brick {
    int startX;
    int startY;
    int startZ;
    int endX;
    int endY;
    int endZ;
    int brickNumber;
    Set<Day22Brick> above;
    Set<Day22Brick> below;
    boolean counted;

    public Day22Brick(int startX, int startY, int startZ, int endX, int endY, int endZ, int brickNumber) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.brickNumber = brickNumber;
        this.above = new HashSet<>();
        this.below = new HashSet<>();
        this.counted = false;
    }

    public String toString() {
        return "brickNumber: " + this.brickNumber + " startX: " + this.startX + " endX: " + this.endX + " startY: " + this.startY + " endY: " + this.endY + " startZ: " + this.startZ + " endZ: " + this.endZ;
    }
}
