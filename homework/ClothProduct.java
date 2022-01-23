package com.company.ws05;

public class ClothProduct {

    private int ID;
    private String name;
    private String info;
    private int price;
    private int stack;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        return "ClothProduct{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", price=" + price +
                ", stack=" + stack +
                '}';
    }

    public ClothProduct(int ID, String name, String info, int price, int stack) {
        this.ID = ID;
        this.name = name;
        this.info = info;
        this.price = price;
        this.stack = stack;



    }



    public static void main(String[] args) {
        ClothProduct c1 = new ClothProduct(23425, "니트", "터틀넥", 69000, 50);

        System.out.println(c1.toString());

    }
}
