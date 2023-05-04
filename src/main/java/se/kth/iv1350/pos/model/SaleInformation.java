package se.kth.iv1350.pos.model;

import java.util.HashMap;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.DiscountDTO;
import se.kth.iv1350.pos.integration.CustomerDTO;
import se.kth.iv1350.pos.integration.DiscountRegistry;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.integration.CustomerRegistry;


/**
* This is the SaleInformation class, containing methods of getting information of the current sale.
* It has discounts DiscountDTO[], double totalPrice, HashMap<ItemDTO, Integer> soldItems as attibutes.
*/ 
public class SaleInformation {
    private DiscountDTO[] discounts;
    private double totalPrice;
    private HashMap<ItemDTO, Integer> soldItems;
    private double totalPriceIncludeVat;

/**
* Create and initialize the saleInformation.
* Set the totalPrice = 0 when the sale starts and the discounts, soldItems are null in the beginning.
*/
    public SaleInformation(){
        discounts = new DiscountDTO[]{};
        totalPrice = 0;
        soldItems = new HashMap<ItemDTO, Integer>();
    }

/**
* Get totalPrice of all soldItems.
* @return totalPrice The double of the running total price.
*/
    public double getTotalPrice(){
        return totalPrice;
    }

/**
* Get vat of all soldItems.
* @return vatPrice The VAT double of running total price.
*/
    public double getVatPrice(){
        return totalPrice * 0.2;
    }


/**
* Get eligible discounts of the customer.
* @return discounts The DiscoutDTO[] which contains all eligible discounts of a customer.
*/
    public DiscountDTO[] getDiscount(){
        return discounts;
    }

/**
* Get the information of all sold items.
* @return soldItems The HashMap<ItemDTO, Integer> which contains all the entered items and their sold quantity for the current sale.
*/
    public HashMap<ItemDTO, Integer> getSoldItems(){
        return new HashMap<ItemDTO, Integer>(soldItems);
    }

/**
* Add the item to the current sale.
* If the item is invalid, it will not be entered in.
* If the item is valid(can be found in inventory), the itemDTO will be added in the soldItems.
* If the item is already existed on soldItems list, the quantity of the sold item will be added
* with the alreadySoldQuantity and the newEnteredQuantity.
* @return soldItems The HashMap<ItemDTO, Integer> which contains all the entered items and their sold quantity.
*/
    public void addItem(int identifier, int quantity, ExternalInventorySystem externalInventorySystem){
        ItemDTO toBeAddedItem = externalInventorySystem.findItem(identifier);
        if (toBeAddedItem == null) {
            return;
        }
        int alreadySoldQuantity = soldItems.getOrDefault(toBeAddedItem, 0);
        soldItems.put(toBeAddedItem, alreadySoldQuantity+quantity);
    }

/**
* Find and apply discount to totalPrice.
* @param customerId The int which will be entered for getting eligible discounts of the customer.
* @return totalPrice The double of total price after that eligible discounts are applied.
*/ 
    public double includeDiscount(int customerID, DiscountRegistry discountRegistry, CustomerRegistry customerRegistry){
        CustomerDTO customerDTO = customerRegistry.findCustomerById(customerID);
        discounts = discountRegistry.findDiscount(customerDTO);
        uppdateSaleInformation();
        return totalPrice;
    }

/**
* Uppdate the saleInformation of the current sale.
* Add price of all entered items.
* Add discount to the total price.
*/
    public void uppdateSaleInformation(){
        totalPrice = 0;
        for(ItemDTO solditem : soldItems.keySet()){
            totalPrice += soldItems.get(solditem) * solditem.getPrice();
        }
        for (DiscountDTO discount : discounts){
            totalPrice *= discount.getType();
        }
    }
}
