import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules
    private HashMap<String, ArrayList<String>> adjacencyList = new HashMap<>();
    private final Comparator<MolecularStructure> customCompInc = new Comparator<MolecularStructure>() {
        @Override
        public int compare(MolecularStructure m1, MolecularStructure m2) {
            return Integer.compare(m1.getMolecules().size(), m2.getMolecules().size());
        }
    };

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    public void makeAdjList() {
        for (Molecule molecule : molecules) {
            for (String s : molecule.getBonds()) {
                if (!adjacencyList.containsKey(molecule.getId())) {
                    ArrayList<String> adj = new ArrayList<>();
                    adj.add(s);
                    adjacencyList.put(molecule.getId(), adj);
                }
                else {
                    ArrayList<String> temp = adjacencyList.get(molecule.getId());
                    temp.add(s);
                    adjacencyList.put(molecule.getId(), temp);
                }

                if (!adjacencyList.containsKey(s)) {
                    ArrayList<String> adj = new ArrayList<>();
                    adj.add(molecule.getId());
                    adjacencyList.put(s, adj);
                }
                else {
                    ArrayList<String> temp = adjacencyList.get(s);
                    temp.add(molecule.getId());
                    adjacencyList.put(s, temp);
                }

            }
        }
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        makeAdjList();
        ArrayList<MolecularStructure> structures = new ArrayList<>();
        HashSet<String> seen = new HashSet<>();
        for (Molecule molecule : molecules) {
            if (!seen.contains(molecule.getId())) {
                MolecularStructure molecularStructure = new MolecularStructure();
                dfs(molecule, seen, molecularStructure);
                structures.add(molecularStructure);
            }
        }
        return structures;
    }

    public void dfs(Molecule molecule, HashSet<String> seen, MolecularStructure molecularStructure) {
        seen.add(molecule.getId());
        molecularStructure.addMolecule(molecule);

        for (String nextMolecule : adjacencyList.get(molecule.getId())) {
            if (!seen.contains(nextMolecule)) {
                for (Molecule temp : molecules) {
                    if (temp.getId().equals(nextMolecule)) {
                        dfs(temp, seen, molecularStructure);
                    }
                }
            }
        }
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        Collections.sort(molecularStructures, customCompInc);
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        for (int i = 0 ; i < molecularStructures.size(); i++) {
            System.out.println("Molecules in Molecular Structure " + (i + 1) + " : " + molecularStructures.get(i).toString());
        }
    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        for (MolecularStructure target : targetStructures) {
            boolean unique = true;
            for (MolecularStructure source : sourceStructures) {
                if (source.equals(target)) {
                    unique = false;
                    break;
                }
            }
            
            if (unique) {
                anomalyList.add(target);
            }
        }

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        Collections.sort(molecularStructures, customCompInc);
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure.toString());
        }

    }
}