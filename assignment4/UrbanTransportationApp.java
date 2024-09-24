import java.io.Serializable;
import java.util.*;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    
    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function calculate the fastest route from the user's desired starting point to 
     * the desired destination point, taking into consideration the hyperloop train
     * network. 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        HashMap<Station, List<RouteDirection>> adjList = adjacencyList(network);
        List<RouteDirection> routeDirections = dijkstraShortestPath(adjList, network.startPoint, network.destinationPoint, network);
        return routeDirections;
    }

    public List<RouteDirection> dijkstraShortestPath(HashMap<Station, List<RouteDirection>> graph, Station source, Station destination, HyperloopTrainNetwork net) {
        Map<Station, Double> distances = new HashMap<>();
        Map<Station, RouteDirection> previousRoute = new HashMap<>();
        Comparator<Station> customComparator = (station1, station2) -> Double.compare(distances.get(station1), distances.get(station2));
        PriorityQueue<Station> pq = new PriorityQueue<>(customComparator);

        for (Station station : graph.keySet()) {
            distances.put(station, Double.POSITIVE_INFINITY);
        }

        distances.put(source, 0.0);
        pq.add(source);

        while (!pq.isEmpty()) {
            Station curr = pq.poll();

            for (RouteDirection route : graph.get(curr)) {
                double tempDist = distances.get(curr) + route.duration;
                Station routeEnd = findStation(net, route.endStationName);
                if (tempDist < distances.get(routeEnd)) {
                    distances.put(routeEnd, tempDist);
                    previousRoute.put(routeEnd, route);
                    pq.add(routeEnd);
                }

            }
        }

        return shortestPathBuilder(destination, previousRoute, net);
    }

    public List<RouteDirection> shortestPathBuilder(Station destination, Map<Station, RouteDirection> previousRoute, HyperloopTrainNetwork net) {
        List<RouteDirection> shortestPath = new ArrayList<>();
        while (destination != null && previousRoute.containsKey(destination)) {
            shortestPath.add(previousRoute.get(destination));
            destination = findStation(net, previousRoute.get(destination).startStationName);
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public Station findStation(HyperloopTrainNetwork net, String stationName) {
        List<Station> stations = getVertices(net);
        for (Station station : stations) {
            if (station.description.equals(stationName)) {
                return station;
            }
        }
        return null;
    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        System.out.println("The fastest route takes " + fastestTime(directions) + " minute(s).");
        System.out.println("Directions\n----------");
        for (int i = 0; i < directions.size(); i++) {
            RouteDirection direction = directions.get(i);
            if (direction.trainRide) {
                System.out.println(i+1 + ". Get on the train from \"" + direction.startStationName + "\" to " + "\"" + direction.endStationName + "\" for " + String.format("%.2f", direction.duration) + " minutes.");
            }
            else {
                System.out.println(i+1 + ". Walk from \"" + direction.startStationName + "\" to " + "\"" + direction.endStationName + "\" for " + String.format("%.2f", direction.duration) + " minutes.");
            }
        }
    }

    public int fastestTime(List<RouteDirection> directions) {
        double total = 0.0;

        for (RouteDirection route : directions) {
            total += route.duration;
        }
        long rounded = Math.round(total);
        return (int) rounded;
    }

    public HashMap<Station, List<RouteDirection>> adjacencyList(HyperloopTrainNetwork network) {
        HashMap<Station, List<RouteDirection>> adjList = new HashMap<>();
        List<Station> stations = getVertices(network);
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            List<RouteDirection> edges = new ArrayList<>();
            adjList.put(station, edges);
            for (int j = 0; j < stations.size(); j++) {
                if (i == j) {
                    continue;
                }
                Station nextStation = stations.get(j);
                if (station.description.split(" ")[0].equals(nextStation.description.split(" ")[0]) && Math.abs(j-i) == 1) {
                    double dist = getDistance(station.coordinates, nextStation.coordinates);
                    double time = dist / network.averageTrainSpeed;
                    RouteDirection rd = new RouteDirection(station.description, nextStation.description, time, true);
                    adjList.get(station).add(rd);
                }
                else if (!station.description.split(" ")[0].equals(nextStation.description.split(" ")[0])){
                    double dist = getDistance(station.coordinates, nextStation.coordinates);
                    double time = dist / network.averageWalkingSpeed;
                    RouteDirection rd = new RouteDirection(station.description, nextStation.description, time, false);
                    adjList.get(station).add(rd);
                }
            }
        }
        return adjList;
    }

    public double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public List<Station> getVertices(HyperloopTrainNetwork network) {
        List<Station> res = new ArrayList<>();
        res.add(network.startPoint);
        for (TrainLine trainLine : network.lines) {
            for (Station station : trainLine.trainLineStations) {
                res.add(station);
            }
        }
        res.add(network.destinationPoint);
        return res;
    }
}