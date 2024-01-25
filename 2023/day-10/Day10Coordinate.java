public class Day10Coordinate {
    int x;
    int y;
    int stepCount;
    char step;
    Day10Coordinate last;

    public Day10Coordinate(int x, int y, char step, Day10Coordinate last) {
        this.x = x;
        this.y = y;
        this.step = step;
        this.last = last;
    }
}
