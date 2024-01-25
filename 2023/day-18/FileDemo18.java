import java.io.*;
import java.util.*;

public class FileDemo18 {

    private static int getCornerValue(String[][] mat2, int x, int y) {
        int ret = -1;

        

        return ret;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-18/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        String[][] mat = new String[10000][10000];
        int i = 500;
        int j = 500;

        for (String s : stringPerLineList) {
            String[] split1 = s.split(" ", 0);
            char dir = split1[0].charAt(0);
            int amt = Integer.parseInt(split1[1]);
            String colour = split1[2].substring(1, split1[2].length()-1);//e.g. (#272dc0)
            
            switch(dir) {
                case 'U':
                    for (int x = 1; x <= amt; x++) {
                        i--;
                        mat[i][j] = colour;
                    }
                    break;
                case 'L':
                    for (int x = 1; x <= amt; x++) {
                        j--;
                        mat[i][j] = colour;
                    }
                    break;
                case 'R':
                    for (int x = 1; x <= amt; x++) {
                        j++;
                        mat[i][j] = colour;
                    }
                    break;
                case 'D':
                    for (int x = 1; x <= amt; x++) {
                        i++;
                        mat[i][j] = colour;
                    }
                    break;
                default:
            }

            System.out.println("i: " + i + " j: " + j);
        }

        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        for (int x = 0; x < mat.length; x++) {
            for (int y = 0; y < mat[0].length; y++) {
                if (mat[x][y] != null) {
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                }
            }
        }

        //System.out.println("x: " + minX + " : " + maxX + " | y: " + minY + " : " + maxY);

        String[][] mat2 = new String[maxX - minX + 1][maxY - minY + 1];

        int a = 0;

        for (int x = minX; x <= maxX; x++) {
            int b = 0;

            for (int y = minY; y <= maxY; y++) {
                mat2[a][b++] = mat[x][y];
            }

            a++;

        }

        int[][] corners = new int[mat2.length][mat2[0].length];

        for (int x = 0; x < mat2.length; x++) {
            for (int y = 0; y < mat2[0].length; y++) {
                corners[x][y] = getCornerValue(mat2, x, y);
            }
        }


    }
}
