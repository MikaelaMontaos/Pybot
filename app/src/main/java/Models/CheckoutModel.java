package Models;

public class CheckoutModel {

private int Quantity;
private String Name;
    private double Price;

    public CheckoutModel(int quantity, String name, double price) {
        Quantity = quantity;
        Name = name;
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }



}
