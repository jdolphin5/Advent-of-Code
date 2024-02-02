public class Day24Line {
    long px;
    long py;
    long pz;
    long vx;
    long vy;
    long vz;

    public Day24Line(long px, long py, long pz, long vx, long vy, long vz) {
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public String toString() {
        return "position: " + this.px + " " + this.py + " " + this.pz + " - velocity: " + this.vx + " " + this.vy + " " + this.vz;
    }
    
}
