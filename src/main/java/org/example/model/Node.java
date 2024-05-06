package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private List<Route> routesOut;

    public Node(String name) {
        this.name = name;
        this.routesOut = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void addRouteOut(Route route){
        routesOut.add(route);
    }

    public List<Route> getRoutesOut(){
        return routesOut;
    }

}
