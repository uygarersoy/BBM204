import java.io.*;
import java.util.*;

public class Quiz4 {
    public static void main(String[] args) throws IOException {
        List<String> database = readFile(args[0]);
        List<String> queries = readFile(args[1]);
        List<List<String>> databaseLines = util(database, 1);
        List<List<String>> queryLines = util(queries, 0);
        HashMap<String, Long> wordWeight = populateHashMap(databaseLines);
        Trie trie = new Trie();
        populateTrie(databaseLines, trie);
        processQueries(queryLines, trie, wordWeight);
    }

    public static HashMap<String, Long> populateHashMap(List<List<String>> databaseLines) {
        HashMap<String, Long> res = new HashMap<>();
        for (List<String> line : databaseLines) {
            res.put(line.get(1), Long.parseLong(line.get(0)));
        }
        return res;
    }

    public static void processQueries(List<List<String>> queryLines, Trie trie, HashMap<String, Long> weights) {
        for (List<String> list : queryLines) {
            List<String> temp = trie.search(list.get(0));
            Comparator<String> customComparator = (s1, s2) -> {
            Long weight1 = weights.get(s1);
            Long weight2 = weights.get(s2);
                return Long.compare(weight2, weight1);
            };
            temp.sort(customComparator);
            print(temp, Long.parseLong(list.get(1)), list.get(0), weights);
        }
    }

    public static void print(List<String> words, Long limit, String prefix, HashMap<String, Long> weights) {
        String output = "Query received: \"%s\" with limit %d. Showing results:";
        String format = String.format(output, prefix, limit);
        System.out.println(format);
        if (limit == 0 || words.size() == 0) {
            System.out.println("No results.");
        }
        else {
            for (int i = 0; i < Math.min(limit, words.size()); i++) {
                System.out.println("- " + weights.get(words.get(i)) + " " + words.get(i));
            }
        }
    }

    public static void populateTrie(List<List<String>> lines, Trie trie) {
        for (List<String> list : lines) {
            String s = list.get(1);
            trie.insert(s);
        }
    }

    public static List<List<String>> util(List<String> lines, int index) {
        List<List<String>> res = new ArrayList<>();
        for (int i = index; i < lines.size(); i++) {
            String[] sep = lines.get(i).split("\t");
            List<String> temp = new ArrayList<>();
            temp.add(sep[0].toLowerCase());
            temp.add(sep[1].toLowerCase());
            res.add(temp);
        }
        return res;
    }

    public static List<String> readFile(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            List<String> lines = new ArrayList<String>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
            return lines;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}