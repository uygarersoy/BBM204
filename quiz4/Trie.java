import java.util.*;

public class Trie {
    Node root;

    Trie() {
        root = new Node();
    }

    public void insert(String string) {
        Node node = root;

        for (int i = 0; i < string.length(); i++) {
            if (!node.nextLetters.containsKey(string.charAt(i))) {
                Node newNode = new Node();
                node.nextLetters.put(string.charAt(i), newNode);
            }
            node = node.nextLetters.get(string.charAt(i));
        }
        node.isWord = true;
    }

    public List<String> search(String string) {
        Node node = root;

        for (int i = 0; i < string.length(); i++) {
            node = node.nextLetters.get(string.charAt(i));
            if (node == null) {
                return new ArrayList<>();
            }
        }

        return findWords(node, string);
    }

    public List<String> findWords(Node node, String string) {
        List<String> res = new ArrayList<>();

        if (node.isWord) {
            res.add(string);
        }

        for (Character chr : node.nextLetters.keySet()) {
            String newString = string + "" + chr;
            List<String> words = findWords(node.nextLetters.get(chr), newString);
            for (String str : words) {
                res.add(str);
            }
        }
        return res;
    }
}