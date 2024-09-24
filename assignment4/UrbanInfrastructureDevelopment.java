import java.io.Serializable;
import java.util.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project : projectList) {
            int[] schedule = project.getEarliestSchedule();
            project.printSchedule(schedule);
        }
    }

    /**
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String fileName) {
        List<Project> projectList = new ArrayList<>();
        
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList listOfProjects = document.getElementsByTagName("Project");

            for (int i = 0; i < listOfProjects.getLength(); i++) {
                Node project = listOfProjects.item(i);
                Element currProject = (Element) project;
                String projectName = currProject.getElementsByTagName("Name").item(0).getTextContent();
                NodeList listOfTasks = currProject.getElementsByTagName("Task");
                List<Task> tasks = new ArrayList<>();

                for (int j = 0; j < listOfTasks.getLength(); j++) {
                    Node allTasks = listOfTasks.item(j);
                    Element currTask = (Element) allTasks;
                    int taskId = Integer.parseInt(currTask.getElementsByTagName("TaskID").item(0).getTextContent());
                    String description = currTask.getElementsByTagName("Description").item(0).getTextContent();
                    int duration = Integer.parseInt(currTask.getElementsByTagName("Duration").item(0).getTextContent());
                    List<Integer> dependencies = new ArrayList<>();
                    NodeList dependencyList = currTask.getElementsByTagName("DependsOnTaskID");

                    for (int k = 0; k < dependencyList.getLength(); k++) {
                        Node dependency = dependencyList.item(k);
                        dependencies.add(Integer.parseInt(dependency.getTextContent()));
                    }
                    Task task = new Task(taskId, description, duration, dependencies);
                    tasks.add(task);
                }
                Project newProject = new Project(projectName, tasks);
                projectList.add(newProject);
            }
            return projectList;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}