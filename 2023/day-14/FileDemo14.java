import java.io.*;
import java.util.*;

public class FileDemo14 {

    private static void tiltNorth(char[][] mat) {
        for (int j = 0; j < mat[0].length; j++) {
            for (int i = 1; i < mat.length; i++) {
                if (i == 0 || mat[i-1][j] == '#' || mat[i-1][j] == 'O') {
                    //do nothing
                }
                else {
                    if (mat[i][j] == 'O') {
                        mat[i][j] = '.';
                        mat[i-1][j] = 'O';
                        i-=2;
                    }
                }
            }
        }
    }

    private static long countWeight(char[][] mat) {
        long ret = 0;

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 'O') {
                    ret += (mat.length - i);
                }
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        File file = new File("2023/day-14/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        char[][] mat = new char[stringPerLineList.size()][stringPerLineList.get(0).length()];

        for (int i = 0; i < stringPerLineList.size(); i++) {
            for (int j = 0; j < stringPerLineList.get(i).length(); j++) {
                mat[i][j] = stringPerLineList.get(i).charAt(j);
            }
        }

        tiltNorth(mat);

        //for (int i = 0; i < mat.length; i++) {
        //    System.out.println(Arrays.toString(mat[i]));
        //}

        long ret = countWeight(mat);

        System.out.println(ret);
    }
    
}
