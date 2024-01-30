public class Day5Range {
    long dest;
    long source;
    long len;

    public Day5Range (long dest, long source, long len) {
        this.dest = dest;
        this.source = source;
        this.len = len;
    }

    public String toString() {
        return this.dest + " " + this.source + " " + this.len;
    }
}
