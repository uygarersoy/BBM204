import java.util.*;
import java.io.*;

public class Quiz1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> lines = readIntegersFromFile(args[0]);
        int ignore_index = 0;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("...")) {
                ignore_index = i;
                break;
            }
        }
        ArrayList<String> ignores = new ArrayList<String>();
        for (int i = 0; i < ignore_index; i++) {
            ignores.add(lines.get(i));
        }
        ArrayList<String> titles = new ArrayList<String>();
        for (int i = ignore_index + 1; i < lines.size(); i++) {
            titles.add(lines.get(i));
        }

        ArrayList<String> each_title = new ArrayList<String>();

        for (int i = 0; i < titles.size(); i++) {
            String[] splitted = clean(titles.get(i).split(" "));
            for (int j = 0; j < splitted.length; j++) {
                boolean flag = false;
                for (String ignore : ignores) {
                    if (ignore.equals(splitted[j].toLowerCase())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    String res = "";
                    for (int k = 0; k < j; k++) {
                        res += splitted[k].toLowerCase() + " ";
                    }
                    res += splitted[j].toUpperCase();
                    if (j != splitted.length-1) {
                        res += " ";
                    }
                    for (int k = j + 1; k < splitted.length; k++) {
                        if (k == splitted.length -1) {
                            res += splitted[k].toLowerCase();
                        }
                        else {
                            res += splitted[k].toLowerCase() + " ";
                        }
                    }
                    res += "," + Integer.toString(i) + "," + Integer.toString(j);
                    each_title.add(res);
                }
            }
        }
        sortTitles(each_title);
        for (String s : each_title) {
            String[] temp = s.split(",");
            System.out.println(temp[0]);
        }
    }

    public static ArrayList<String> readIntegersFromFile(String fileName) {
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

    public static void sortTitles(ArrayList<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            for (int j = 0; j < titles.size() - i - 1; j++) {
                String[] temp1 = titles.get(j).split(",");
                String[] temp2 = temp1[0].split(" ");
                String check1 = temp2[Integer.parseInt(temp1[2])];

                String[] temp3 = titles.get(j + 1).split(",");
                String[] temp4 = temp3[0].split(" ");
                String check2 = temp4[Integer.parseInt(temp3[2])];
                
                if (check1.compareTo(check2) > 0) {
                    String tmp = titles.get(j);
                    titles.set(j, titles.get(j + 1));
                    titles.set(j + 1, tmp);
                }
            }
        }
    }

    public static String[] clean(String[] s) {
        int count = 0;
        for (String str : s) {
            if (str.length() == 0) {
                count++;
            } 
        }
        String[] res = new String[s.length-count];
        int i = 0;
        for (String str : s) {
            if (str.length() != 0) {
                res[i] = str;
                i++;
            }
        }
        return res;
    }
}