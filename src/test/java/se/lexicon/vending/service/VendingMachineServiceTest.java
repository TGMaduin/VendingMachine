package se.lexicon.vending.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.vending.model.Product;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceTest {

    private VendingMachineService machine;

    @BeforeEach
    void setUp() {
        machine = new VendingMachineService();
    }

    @Test
    void insertValidCoin_shouldIncreaseBalance() {
        machine.insertCoin(10);

        assertEquals(10, machine.getBalance());
    }

    @Test
    void insertInvalidCoin_shouldNotChangeBalance() {
        machine.insertCoin(7);

        assertEquals(0, machine.getBalance());
    }

    @Test
    void purchaseProduct_shouldSucceed() {
        machine.insertCoin(20);

        Product product = machine.purchaseProduct(2);

        assertNotNull(product);
        assertEquals("Cola", product.getName());
        assertEquals(2, product.getQuantity());
        assertEquals(0, machine.getBalance());
    }

    @Test
    void purchaseProduct_shouldFailWhenBalanceIsTooLow() {
        machine.insertCoin(10);

        Product product = machine.purchaseProduct(2);

        assertNull(product);
        assertEquals(10, machine.getBalance());

        Product cola = machine.getProducts().get(1);
        assertEquals(3, cola.getQuantity());
    }

    @Test
    void purchaseProduct_shouldFailWhenOutOfStock() {
        Product chips = machine.getProducts().get(0);

        while (chips.getQuantity() > 0) {
            machine.insertCoin(20);
            machine.purchaseProduct(1);
            machine.returnChange();
        }

        machine.insertCoin(20);

        Product product = machine.purchaseProduct(1);

        assertNull(product);
        assertEquals(0, chips.getQuantity());
        assertEquals(20, machine.getBalance());
    }

    @Test
    void returnChange_shouldReturnBalanceAndResetToZero() {
        machine.insertCoin(50);

        int change = machine.returnChange();

        assertEquals(50, change);
        assertEquals(0, machine.getBalance());
    }

    @Test
    void getProducts_shouldReturnThreeProducts() {
        assertEquals(3, machine.getProducts().size());
    }
}