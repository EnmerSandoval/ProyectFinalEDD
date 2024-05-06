package org.example.model.graphviz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Graph;
import org.example.model.Node;
import org.example.model.Route;

public class GraphVizExporter {

    public void export(Graph graph, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("digraph Grafo {resolution = 110; \n");
            writer.println("randkir=LR;\n");
            for (Node node : graph.getNodes().values()) {
                writer.printf("  \"%s\";%n", node.getName());
            }

            for (Node node : graph.getNodes().values()) {
                for (Route route : node.getRoutesOut()) {
                    writer.printf("  \"%s\" -> \"%s\" ;%n",
                            node.getName(), route.getDestination().getName());
                }
            }

            writer.println("}");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    public void generateGraphvizFile(Route route, String fileName) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            writer.write("digraph G {\n");
//            List<Node> nodes = getAllNodesInRoute(route);
//            for (int i = 0; i < nodes.size() - 1; i++) {
//                Node currentNode = nodes.get(i);
//                Node nextNode = nodes.get(i + 1);
//                writer.write("    " + currentNode.getName() + " -> " + nextNode.getName() + ";\n");
//            }
//            writer.write("}");
//            System.out.println("Archivo DOT generado: " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
