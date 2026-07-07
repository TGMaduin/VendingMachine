package se.lexicon.vending.model;

public abstract class Product {
    private final int id;
    private final String name;
    private final int price;
    private int quantity;

    public Product(int id, String name, int price, int quantity) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isInStock() {
        return quantity > 0;
    }

    public void reduceQuantity() {
        if (quantity <= 0) {
            throw new IllegalStateException("Product is out of stock.");
        }

        quantity--;
    }

    public abstract String describe();
}