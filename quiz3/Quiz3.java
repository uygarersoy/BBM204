import java.io.*;
import java.util.*;

public class Quiz3 {

    static int[] leader;
    static int[] order;

    public static void main(String[] args) throws IOException{

        try  {

            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);

            while(myReader.hasNextLine()) {

                int test = Integer.parseInt(myReader.nextLine());
                for (int i = 0; i < test; i++) {

                    String[] spValues = myReader.nextLine().split(" ");
                    int s = Integer.parseInt(spValues[0]);
                    int p = Integer.parseInt(spValues[1]);
                    int[][] vertices = new int[p][2];

                    for (int j = 0; j < p; j++) {

                        String[] temp = myReader.nextLine().split(" ");
                        int x = Integer.parseInt(temp[0]);
                        int y = Integer.parseInt(temp[1]);
                        vertices[j][0] = x;
                        vertices[j][1] = y;
                    }

                    leader = new int[p];
                    order = new int[p];

                    for (int j = 0; j < p; j++) {
                        leader[j] = j;
                        order[j] = 0;
                    }

                    double Tvalue = minimumTValue(s, vertices);
                    System.out.println(String.format("%.2f", Tvalue));
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading file.");
            e.printStackTrace();
        }
    }

    public static int findKruskalMst(int vertex) {

        if (vertex != leader[vertex]) {
            leader[vertex] = findKruskalMst(leader[vertex]);
        }
        
        return leader[vertex];

    }

    public static double minimumTValue(int noOfDrones, int[][] vertices) {

        ArrayList<ArrayList<Double>> edgeList = new ArrayList<>();
        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                double dist = Math.sqrt(Math.pow(vertices[i][0] - vertices[j][0], 2) + Math.pow(vertices[i][1] - vertices[j][1], 2));
                ArrayList<Double> edge = new ArrayList<>();
                edge.add((double)i);
                edge.add((double)j);
                edge.add(dist);
                edgeList.add(edge);
            }
        }

        Comparator<ArrayList<Double>> sortArrayList = new Comparator<ArrayList<Double>>() {
            @Override
            public int compare(ArrayList<Double> list1, ArrayList<Double> list2) {
                return list1.get(2).compareTo(list2.get(2));
            }
        };

        Collections.sort(edgeList, sortArrayList);

        int noOfVertices = vertices.length;
        double TValue = 0;

        for (int i = 0; i < edgeList.size(); i++) {

            int source = (int)(double) edgeList.get(i).get(0);
            int destination = (int)(double) edgeList.get(i).get(1);
            double distance = edgeList.get(i).get(2);

            if (findKruskalMst(source) != findKruskalMst(destination)) {
            
                unionKruskalMst(source, destination);
                noOfVertices--;
                TValue = distance;
                
                if (noOfVertices == noOfDrones) {
                    break;
                }
            }
        }
        return TValue;
    }

    public static void unionKruskalMst(int vertexA, int vertexB) {

        int rootA = findKruskalMst(vertexA);
        int rootB = findKruskalMst(vertexB);

        if (rootA != rootB) {

            if (order[rootA] < order[rootB]) {
                leader[rootA] = rootB;
            }

            else if (order[rootA] > order[rootB]) {
                leader[rootB] = rootA;
            }

            else {
                leader[rootB] = rootA;
                order[rootA]++;
            }
        }

    }
}