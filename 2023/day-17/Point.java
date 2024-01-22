public class Point {
    int index;
    int i;
    int j;
    int dir;
    int cost;
    int straightSteps;

    public Point(int i, int j, int dir, int straightSteps) {
        this.index = (i * 1000) + j + ((dir+1) * 10000000);
        this.i = i;
        this.j = j;
        this.dir = dir;
        this.straightSteps = straightSteps;
    }

    public Point(int i, int j, int dir, int cost, int straightSteps) {
        this.index = (i * 1000) + j + ((dir+1) * 10000000);
        this.i = i;
        this.j = j;
        this.dir = dir;
        this.cost = cost;
        this.straightSteps = straightSteps;
    }
}
