public class Coordinate {
    int x;
    int y;
    int stepCount;
    char step;
    Coordinate last;

    public Coordinate(int x, int y, char step, Coordinate last) {
        this.x = x;
        this.y = y;
        this.step = step;
        this.last = last;
    }
}
