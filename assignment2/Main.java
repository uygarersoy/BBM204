import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        ArrayList<String> demandValues = readFromFile(args[0]);
        ArrayList<Integer> values = stringToInt(demandValues.get(0));
        int total = 0;
        for (int i : values) {
            total += i;
        }
        PowerGridOptimization powerGrid = new PowerGridOptimization(values);
        OptimalPowerGridSolution result = powerGrid.getOptimalPowerGridSolutionDP();
        System.out.println("The total number of demanded gigawatts: " + total);
        System.out.println("Maximum number of satisfied gigawatts: " + result.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        ArrayList<Integer> temp = result.getHoursToDischargeBatteriesForMaxEfficiency();
        for (int i = 0; i < temp.size() - 1; i++) {
            System.out.print(temp.get(i) + ", ");
        }
        System.out.println(temp.get(temp.size()-1));
        System.out.println("The number of unsatisfied gigawatts: " +  (total - result.getmaxNumberOfSatisfiedDemands()));        
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");


        System.out.println("##MISSION ECO-MAINTENANCE##");
        ArrayList<String> maintenanceValues = readFromFile(args[1]);
        ArrayList<Integer> availCapacity = stringToInt(maintenanceValues.get(0));
        ArrayList<Integer> taskValues = stringToInt(maintenanceValues.get(1));
        int avail = availCapacity.get(0);
        int capacity = availCapacity.get(1);
        OptimalESVDeploymentGP opt = new OptimalESVDeploymentGP(taskValues);
        int esvValue = opt.getMinNumESVsToDeploy(avail, capacity);
        if (esvValue == -1) {
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }
        else {
            ArrayList<ArrayList<Integer>> assignedESVS = opt.getMaintenanceTasksAssignedToESVs();
            System.out.println("The minimum number of ESVs to deploy: " + esvValue);
            for (int i = 0; i < assignedESVS.size(); i++) {
                System.out.println("ESV " + (i + 1) + " tasks: " + assignedESVS.get(i));
            }
        }
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }


    public static ArrayList<String> readFromFile(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> res = new ArrayList<>();

            while (myReader.hasNextLine()) {
                res.add(myReader.nextLine());
            }
            myReader.close();
            return res;
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading file.");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Integer> stringToInt(String values) {
        ArrayList<Integer> res = new ArrayList<>();
        String[] temp = values.split(" ");
        for (String s : temp) {
            res.add(Integer.parseInt(s));
        }
        return res;
    }
}