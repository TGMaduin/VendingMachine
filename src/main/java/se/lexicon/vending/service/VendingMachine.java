package se.lexicon.vending.service;

import se.lexicon.vending.model.Product;
import java.util.List;

public interface VendingMachine {

    boolean insertCoin(int coin);

    Product purchaseProduct(int id);

    int returnChange();

    int getBalance();

    List<Product> getProducts();
}