import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        ArrayList<Integer> D = amountOfEnergyDemandsArrivingPerHour;
        int[] SOL = new int[N + 1];
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>();
        
        SOL[0] = 0;
        HOURS.add(new ArrayList<>());
        
        for (int j = 1; j <= N; j++) {
            SOL[j] = 0;
            ArrayList<Integer> currentHours = new ArrayList<>();
            for (int i = 0; i < j; i++) {
                int value = SOL[i] + Math.min(D.get(j - 1), (j - i) * (j - i));
                if (value > SOL[j]) {
                    SOL[j] = value;
                    currentHours.clear();
                    currentHours.addAll(HOURS.get(i));
                    currentHours.add(j);
                }
            }
            HOURS.add(currentHours);
        }
        OptimalPowerGridSolution solution = new OptimalPowerGridSolution(SOL[N], HOURS.get(HOURS.size()-1));
        return solution;
    }
}
