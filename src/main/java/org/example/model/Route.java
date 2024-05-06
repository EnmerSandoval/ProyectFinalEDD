package org.example.model;

import java.util.List;

public class Route  implements Comparable<Route> {
    private Node origin;
    private Node destination;
    private int timeCar;
    private int timeWalk;
    private int gasolineExpensive;
    private int personalAttrition;
    private List<Traffic> trafficProbability;
    private int distance;

    public Route(Node origin, Node destination, int timeCar, int timeWalk, int gasolineExpensive, int personalAttrition, int distance) {
        this.origin = origin;
        this.destination = destination;
        this.timeCar = timeCar;
        this.timeWalk = timeWalk;
        this.personalAttrition = personalAttrition;
        this.gasolineExpensive = gasolineExpensive;
        this.distance = distance;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    public int getTimeCar() {
        return timeCar;
    }

    public int getTimeWalk() {
        return timeWalk;
    }

    public int getGasolineExpensive() {
        return gasolineExpensive;
    }

    public int getDistance() {
        return distance;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public void setTimeCar(int timeCar) {
        this.timeCar = timeCar;
    }

    public void setTimeWalk(int timeWalk) {
        this.timeWalk = timeWalk;
    }

    public void setGasolineExpensive(int gasolineExpensive) {
        this.gasolineExpensive = gasolineExpensive;
    }


    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Route other) {
        return Integer.compare(this.distance, other.distance);
    }

    public int compareOriginAndDestination(String origin, String destination){
        int originComparison = this.origin.getName().compareTo(origin);
        if(originComparison != 0){
            return originComparison;
        }
        return this.destination.getName().compareTo(destination);
    }

    public int getPersonalAttrition() {
        return personalAttrition;
    }

    public void setPersonalAttrition(int personalAttrition) {
        this.personalAttrition = personalAttrition;
    }

    public List<Traffic> getTrafficProbability() {
        return trafficProbability;
    }

    public void setTrafficProbability(List<Traffic> trafficProbability) {
        this.trafficProbability = trafficProbability;
    }

    
    
}
