import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try {
            List<Molecule> humanMolecules = moleculeParser("HumanMolecularData", filename);
            List<Molecule> vitalesMolecules = moleculeParser("VitalesMolecularData", filename);
            molecularDataHuman = new MolecularData(humanMolecules);
            molecularDataVitales = new MolecularData(vitalesMolecules);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static List<Molecule> moleculeParser(String humanVitales, String FileName) throws Exception {
        List<Molecule> molecules = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(FileName);
        Document doc = db.parse(FileName);

        NodeList nodeList = doc.getElementsByTagName(humanVitales);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeElement = (Element) nodeList.item(i);
            NodeList moleculeList = nodeElement.getElementsByTagName("Molecule");
            for (int j = 0; j < moleculeList.getLength(); j++) {
				Element element = (Element) (moleculeList.item(j));
				String id = element.getElementsByTagName("ID").item(0).getTextContent();
				int bondStrength = Integer.parseInt(element.getElementsByTagName("BondStrength").item(0).getTextContent());
				List<String> bonds = new ArrayList<>();
				NodeList bondList = element.getElementsByTagName("Bonds").item(0).getChildNodes();
				for (int k = 0; k < bondList.getLength(); k++) {
					if (bondList.item(k).getNodeType() == Node.ELEMENT_NODE) {
						bonds.add(bondList.item(k).getTextContent());
					}
				}
				Molecule molecule = new Molecule(id, bondStrength, bonds);
				molecules.add(molecule);
            }
        }
        return molecules;
    }
}