package se.kth.iv1350.pos.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

public class ExternalInventorySystemTest {
    private ExternalInventorySystem externalInventory;

    @Before
    public void setUp(){
        externalInventory = new ExternalInventorySystem();
    }

    @After
    public void tearDown(){
        externalInventory = null;
    }

    @Test
    public void findItemSuccessfulTest(){
        ItemDTO foundItem = externalInventory.findItem(339800);
        assertNotNull("findItem() returned null when a valid item is entered", 
                    foundItem);
        assertEquals("findItem() returned wrong Id of item", 
                    foundItem.getId(), 339800);
        assertEquals("findItem() returned wrong name of item", 
                    foundItem.getName(), "Hagendas");
        assertEquals("findItem() returned wrong item description of item", 
                    foundItem.getItemDescription(), "strawberry");
        assertEquals("findItem() returned wrong vat of item", 
                    foundItem.getVat(), 0.2, 1e-9);
        assertEquals("findItem() returned wrong price of item", 
                    foundItem.getPrice(), 70, 1e-9);
    }

    @Test
    public void findItemFailureTest(){
        ItemDTO foundItem = externalInventory.findItem(20210123);
        assertNull("findItem() did not return null when an invalid item is entered", foundItem);
    }

    @Test
    public void uppdateInventoryTest(){
        ItemDTO godis = new ItemDTO(420101, "godis", "bästa", 0.2, 50);
        int soldQuantity = 2;

        HashMap<ItemDTO, Integer> inventoryBefore = externalInventory.getInventory();
        HashMap<ItemDTO, Integer> soldItems = new HashMap<ItemDTO, Integer>();
        soldItems.put(godis, soldQuantity);
        externalInventory.uppdateInventory(soldItems);
        HashMap<ItemDTO, Integer> inventoryAfter = externalInventory.getInventory();
        
        assertEquals("uppdateInventory() changed the items in inventory after the uppdating", 
                    inventoryBefore.keySet(), inventoryAfter.keySet());

        for(ItemDTO itemDTO : inventoryBefore.keySet()) {
            if (itemDTO.equals(godis)) {
                assertEquals(
                    "uppdateInventorySystem() did not decrease the inventory when item is sold out",
                    (int) inventoryBefore.get(itemDTO)-soldQuantity,
                    (int) inventoryAfter.get(itemDTO));
            } else {
                assertEquals(
                    "uppdateInventorySystem() changed the quantity of an unsolditem",
                    (int) inventoryBefore.get(itemDTO),
                    (int) inventoryAfter.get(itemDTO));
            }
        }
    }
}
