import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans
    private List<Molecule> weakestHuman = new ArrayList<>();
    private List<Molecule> weakestVitales = new ArrayList<>();
    Comparator<Bond> compByBond = new Comparator<Bond>() {
        @Override
        public int compare(Bond b1, Bond b2) {
            return Double.compare(b1.getWeight(), b2.getWeight());
        }
    };


    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        weakestHuman = weakestBondPoints(humanStructures);
        weakestVitales = weakestBondPoints(diffStructures);
        List<Molecule> combined = combineMolecules();
        HashMap<Molecule, ArrayList<Bond>> possibleBonds = getPossibleBonds(combined);
        List<Bond> tempSerum = mstConstructor(combined, possibleBonds);
        Collections.sort(tempSerum, compByBond);
        List<Bond> serum = swapFromTo(tempSerum);

        return serum;
    }

    public List<Bond> swapFromTo(List<Bond> serum) {
        List<Bond> swappedBonds = new ArrayList<>();
        for (Bond bond : serum) {
            if (bond.getFrom().compareTo(bond.getTo()) > 0) {
                Bond swap = new Bond(bond.getFrom(), bond.getTo(), bond.getWeight());
                swappedBonds.add(swap);
            }
            else {
                swappedBonds.add(bond);
            }
        }
        return swappedBonds;
    }

    public List<Bond> mstConstructor(List<Molecule> combined, HashMap<Molecule, ArrayList<Bond>> bonds) {
        HashSet<Molecule> seen = new HashSet<>();
        List<Bond> mstEdges = new ArrayList<>();
        PriorityQueue<Bond> pq = new PriorityQueue<>(Comparator.comparingDouble(bond -> bond.getWeight()));
        Molecule start = combined.get(0);
        seen.add(start);
        
        for (Bond b : bonds.get(start)) {
            pq.add(b);
        }

        while (!pq.isEmpty()) {
            Bond minBond = pq.poll();
            Molecule to = minBond.getTo();
            Molecule from = minBond.getFrom();

            if (seen.contains(to)) {
                continue;
            }

            seen.add(from);
            seen.add(to);
            mstEdges.add(minBond);
            for (Bond b : bonds.get(to)) {
                if (!seen.contains(b)) {
                    pq.add(b);
                }
            }
        }
        return mstEdges;
    }

    public List<Molecule> combineMolecules() {
        List<Molecule> combined = new ArrayList<>();
        for (Molecule m : weakestHuman) {
            combined.add(m);
        }

        for (Molecule m : weakestVitales) {
            combined.add(m);
        }
        return combined;
    }

    public HashMap<Molecule, ArrayList<Bond>> getPossibleBonds(List<Molecule> combined) {
        HashMap<Molecule, ArrayList<Bond>> possibleBonds = new HashMap<>();

        for (int i = 0; i < combined.size(); i++) {
            for (int j = i + 1; j < combined.size(); j++) {
                double newBond = (combined.get(i).getBondStrength() + combined.get(j).getBondStrength()) / 2.0;
                if (!possibleBonds.containsKey(combined.get(i))) {
                    Bond bond = new Bond(combined.get(j), combined.get(i), newBond);
                    ArrayList<Bond> temp = new ArrayList<>();
                    temp.add(bond);
                    possibleBonds.put(combined.get(i), temp);
                }
                else {
                    Bond bond = new Bond(combined.get(j), combined.get(i), newBond);
                    possibleBonds.get(combined.get(i)).add(bond);
                }
                if (!possibleBonds.containsKey(combined.get(j))) {
                    Bond bond = new Bond(combined.get(i), combined.get(j), newBond);
                    ArrayList<Bond> temp = new ArrayList<>();
                    temp.add(bond);
                    possibleBonds.put(combined.get(j), temp);
                }
                else {
                    Bond bond = new Bond(combined.get(i), combined.get(j), newBond);
                    possibleBonds.get(combined.get(j)).add(bond);
                }
            }
        }
        return possibleBonds;
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {        

        System.out.println("Typical human molecules selected for synthesis: " + weakestHuman);
        System.out.println("Vitales molecules selected for synthesis: " + weakestVitales);
        System.out.println("Synthesizing the serum...");
        double total = 0;
        for (Bond bond : serum) {
            System.out.println("Forming a bond between " + bond.getFrom() + " - " + bond.getTo() + " with strength " + String.format("%.2f", bond.getWeight()));
            total += bond.getWeight();
        }
        System.out.println("The total serum bond strength is " + String.format("%.2f", total));
    }

    public List<Molecule> weakestBondPoints(List<MolecularStructure> molecularStructure) {
        
        List<Molecule> res = new ArrayList<>();
        for (MolecularStructure ms : molecularStructure) {
            int min = Integer.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < ms.getMolecules().size(); i++) {
                if (ms.getMolecules().get(i).getBondStrength() < min) {
                    min = ms.getMolecules().get(i).getBondStrength();
                    index = i;
                }
            }
            res.add(ms.getMolecules().get(index));
        }
        return res;
    }

}