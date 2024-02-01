public class Day24Line {
    double px;
    double py;
    double pz;
    double vx;
    double vy;
    double vz;

    public Day24Line(double px, double py, double pz, double vx, double vy, double vz) {
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
