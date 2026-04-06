package main.java.com.shoppingcart.model;

import java.io.Serializable;
import java.text.NumberFormat;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    public double getTotal() {
        return price * quantity;
    }

    public String getFormattedPrice() {
        return NumberFormat.getCurrencyInstance().format(price);
    }

    public String getFormattedTotal() {
        return NumberFormat.getCurrencyInstance().format(getTotal());
    }

    @Override
    public String toString() {
        return String.format("%s - %d x %s = %s",
                name, quantity, getFormattedPrice(), getFormattedTotal());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return name.equalsIgnoreCase(item.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}