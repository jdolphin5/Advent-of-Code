public class Day19Condition {
    char partType;
    boolean mustBeGreaterThanValue;
    int value;
    String ret;
    boolean auto;

    public Day19Condition(char partType, boolean mustBeGreaterThanValue, int value, String ret, boolean auto) {
        this.partType = partType;
        this.mustBeGreaterThanValue = mustBeGreaterThanValue;
        this.value = value;
        this.ret = ret;
        this.auto = auto;
    }
}
