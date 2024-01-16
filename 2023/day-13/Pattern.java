import java.util.*;

public class Pattern {
    char[][] mat;

    public Pattern(List<List<Character>> rowsCharList) {
        mat = new char[rowsCharList.size()][rowsCharList.get(0).size()];

        for (int i = 0; i < rowsCharList.size(); i++) {
            for (int j = 0; j < rowsCharList.get(i).size(); j++) {
                mat[i][j] = rowsCharList.get(i).get(j);
            }
        }
    }
}
