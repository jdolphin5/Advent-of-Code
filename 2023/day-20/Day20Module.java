import java.util.List;

public class Day20Module {
    String name;
    char type;
    boolean state;
    List<String> inputList;
    List<String> destinationList;
    boolean lastPulse;

    public Day20Module(String name, char type, boolean state, List<String> inputList, List<String> destinationList) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.lastPulse = false;
        this.inputList = inputList;
        this.destinationList = destinationList;
    }

    public String toString() {
        return "name: " + this.name + " type: " + this.type + " state: " + this.state + " lastPulse: " + this.lastPulse + "\n inputList: " + this.inputList.toString() + " \n destinationList: " + this.destinationList.toString();
    }
}
