package main.java.com.shoppingcart.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        // Check if item already exists
        int index = items.indexOf(item);
        if (index >= 0) {
            Item existingItem = items.get(index);
            int newQuantity = existingItem.getQuantity() + item.getQuantity();
            existingItem.setQuantity(newQuantity);
        } else {
            items.add(item);
        }
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public void updateQuantity(String itemName, int newQuantity) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.setQuantity(newQuantity);
                break;
            }
        }
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(Item::getTotal)
                .sum();
    }

    public String getFormattedTotal() {
        return NumberFormat.getCurrencyInstance().format(getTotal());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.size();
    }

    public void clearCart() {
        items.clear();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy for encapsulation
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
}