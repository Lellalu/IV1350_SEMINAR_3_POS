package se.kth.iv1350.pos.integration;

import java.util.HashMap;


/**
* This is the ExternalInventorySystem class in the integration. 
* It cantains information of all valid itemDTOs, including Int identifier, 
* String name, String itenDescription, double Vat, double price and their corresponding inventory quantity.
* The ExternalInventorySystem class is used for finding valid items in a sale process.
* The ExternalInventorySystem will be uppdated after ending the sale.
*/
public class ExternalInventorySystem {
    private HashMap<ItemDTO, Integer> inventory;

/**
* Create the ExternalInventorySystem, including some faked database of inventory.
* The externalInventorySystem contains a HashMap with ItemDTO as key and quantity as value.
*/
    public ExternalInventorySystem(){
        ItemDTO godis = new ItemDTO(420101, "godis", "bästa", 0.2, 50);
        ItemDTO chips = new ItemDTO(520001, "OLW chips", "250g", 0.2, 30);
        ItemDTO glass = new ItemDTO(339800, "Hagendas", "strawberry", 0.2, 70);
        ItemDTO cola = new ItemDTO(778020, "Cola", "500ml", 0.2, 15);
        
        inventory = new HashMap<ItemDTO, Integer>();
        inventory.put(godis, 10);
        inventory.put(chips, 20);
        inventory.put(glass, 30);
        inventory.put(cola, 40);
    }

/**
* Get a copied inventory system.
* @return copied_inventory The HashMap<ItemDTO,Integer> External InventorySystem for doing the test and ensure safety of the privtate inventory database.
*/
    public HashMap<ItemDTO,Integer> getInventory() {
        HashMap<ItemDTO,Integer> copied_inventory = new HashMap<ItemDTO,Integer>(inventory);
        return copied_inventory;
    }

/**
* Find the item by itemId.
* @return itemDTO The founded itemDTO if the items is valid.
* @return null The invalidation if the items is invalid.
*/
    public ItemDTO findItem(int identifier){
        for (ItemDTO itemDTO : inventory.keySet())
        {
            if(itemDTO.getId() == identifier) {
                return itemDTO;
            }
        }
        return null;
    }

/**
* Uppdate the inventory system by sold items.
* The inventory quantity will be changed according to the sold item quantity in the soldItems HashMap.
*/
    public void uppdateInventory(HashMap<ItemDTO, Integer>  sold_items){
        int sold_quantity;
        int inventory_quantity;
        for(ItemDTO sold_itemDTO : sold_items.keySet()) {
            sold_quantity = sold_items.get(sold_itemDTO);
            inventory_quantity = inventory.get(sold_itemDTO);
            inventory.replace(sold_itemDTO, inventory_quantity-sold_quantity);
        }
    }


}
