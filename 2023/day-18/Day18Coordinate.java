public class Day18Coordinate {
    int x;
    int y;
    char symbol;
    String colour;
    Day18Coordinate last;

    public Day18Coordinate(int x, int y, char symbol, String colour, Day18Coordinate last) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.colour = colour;
        this.last = last;
    }

    public String toString() {
        return "x: " + this.x + " y: " + this.y + " symbol: " + this.symbol;
    }
}
