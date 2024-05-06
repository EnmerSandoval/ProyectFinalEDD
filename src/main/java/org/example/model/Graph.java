package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(Node node){
        nodes.put(node.getName(), node);
    }

    public void addRoute(String origin, String destination, int timeCar, int timeWalk, int gasolineExpensive, int personalAttrition, int distance){
       
        Node nodeOrigin = nodes.get(origin);
        Node nodeDestination = nodes.get(destination);
        Route route = new Route(nodeOrigin, nodeDestination, timeCar, timeWalk, gasolineExpensive, personalAttrition, distance);
        nodeOrigin.addRouteOut(route);
    }

    public Route getBestRouteByGasoline(String origin) {
        Node originNode = getNode(origin);
        if (originNode == null) {
            return null;
        }

        List<Route> routes = new ArrayList<>(originNode.getRoutesOut());
        Collections.sort(routes, (r1, r2) -> Integer.compare(r1.getGasolineExpensive(), r2.getGasolineExpensive()));

        return routes.isEmpty() ? null : routes.get(0);
    }

    public Route getBestRouteByPhysicalAttrition(String origin) {
        Node originNode = getNode(origin);
        if (originNode == null) {
            return null;
        }

        List<Route> routes = new ArrayList<>(originNode.getRoutesOut());
        Collections.sort(routes, (r1, r2) -> Integer.compare(r1.getPersonalAttrition(), r2.getPersonalAttrition()));

        return routes.isEmpty() ? null : routes.get(0);
    }

    public Route getBestRouteByDistance(String origin) {
        Node originNode = getNode(origin);
        if (originNode == null) {
            return null;
        }

        List<Route> routes = new ArrayList<>(originNode.getRoutesOut());
        Collections.sort(routes, (r1, r2) -> Integer.compare(r1.getDistance(), r2.getDistance()));

        return routes.isEmpty() ? null : routes.get(0);
    }

    public Route getFastestRoute(String origin, boolean isVehicle) {
        Node originNode = getNode(origin);
        if (originNode == null) {
            return null;
        }

        List<Route> routes = new ArrayList<>(originNode.getRoutesOut());

        if (isVehicle) {
            routes.removeIf(route -> route.getTimeCar() == 0); 
        } else {
            routes.removeIf(route -> route.getTimeWalk() == 0); 
        }

        Route fastestRoute = null;
        double bestScore = Double.MAX_VALUE; 
        for (Route route : routes) {
            double score = route.getDistance() / (double) (route.getTimeCar() + route.getTimeWalk());
            if (route.getTrafficProbability() != null) {
                for (Traffic traffic : route.getTrafficProbability()) {
                    score *= (1 + traffic.getTrafficProbability() / 100.0);
                }
            }
            if (score < bestScore) {
                bestScore = score;
                fastestRoute = route;
            }
        }
        return fastestRoute;
    }


    public Node getNode(String nodeName) {
        return nodes.get(nodeName);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }
    
}
