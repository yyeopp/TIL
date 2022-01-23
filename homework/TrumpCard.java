package com.company.ws04;

public class TrumpCard {

    static String [] shape = {"♠", "◆", "♥", "♣"};
    static String [] number = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    static String [][] trump = new String [shape.length][number.length];



    public static void main(String[] args) {

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < number.length; j++) {
                trump[i][j] = shape[i]+number[j];
            }
        }

        for (int i = 0; i < trump.length; i++) {
            for (int j = 0; j < trump[i].length; j++) {
                System.out.print(trump[i][j]+", ");
            }
            System.out.println();
        }


    }
}
