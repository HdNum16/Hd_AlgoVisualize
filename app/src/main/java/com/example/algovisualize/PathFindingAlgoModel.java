package com.example.algovisualize;

import android.util.Log;

import java.util.ArrayList;

public class PathFindingAlgoModel {
    private int NUM_OF_CELLS_WIDTH, NUM_OF_CELLS_HEIGHT;
    private Vertex[][] pGraph;
    private ArrayList<PathFindingAlgoModelListener> listeners;

    public PathFindingAlgoModel(int NUM_OF_CELLS_WIDTH, int NUM_OF_CELLS_HEIGHT) {
        this.NUM_OF_CELLS_WIDTH = NUM_OF_CELLS_WIDTH;
        this.NUM_OF_CELLS_HEIGHT = NUM_OF_CELLS_HEIGHT;
        pGraph = new Vertex[NUM_OF_CELLS_HEIGHT][NUM_OF_CELLS_WIDTH];
        for (int row = 0; row < NUM_OF_CELLS_HEIGHT; row++) {
            for (int col = 0; col < NUM_OF_CELLS_WIDTH; col++) {
                pGraph[row][col] = new Vertex(new Coord(col, row));
            }
        }
        for (int row = 0; row < NUM_OF_CELLS_HEIGHT; row++) {
            for (int col = 0; col < NUM_OF_CELLS_WIDTH; col++) {
//                if (row > 0 && col > 0) pGraph[row][col].addHmEdge(pGraph[row-1][col-1], 20);
//                if (row > 0 && col < NUM_OF_CELLS_WIDTH-1) pGraph[row][col].addHmEdge(pGraph[row-1][col+1], 20);
//                if (row < NUM_OF_CELLS_HEIGHT-1 && col > 0) pGraph[row][col].addHmEdge(pGraph[row+1][col-1], 20);
//                if (row < NUM_OF_CELLS_HEIGHT-1 && col < NUM_OF_CELLS_WIDTH-1) pGraph[row][col].addHmEdge(pGraph[row+1][col+1], 20);

                if (row > 0) pGraph[row][col].addHmEdge(pGraph[row-1][col]);
                if (row < NUM_OF_CELLS_HEIGHT-1) pGraph[row][col].addHmEdge(pGraph[row+1][col]);
                if (col > 0) pGraph[row][col].addHmEdge(pGraph[row][col-1]);
                if (col < NUM_OF_CELLS_WIDTH-1) pGraph[row][col].addHmEdge(pGraph[row][col+1]);
            }
        }
        listeners = new ArrayList<>();
    }

    public Vertex[][] getpGraph() {
        return pGraph;
    }

    public int getWIDTH() {
        return NUM_OF_CELLS_WIDTH;
    }

    public int getHEIGHT() {
        return NUM_OF_CELLS_HEIGHT;
    }

    public void initPathFindingAlgo() {
        for (int row = 0; row < NUM_OF_CELLS_HEIGHT; row++) {
            for (int col = 0; col < NUM_OF_CELLS_WIDTH; col++) {
                pGraph[row][col].setNewWeight(Integer.MAX_VALUE);
                pGraph[row][col].setChecked_flag(false);
                pGraph[row][col].setShortestPath_flag(false);
            }
        }
        pGraph[GlobalVar.DEFAULT_S.getY()][GlobalVar.DEFAULT_S.getX()].setNewWeight(0);
    }

    public int PathFindingAlgo_1Step(String algo_name) {
        if (algo_name.compareTo("Dijsktra") == 0) return DijsktraAlgo_1Step();
        else if (algo_name.compareTo("A star") == 0) return AstarAlgo_1Step();
        else if (algo_name.compareTo("Greedy Best first") == 0) return Greedy_Best_First_Algo_1Step();
        return -1;
    }

    public Coord minDistance() {
        int min = Integer.MAX_VALUE;
        Coord min_index = new Coord(-1,-1);

        for (int row = 0; row < NUM_OF_CELLS_HEIGHT; row++) {
            for (int col = 0; col < NUM_OF_CELLS_WIDTH; col++) {
                if (!pGraph[row][col].isChecked_flag() && pGraph[row][col].getStatus() != GlobalVar.IKENAI && pGraph[row][col].getNewWeight() < min) {
                    min = pGraph[row][col].getNewWeight();
                    min_index.setCoord(col, row);
                }
            }
        }
        return min_index;
    }

    public int DijsktraAlgo_1Step() {
        Coord u = minDistance();
        if (u.getY() < 0 || u.getX() < 0) return 2;
        for (Vertex vertex: pGraph[u.getY()][u.getX()].getEdge()) {
            if (!vertex.isChecked_flag() && pGraph[u.getY()][u.getX()].getNewWeight() + pGraph[u.getY()][u.getX()].getWeight(vertex) < vertex.getNewWeight()) { // 1 cho nay ne
                vertex.setNewWeight(pGraph[u.getY()][u.getX()].getNewWeight() + 1);
                vertex.setParent(pGraph[u.getY()][u.getX()]);
                if (vertex.getCoord().equals(GlobalVar.DEFAULT_T)) return 1;
            }
        }
        pGraph[u.getY()][u.getX()].setChecked_flag(true);
        fireUpdate();
        return 0;
    }

    public void PathFindingAlgo_getShortestPath() {
        Vertex cur = pGraph[GlobalVar.DEFAULT_T.getY()][GlobalVar.DEFAULT_T.getX()];
        while (!cur.getCoord().equals(pGraph[GlobalVar.DEFAULT_S.getY()][GlobalVar.DEFAULT_S.getX()].getCoord())) {
            cur.setShortestPath_flag(true);
            cur = cur.getParent();
        }
        fireUpdate();
    }

    public Coord minDistanceAstar() {
        int min = Integer.MAX_VALUE;
        Coord min_index = new Coord(-1,-1);

        for (int row = 0; row < NUM_OF_CELLS_HEIGHT; row++) {
            for (int col = 0; col < NUM_OF_CELLS_WIDTH; col++) {
                if (!pGraph[row][col].isChecked_flag() && pGraph[row][col].getStatus() != GlobalVar.IKENAI && pGraph[row][col].getNewWeight() != Integer.MAX_VALUE && pGraph[row][col].getNewWeight() <= min) {
                    if (pGraph[row][col].getNewWeight() == min) {
                        int g = pGraph[row][col].getDist_G();
                        int h = pGraph[row][col].getNewWeight() - g;
                        int g_min = pGraph[min_index.getY()][min_index.getX()].getDist_G();
                        int h_min = pGraph[min_index.getY()][min_index.getX()].getNewWeight() - g_min;

                        if ((double) h / g < (double) h_min / g_min) {
                            min = pGraph[row][col].getNewWeight();
                            min_index.setCoord(col, row);
                        }
                    }
                    else {
                        min = pGraph[row][col].getNewWeight();
                        min_index.setCoord(col, row);
                    }
                }
            }
        }
        return min_index;
    }

    public int AstarAlgo_1Step() {
        Coord u = minDistanceAstar();
        if (u.getY() < 0 || u.getX() < 0) return 2;
        for (Vertex vertex: pGraph[u.getY()][u.getX()].getEdge()) {
            int g = pGraph[u.getY()][u.getX()].getDist_G() + pGraph[u.getY()][u.getX()].getWeight(vertex); // 1 cho nay ne
            int h = vertex.getCoord().ManhattanDistance(GlobalVar.DEFAULT_T);

            if (!vertex.isChecked_flag() && h + g < vertex.getNewWeight()) {
                vertex.setDist_G(g);
                vertex.setNewWeight(g + h);
                vertex.setParent(pGraph[u.getY()][u.getX()]);
                if (vertex.getCoord().equals(GlobalVar.DEFAULT_T)) return 1;
            }
        }
        pGraph[u.getY()][u.getX()].setChecked_flag(true);
        fireUpdate();
        return 0;
    }

    public int Greedy_Best_First_Algo_1Step() {
        Coord u = minDistanceAstar();
        if (u.getY() < 0 || u.getX() < 0) return 2;
        for (Vertex vertex: pGraph[u.getY()][u.getX()].getEdge()) {
            int h = vertex.getCoord().ManhattanDistance(GlobalVar.DEFAULT_T);

            if (!vertex.isChecked_flag() && h < vertex.getNewWeight()) {
                vertex.setNewWeight(h);
                vertex.setParent(pGraph[u.getY()][u.getX()]);
                if (vertex.getCoord().equals(GlobalVar.DEFAULT_T)) return 1;
            }
        }
        pGraph[u.getY()][u.getX()].setChecked_flag(true);
        fireUpdate();
        return 0;
    }

    public void addListener(PathFindingAlgoModelListener listener) {
        listeners.add(listener);
    }

    public void fireUpdate() {
        for (PathFindingAlgoModelListener listener: listeners) {
            listener.updated(this);
        }
    }
}
