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
        assertNotNull(foundItem);

        assertEquals(foundItem.getId(), 339800);
        assertEquals(foundItem.getName(), "Hagendas");
        assertEquals(foundItem.getItemDescription(), "strawberry");
        assertEquals(foundItem.getVat(), 0.2, 1e-9);
        assertEquals(foundItem.getPrice(), 70, 1e-9);
    }

    @Test
    public void findItemFailureTest(){
        ItemDTO foundItem = externalInventory.findItem(20210123);
        assertNull(foundItem);
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
        
        assertEquals(inventoryBefore.keySet(), inventoryAfter.keySet());

        for(ItemDTO itemDTO : inventoryBefore.keySet()) {
            if (itemDTO.equals(godis)) {
                assertEquals(
                    (int) inventoryBefore.get(itemDTO)-soldQuantity,
                    (int) inventoryAfter.get(itemDTO));
            } else {
                assertEquals(
                    (int) inventoryBefore.get(itemDTO),
                    (int) inventoryAfter.get(itemDTO));
            }
        }
    }
}
