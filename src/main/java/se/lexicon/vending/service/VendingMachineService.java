package se.lexicon.vending.service;

import se.lexicon.vending.model.*;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineService implements VendingMachine {

    private final List<Product> products;
    private int balance;

    private static final int[] VALID_COINS = {1, 2, 5, 10, 20, 50};

    public VendingMachineService() {
        products = new ArrayList<>();

        // Initial products
        products.add(new Snack(1, "Chips", 15, 5, 130));
        products.add(new Beverage(2, "Cola", 20, 3, 330));
        products.add(new Fruit(3, "Apple", 10, 8, "Sweden"));
    }

    @Override
    public boolean insertCoin(int coin) {

        for (int validCoin : VALID_COINS) {
            if (coin == validCoin) {
                balance += coin;
                return true;
            }
        }

        return false;
    }

    @Override
    public Product purchaseProduct(int id) {

        Product product = findProduct(id);

        if (product == null) {
            return null;
        }

        if (!product.isInStock()) {
            return null;
        }

        if (balance < product.getPrice()) {
            return null;
        }

        balance -= product.getPrice();
        product.reduceQuantity();

        // Automatically return remaining change
        returnChange();

        return product;
    }

    @Override
    public int returnChange() {

        int change = balance;
        balance = 0;
        return change;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    private Product findProduct(int id) {

        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }
}