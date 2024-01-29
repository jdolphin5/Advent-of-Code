public class Day23Edge {
    int i;
    int j;
    int weight;
    int index;

    public Day23Edge(int i, int j) {
        this.i = i;
        this.j = j;
        this.weight = 1;
    }

    public Day23Edge(int i, int j, int weight) {
        this.i = i;
        this.j = j;
        this.weight = weight;
    }

    public String toString() {
        return "edge - i: " + this.i + " , j: " + this.j + " weight: " + this.weight;
    }
}
