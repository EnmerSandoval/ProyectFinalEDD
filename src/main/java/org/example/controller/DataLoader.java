package org.example.controller;

import org.example.model.Graph;
import org.example.model.Node;
import org.example.model.Route;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.example.model.Traffic;

public class DataLoader {

    public void loadRoutes(Graph graph, String routesFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(routesFilePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split("\\|");
                if (data.length >= 7) {
                    String origin = data[0];
                    String destination = data[1];
                    int timeCar = Integer.parseInt(data[2]);
                    int timeWalk = Integer.parseInt(data[3]);
                    int gasolineExpensive = Integer.parseInt(data[4]);
                    int personalAttrition = Integer.parseInt(data[5]);
                    int distance = Integer.parseInt(data[6]);

                    Node nodeOrigin = graph.getNode(origin);
                    Node nodeDestination = graph.getNode(destination);
                    if (nodeOrigin == null) {
                        nodeOrigin = new Node(origin);
                        graph.addNode(nodeOrigin);
                    }
                    if (nodeDestination == null) {
                        nodeDestination = new Node(destination);
                        graph.addNode(nodeDestination);
                    }

                    graph.addRoute(origin, destination, timeCar, timeWalk, gasolineExpensive, personalAttrition, distance);
                } else {
                    System.err.println("Error: La línea no contiene suficientes partes para procesar.");
                    System.err.println("Contenido de la línea " + lineNumber + ": " + line);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public void loadTraffic(Graph graph, String trafficFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(trafficFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 5) {
                    String origin = data[0];
                    String destination = data[1];
                    int startTime = Integer.parseInt(data[2]);
                    int endTime = Integer.parseInt(data[3]);
                    int probabilityTraffic = Integer.parseInt(data[4]);

                    Node originNode = graph.getNode(origin);
                    Node destinationNode = graph.getNode(destination);
                    if (originNode != null && destinationNode != null) {
                        for (Route route : originNode.getRoutesOut()) {
                            if (route.getDestination().equals(destinationNode)) {
                                if (route.getTrafficProbability() == null) {
                                    route.setTrafficProbability(new ArrayList<>());
                                }
                                route.getTrafficProbability().add(new Traffic(startTime, endTime, probabilityTraffic));
                                break;
                            }
                        }
                    } else {
                        System.out.println("No se encontró la ruta para " + origin + " a " + destination);
                    }
                } else {
                    System.err.println("Error: La línea no contiene suficientes partes para procesar.");
                    System.err.println("Contenido de la línea: " + line);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

}
