package se.lexicon.vending.model;

public class Beverage extends Product {
    private final int volumeMl;

    public Beverage(int id, String name, int price, int quantity, int volumeMl) {
        super(id, name, price, quantity);

        if (volumeMl <= 0) {
            throw new IllegalArgumentException("Volume must be greater than 0.");
        }

        this.volumeMl = volumeMl;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    @Override
    public String describe() {
        return getName() + " (Beverage, " + volumeMl + "ml)";
    }
}