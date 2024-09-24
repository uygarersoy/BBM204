import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;
    public HashMap<Integer, List<Integer>> adjList = new HashMap<>();

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public void makeAdjacencyList() {

        for (Task task : tasks) {
            int taskID = task.getTaskID();
            for (Integer dep : task.getDependencies()) {
                if (!adjList.containsKey(dep)) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(taskID);
                    adjList.put(dep, temp);
                }
                else {
                    adjList.get(dep).add(taskID);
                }
            }
        }

        for (int i = 0; i < tasks.size(); i++) {
            if (!adjList.containsKey(i)) {
                List<Integer> temp = new ArrayList<>();
                adjList.put(i, temp);
            }
        }
    }

    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        HashSet<Integer> seen = new HashSet<>();
        List<Integer> sort = new ArrayList<>();

        for (Task task : tasks) {
            int taskID = task.getTaskID();
            if (!seen.contains(taskID)) {
                dfs(taskID, stack, seen);
            }
        }

        while (!stack.isEmpty()) {
            sort.add(stack.pop());
        }
        return sort;
    }

    public void dfs(int taskId, Stack<Integer> stack, HashSet<Integer> seen) {
        seen.add(taskId);
        for (int id : adjList.get(taskId)) {
            if (!seen.contains(id)) {
                dfs(id, stack, seen);
            }
        }
        stack.push(taskId);
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int[] schedule = getEarliestSchedule();
        int projectDuration = schedule[schedule.length-1] + tasks.get(schedule.length-1).getDuration();
        return projectDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        makeAdjacencyList();
        List<Integer> sorted = topologicalSort();
        int[] schedule = new int[tasks.size()];

        for (int i = 0; i < tasks.size(); i++) {
            schedule[i] = 0;
        }

        for (int taskId : sorted) {
            Task task = tasks.get(taskId);
            int earliestStartTimeForTask = 0;

            for (int dependency : task.getDependencies()) {
                earliestStartTimeForTask = Math.max(earliestStartTimeForTask, schedule[dependency] + tasks.get(dependency).getDuration());
            }

            schedule[taskId] = earliestStartTimeForTask;
        }

        return schedule;
    }


    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) {
            System.out.print(symbol);
        }
        System.out.println();
    }

    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }
}