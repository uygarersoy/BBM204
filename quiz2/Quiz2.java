import java.util.*;
import java.io.*;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        
        ArrayList<String> lines = readFile(args[0]);
        String[] massResourceCount = lines.get(0).split(" ");
        int capacity = Integer.parseInt(massResourceCount[0]);
        int resourceCount = Integer.parseInt(massResourceCount[1]);
        String[] massSplit = lines.get(1).split(" ");
        int[] resources = stringArrToIntArray(massSplit, resourceCount);
        boolean[][] dp = tabulation(capacity, resources);
        String[] maxAndRes = consoleOutput(dp);
        System.out.println(maxAndRes[0]);
        System.out.println(maxAndRes[1]);
    }

    public static int[] stringArrToIntArray(String[] massSplit, int size) {
        int[] resources = new int[size];

        for (int i = 0; i < size; i++) {
            resources[i] = Integer.parseInt(massSplit[i]);
        }
        return resources;
    }

    public static String[] consoleOutput(boolean[][] dp) {
        String[] maxAndRes = new String[2];
        String maximum = "0";
        String res = "";

        for (int i = 0; i < dp.length; i++) {
            String temp = "";
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j]) {
                    temp += "1";
                }
                else {
                    temp += "0";
                }
            }
            if (dp[i][dp[0].length-1]) {
                maximum = String.valueOf(i);
            }
            res += temp;
            if (i != dp.length - 1) {
                res += "\n";
            }
        }
        maxAndRes[0] = maximum;
        maxAndRes[1] = res;
        return maxAndRes;
    }

    public static boolean[][] tabulation(int capacity, int[] resources) {
        boolean[][] dp = new boolean[capacity+1][resources.length+1];
        
        for (int i = 0; i < capacity + 1; i++) {
            for (int j = 0; j < resources.length + 1; j++) {
                dp[i][j] = false;
            }
        }

        for (int i = 0; i < resources.length + 1; i++) {
            dp[0][i] = true;
        }

        for (int i = 1; i < capacity + 1; i++) {
            for (int j = 1; j < resources.length + 1; j++) {
                if (resources[j-1] > i) {
                    dp[i][j] = dp[i][j-1];
                }
                else {
                    dp[i][j] = dp[i][j - 1] || dp[i - resources[j - 1]][j - 1];
                }
            }
        }

        return dp;
    }

    public static ArrayList<String> readFile(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> lines = new ArrayList<String>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
            return lines;
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading file.");
            e.printStackTrace();
            return null;
        }
    }
}
