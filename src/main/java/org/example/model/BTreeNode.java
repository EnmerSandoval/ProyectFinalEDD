package org.example.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BTreeNode {

    private List<Route> routes;
    private List<BTreeNode> children;
    private boolean isLeaf;
    private int degree;
    private int n;

    public BTreeNode(int degree, boolean isLeaf) {
        this.degree = degree;
        this.isLeaf = isLeaf;
        this.routes = new ArrayList<>();
        this.children = new ArrayList<>();
        this.n = 0;
    }

    public void insertRoute(Route route) {
        if (isLeaf) {
            insertIntoLeaf(route);
        } else {
            insertIntoNonLeaf(route);
        }
    }

    private void insertIntoLeaf(Route route) {
        int i = 0;
        while (i < n && routes.get(i).compareTo(route) < 0) {
            i++;
        }
        routes.add(i, route);
        n++;
    }

    private void insertIntoNonLeaf(Route route) {
        int i = 0;
        while (i < n && routes.get(i).compareTo(route) < 0) {
            i++;
        }

        if (i == children.size() || children.get(i).n == 2 * degree - 1) {
            splitChild(i);
            if (i < n && routes.get(i).compareTo(route) < 0) {
                i++;
            }
        }

        if (i < n && routes.get(i).compareTo(route) < 0) {
            i++;
        }

        if (i == children.size()) {
            children.add(new BTreeNode(degree, true));
        }

        children.get(i).insertRoute(route);
    }

    private void splitChild(int index) {
        BTreeNode y = children.get(index);
        BTreeNode z = new BTreeNode(y.degree, y.isLeaf);
        z.n = degree - 1;

        for (int j = 0; j < degree - 1; j++) {
            z.routes.add(y.routes.remove(degree));
        }

        if (!y.isLeaf) {
            for (int j = 0; j < degree; j++) {
                z.children.add(y.children.remove(degree));
            }
        }

        routes.add(index, y.routes.remove(degree - 1));
        children.add(index + 1, z);

        n--;
    }

    public Route searchRoute(String origin, String destination) {
        int i = 0;
        while (i < n) {
            Route route = routes.get(i);
            if (route.getOrigin().getName().equals(origin) && route.getDestination().getName().equals(destination)) {
                return route;
            } else if (!isLeaf) {
                if (routes.get(i).compareTo(route) > 0 || i == n - 1) {
                    return children.get(i).searchRoute(origin, destination);
                }
            }
            i++;
        }
        return null;
    }

    public int obtenerMetricaDeRuta(String origen, String destino, String tipoMetrica, String type) {
        Set<String> visitados = new HashSet<>();
        List<String> ruta = new ArrayList<>();
        int metrica = obtenerMetricaDFS(origen, destino, visitados, tipoMetrica, type, ruta);
        generarArchivoDOT(ruta);
        return metrica;
    }

    private int obtenerMetricaDFS(String actual, String destino, Set<String> visitados, String tipoMetrica, String type, List<String> ruta) {
        visitados.add(actual);
        ruta.add(actual);

        if (actual.equals(destino)) {
            return 0;
        }

        for (int i = 0; i < n; i++) {
            String nodoAdyacente = routes.get(i).getDestination().getName();
            if (!visitados.contains(nodoAdyacente)) {
                int metricaRuta = obtenerMetricaDFS(nodoAdyacente, destino, visitados, tipoMetrica, type, ruta);
                if (metricaRuta != -1) {
                    ruta.add(nodoAdyacente);
                    if (tipoMetrica.equals("distancia")) {
                        return metricaRuta + routes.get(i).getDistance();
                    } else if (tipoMetrica.equals("consumo")) {
                        return metricaRuta + routes.get(i).getGasolineExpensive();
                    } else if (tipoMetrica.equals("tiempo") && type.equals("CAMINANDO")) {
                        return metricaRuta + routes.get(i).getGasolineExpensive();
                    } else if (tipoMetrica.equals("tiempo") && type.equals("VEHICULO")) {
                        return metricaRuta + routes.get(i).getTimeCar();
                    } else if (tipoMetrica.equals("desgaste")) {
                        return metricaRuta + routes.get(i).getPersonalAttrition();
                    }
                }
            }
        }

        return -1;
    }

    private void generarArchivoDOT(List<String> ruta) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("ruta.dot"))) {
            writer.println("digraph G {resolution = 60;");
            writer.println("    rankdir=LR;");

            for (int i = 0; i < ruta.size() - 1; i++) {
                writer.println("    " + ruta.get(i) + " -> " + ruta.get(i + 1));
            }

            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
