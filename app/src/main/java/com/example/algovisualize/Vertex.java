package com.example.algovisualize;

import java.util.HashMap;
import java.util.Set;

public class Vertex {
    private Coord coord;
    private HashMap<Vertex, Integer> hmEdge;
    private int Status;
    private int NewWeight;
    private boolean Checked_flag;
    private boolean ShortestPath_flag;
    private Vertex Parent;

    private int Dist_G; // Dist_G is used for A-star algorithm to store G. F is stored in NewWeight.

    public Vertex(Coord coord) {
        this.coord = coord;
        hmEdge = new HashMap<>();
        NewWeight = 0;
        Dist_G = 1;
        Status = GlobalVar.IKERU;
        Checked_flag = false;
        ShortestPath_flag = false;
        Parent = null;
    }

    public void setChecked_flag(boolean checked_flag) {
        Checked_flag = checked_flag;
    }
    public boolean isChecked_flag() {
        return Checked_flag;
    }

    public void setShortestPath_flag(boolean shortestPath_flag) {
        ShortestPath_flag = shortestPath_flag;
    }
    public boolean isShortestPath_flag() {
        return ShortestPath_flag;
    }

    public Vertex getParent() {
        return Parent;
    }
    public void setParent(Vertex parent) {
        Parent = parent;
    }

    public void setStatus(int status) {
        Status = status;
    }
    public int getStatus() {
        return Status;
    }

    public int getNewWeight() {
        return NewWeight;
    }
    public void setNewWeight(int newWeight) {
        NewWeight = newWeight;
    }

    public void setDist_G(int dist_G) {
        Dist_G = dist_G;
    }
    public int getDist_G() {
        return Dist_G;
    }

    public void addHmEdge(Vertex vertex) {
        hmEdge.put(vertex, 1);
    }

    public void addHmEdge(Vertex vertex, int weight) {
        hmEdge.put(vertex, weight);
    }

    public Set<Vertex> getEdge() {
        return hmEdge.keySet();
    }

    public int getWeight(Vertex vertex) {
        return hmEdge.get(vertex);
    }

    public void deleteEdge(Vertex vertex) {
        hmEdge.remove(vertex);
    }

    public Coord getCoord() {
        return coord;
    }
}
