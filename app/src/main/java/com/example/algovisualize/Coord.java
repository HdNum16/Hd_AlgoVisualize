package com.example.algovisualize;

public class Coord {
    private int x;
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Coord coord) {
        return this.x == coord.getX() && this.y == coord.getY();
    }

    public int ManhattanDistance(Coord coord) {
        return Math.abs(this.x - coord.getX()) + Math.abs(this.y - coord.getY());
    }

    public double EuclidDistance(Coord coord) {
        return Math.sqrt(Math.pow(this.x - coord.getX(), 2) + Math.pow(this.y - coord.getY(), 2));
    }
}
