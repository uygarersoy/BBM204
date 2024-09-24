import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) {
        Collections.sort(maintenanceTaskEnergyDemands, Comparator.reverseOrder());
        ArrayList<Integer> sums = new ArrayList<>();
        if (maintenanceTaskEnergyDemands.get(0) > maxESVCapacity) {
            return -1;
        }
        for (int i = 0; i < maintenanceTaskEnergyDemands.size(); i++) {
            if (maintenanceTasksAssignedToESVs.size() == 0) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(maintenanceTaskEnergyDemands.get(i));
                maintenanceTasksAssignedToESVs.add(temp);
                sums.add(maintenanceTaskEnergyDemands.get(i));
            }
            else {
                boolean flag = false;
                for (int j = 0; j < maintenanceTasksAssignedToESVs.size(); j++) {
                    if (sums.get(j) + maintenanceTaskEnergyDemands.get(i) <= maxESVCapacity) {
                        maintenanceTasksAssignedToESVs.get(j).add(maintenanceTaskEnergyDemands.get(i));
                        sums.set(j, sums.get(j) + maintenanceTaskEnergyDemands.get(i));
                        flag = true;
                        break;
                    }
                }
                
                if (!flag) {
                    ArrayList<Integer> newEsv = new ArrayList<>();
                    newEsv.add(maintenanceTaskEnergyDemands.get(i));
                    maintenanceTasksAssignedToESVs.add(newEsv);
                    sums.add(maintenanceTaskEnergyDemands.get(i));
                }
            }
        }
        if (maintenanceTasksAssignedToESVs.size() <= maxNumberOfAvailableESVs) {
            return maintenanceTasksAssignedToESVs.size();
        }
        return -1;
    }
}