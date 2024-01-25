public class Day19RangePart {
    long minX;
    long maxX;
    long minM;
    long maxM;
    long minA;
    long maxA;
    long minS;
    long maxS;

    public Day19RangePart (long minX, long maxX, long minM, long maxM, long minA, long maxA, long minS, long maxS) {
        this.minX = minX;
        this.maxX = maxX;
        this.minM = minM;
        this.maxM = maxM;
        this.minA = minA;
        this.maxA = maxA;
        this.minS = minS;
        this.maxS = maxS;
    }

    public String toString() {
        return "X: " + minX + " - " + maxX + " , " + "M: " + minM + " - " + maxM + " , " + "A: " + minA + " - " + maxA + " , " + "S: " + minS + " - " + maxS;
    }
}
