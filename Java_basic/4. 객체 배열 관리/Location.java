package com.company.ws01;

public class Location {
    int axisX;


    int axisY;


    public Location(int axisX, int axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
    }

    public String getInfo() {
        return (axisX + "," + axisY);
    }

    public static void main(String[] args) {
        Location A = new Location(5,3);
        System.out.println(A.getInfo());

    }
}
