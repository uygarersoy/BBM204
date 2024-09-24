import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner; 
import java.util.ArrayList;

public class FileIO{

    public static int[] readIntegersFromFile(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> lines = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
            ArrayList<Integer> flowDurations = splitLine(lines);
            return convertToIntArray(flowDurations);
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading file.");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Integer> splitLine(ArrayList<String> line) {
        ArrayList<Integer> flowDurations = new ArrayList<>();
        for (int i = 1; i < line.size(); i++) {
            String[] splitted = line.get(i).split(",");
            flowDurations.add(Integer.parseInt(splitted[6]));
        }
        return flowDurations;
    }


    public static int[] convertToIntArray(ArrayList<Integer> integers) {
        int[] res = new int[integers.size()];

        for (int i = 0; i < integers.size(); i++) {
            res[i] = integers.get(i);
        }
        return res;
    }
}