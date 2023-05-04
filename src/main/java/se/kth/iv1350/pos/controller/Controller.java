package se.kth.iv1350.pos.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.DiscountRegistry;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.integration.AccountingSystem;
import se.kth.iv1350.pos.integration.CustomerRegistry;
import se.kth.iv1350.pos.integration.RegisterCreator;
import se.kth.iv1350.pos.model.SaleInformation;
import se.kth.iv1350.pos.integration.Printer;
import se.kth.iv1350.pos.model.Receipt;

public class Controller {
    private SaleInformation saleInformation;
    private ExternalInventorySystem externalInventorySystem;
    private DiscountRegistry discountRegistry;
    private CustomerRegistry customerRegistry;
    private AccountingSystem accountingSystem;
    private Printer printer;

/**
* This is the only Controller class in the program, calling all the other methods in model and integration.
* @param registerCreator This object contains the methods to create new externalInventorySystem, discountRegistry, customerRegistry in controller.
* @param printer The object Printer which contains the method to print receipt out will be used.
*/
    public Controller (RegisterCreator registerCreator, Printer printer){
        this.externalInventorySystem = registerCreator.getItemRegistry();
        this.discountRegistry = registerCreator.getDiscountRegistry();
        this.customerRegistry = registerCreator.getCustomerRegistry();
        this.accountingSystem = registerCreator.getAccountingSystem();
        this.printer = printer;
    }

/**
* Get the SaleInformation in controller (using for test).
*
* @return saleInformation The object SaleInformation will be returned.
*/
    public SaleInformation getSaleInformation(){
        return saleInformation;
    }

/**
* Start a new sale with a new initialized SaleInformation.
* So that sold items can be entered and added during the following sale process.
*/
    public void startSale(){
        saleInformation = new SaleInformation();
    }

/**
* Enter the sold items to the saleInformation.
* @param identifier The int which is the soldItem ID number will be scanned in.
* @param quantity The int number which will be entered by view.
* @return saleInformation The new saleInformation uppdated with new entered items.
*/
    public SaleInformation enterItem (int identifier, int quantity){
        saleInformation.addItem(identifier, quantity, externalInventorySystem);
        saleInformation.uppdateSaleInformation();
        return saleInformation;
    }

/**
* Apply discount request from view.
* @param customerId The int which will be entered by view.
* @return priceAfterDiscount The double of price after that requested discounts is applied.
*/ 
    public double sendDiscountRequest (int customerId){
        double priceAfterDiscount = 0;
        priceAfterDiscount = saleInformation.includeDiscount(customerId, discountRegistry, customerRegistry);
        return priceAfterDiscount;
    }

/**
* End the sale process with inventory system and accounting system uppdated.
* @return SaleInformation The uppdated final saleInformation object will be returned.
*/ 
    public SaleInformation endSale(){
        HashMap<ItemDTO,Integer> soldItems = saleInformation.getSoldItems();
        externalInventorySystem.uppdateInventory(soldItems);
        saleInformation.uppdateSaleInformation();
        return saleInformation;
    }

/**
* Print the receipt out of the sale.
* @param paidAmount The double which will be entered by view.
*/ 
    public void printReceipt(double paidAmount){
        HashMap<ItemDTO,Integer> soldItems;
        Receipt receipt;
        double totalPrice = saleInformation.getTotalPrice();
        soldItems = saleInformation.getSoldItems();
        LocalDateTime dateTime = LocalDateTime.now();
        receipt = new Receipt(paidAmount, totalPrice, soldItems, dateTime);
        printer.printReceipt(receipt);
    }

}

