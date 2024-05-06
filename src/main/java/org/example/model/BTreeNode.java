package org.example.model;

import java.util.ArrayList;
import java.util.List;

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
}
