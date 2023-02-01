package com.ZiyanGuo;

public class Data {
    private int[] pixels = new int[784];
    int label;
    double distance;

    public Data(int[] pixels, int label) {
        this.pixels = pixels;
        this.label = label;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
