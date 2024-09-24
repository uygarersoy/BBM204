import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Method with a Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"(.+?)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Method with a Regular Expression to extract double values from the fileContent
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([+-]?\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Method with a Regular Expression to extract point objects from the fileContent
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "\\s*=\\s*\\((\\s*-?\\d+(?:\\.\\d+)?\\s*,\\s*-?\\d+(?:\\.\\d+)?\\s*)\\)");
        Matcher m = p.matcher(fileContent);
        if (!m.find()) {
            return null;
        }
        String[] coordinates = m.group(1).split(",");
        double x = Double.parseDouble(coordinates[0].trim());
        double y = Double.parseDouble(coordinates[1].trim());
        Point point = new Point((int) x, (int) y);
        return point;
    } 

    /**
     * Function to extract the train lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();
        String[] lines = fileContent.split("\n");
        
        for (int i = 0; i < lines.length; i+=2) {
            String line = lines[i];
            String nextLine = lines[i+1];
            String stationName = getStringVar("train_line_name", line);
            List<Station> stations = new ArrayList<>();
            Pattern p = Pattern.compile("\\(\\s*(-?\\d+(?:\\.\\d+)?)\\s*,\\s*(-?\\d+(?:\\.\\d+)?)\\s*\\)");
            Matcher m = p.matcher(nextLine);
            int stationCount = 1;

            while (m.find()) {
                int x = Integer.parseInt(m.group(1));
                int y = Integer.parseInt(m.group(2));
                Point point = new Point(x, y);
                String description = stationName + " Line Station " + Integer.toString(stationCount); 
                Station station = new Station(point, description);
                stations.add(station);
                stationCount += 1;
            }
            TrainLine trainLine = new TrainLine(stationName, stations);
            trainLines.add(trainLine);
        }
        return trainLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {
        List<String> inputLines = readLines(filename);
        int index = 0;
        lines = new ArrayList<>();
        String trainLinesCombined = "";
        
        while (index < inputLines.size()) {
            String line = inputLines.get(index);
            String[] splitted = line.split("=");
            String first = splitted[0].trim();
            String second = splitted[1].trim();
            if (first.equals("num_train_lines")) {
                numTrainLines = getIntVar("num_train_lines", line);
                index += 1;
            }
            else if (first.equals("starting_point")) {
                Point start = getPointVar("starting_point", line);
                Station station = new Station(start, "Starting Point");
                startPoint = station;
                index += 1;
            }
            else if (first.equals("destination_point")) {
                Point dest = getPointVar("destination_point", line);
                Station station = new Station(dest, "Final Destination");
                destinationPoint = station;
                index += 1;
            }
            else if (first.equals("average_train_speed")) {
                averageTrainSpeed = getDoubleVar("average_train_speed", line) * 50 / 3.0;
                index += 1;
            }
            else if (first.equals("train_line_name")) {
                String nextLine = inputLines.get(++index);
                trainLinesCombined += line + "\n" + nextLine + "\n";
                index += 1;
            }
        }
        lines = getTrainLines(trainLinesCombined);
    }


    public static List<String> readLines(String fileName) {
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
            e.printStackTrace();
            return null;
        }
    }
}