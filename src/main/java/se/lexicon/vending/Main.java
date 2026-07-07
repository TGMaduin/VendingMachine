package se.lexicon.vending;

import se.lexicon.vending.service.VendingMachine;
import se.lexicon.vending.service.VendingMachineService;
import se.lexicon.vending.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachineService();
        ConsoleUI ui = new ConsoleUI(machine);

        ui.start();
    }
}