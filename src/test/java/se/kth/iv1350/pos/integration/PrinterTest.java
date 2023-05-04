package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.Receipt;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrinterTest {
    private Receipt receipt;
    private HashMap<ItemDTO, Integer> soldItems;
    private ExternalInventorySystem externalInventorySystem;
    private LocalDateTime dateTime;
    private Printer printer;

    @Before
    public void setUp(){
        double paidAmount = 200;
        double totalPrice = 160;
        dateTime = LocalDateTime.now();
        soldItems = new HashMap<ItemDTO, Integer>(); 
        externalInventorySystem = new ExternalInventorySystem();
        ItemDTO foundItem1 = externalInventorySystem.findItem(420101);
        ItemDTO foundItem2 = externalInventorySystem.findItem(520001);
        soldItems.put(foundItem1, 2);
        soldItems.put(foundItem2, 2);
        receipt = new Receipt(paidAmount, totalPrice, soldItems, null, dateTime);
        printer = new Printer();
    }

    @After
    public void tearDown(){
        receipt = null;
    }

    @Test
    public void testPrintReceipt(){
        String actualString = printer.printReceipt(receipt);
        String expectedString;
        expectedString = receipt.toString();
        assertEquals("printrEceipt() did not print the correct receipt.",expectedString, actualString);
        
    }
    
}
