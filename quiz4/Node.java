import java.util.*;

public class Node {
    boolean isWord;
    HashMap<Character, Node> nextLetters;
    Node() {
        isWord = false;
        nextLetters = new HashMap<>();
    }
}