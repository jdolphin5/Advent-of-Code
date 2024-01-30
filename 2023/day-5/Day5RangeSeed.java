public class Day5RangeSeed {
    long start;
    long len;
    int rangeNumber;

    public Day5RangeSeed(long start, long len, int rangeNumber) {
        this.start = start;
        this.len = len;
        this.rangeNumber = rangeNumber;
    }

    public String toString() {
        return "start: " + this.start + " : len: " + this.len + " rangeNumber: " + this.rangeNumber;
    }
}
