package se.lexicon.vending.ui;

import se.lexicon.vending.model.Product;
import se.lexicon.vending.service.VendingMachine;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final VendingMachine machine;
    private final Scanner scanner;

    public ConsoleUI(VendingMachine machine) {
        this.machine = machine;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        System.out.println("Welcome to Lexicon Vending Machine");

        while (running) {
            displayProducts();
            displayBalance();
            displayMenu();

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> handleInsertCoin();
                case 2 -> handlePurchase();
                case 3 -> handleReturnChange();
                case 0 -> {
                    handleExit();
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }

            System.out.println();
        }
    }

    private void displayProducts() {
        System.out.println("------------------------------------");

        List<Product> products = machine.getProducts();

        for (Product product : products) {
            System.out.printf(
                    "[%d] %-12s - %d kr  %-25s Stock: %d%n",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.describe(),
                    product.getQuantity()
            );
        }

        System.out.println("------------------------------------");
    }

    private void displayMenu() {
        System.out.println("[1] Insert coin");
        System.out.println("[2] Select product");
        System.out.println("[3] Return change");
        System.out.println("[0] Exit");
    }

    private void displayBalance() {
        System.out.println("Balance: " + machine.getBalance() + " kr");
    }

    private void handleInsertCoin() {
        int coin = readInt("Insert coin: ");

        boolean accepted = machine.insertCoin(coin);

        if (accepted) {
            System.out.println("Balance: " + machine.getBalance() + " kr");
        } else {
            System.out.println("Invalid coin. Only 1, 2, 5, 10, 20, 50 kr accepted.");
            System.out.println("Balance: " + machine.getBalance() + " kr");
        }
    }

    private void handlePurchase() {
        int id = readInt("Select product: ");

        Product selectedProduct = findProductById(id);

        if (selectedProduct == null) {
            System.out.println("Product does not exist.");
            System.out.println("Balance: " + machine.getBalance() + " kr");
            return;
        }

        int balanceBeforePurchase = machine.getBalance();
        Product purchasedProduct = machine.purchaseProduct(id);

        if (purchasedProduct == null) {
            if (!selectedProduct.isInStock()) {
                System.out.println(selectedProduct.getName() + " is out of stock.");
            } else if (balanceBeforePurchase < selectedProduct.getPrice()) {
                int missing = selectedProduct.getPrice() - balanceBeforePurchase;
                System.out.println("Insufficient balance. Missing " + missing + " kr.");
            } else {
                System.out.println("Purchase failed.");
            }

            System.out.println("Balance: " + machine.getBalance() + " kr");
            return;
        }

        System.out.println("Dispensing: " + purchasedProduct.describe());

        int change = machine.returnChange();

        if (change > 0) {
            System.out.println("Change returned: " + change + " kr");
        }

        System.out.println("Balance: " + machine.getBalance() + " kr");
    }

    private void handleReturnChange() {
        int change = machine.returnChange();

        if (change > 0) {
            System.out.println("Change returned: " + change + " kr");
        } else {
            System.out.println("No balance to return.");
        }

        System.out.println("Balance: " + machine.getBalance() + " kr");
    }

    private void handleExit() {
        int change = machine.returnChange();

        if (change > 0) {
            System.out.println("Change returned: " + change + " kr");
        }

        System.out.println("Goodbye.");
    }

    private Product findProductById(int id) {
        for (Product product : machine.getProducts()) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print("> " + prompt);

            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                scanner.nextLine();
                return number;
            }

            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }
    }
}