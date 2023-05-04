package se.kth.iv1350.pos.integration;


/**
* This is the RegisterCreator class in the integration to create all the external database systems:
* CustomerRegistry, DiscountRegistry, ExternalInventorySystem and the AccountingSystem, 
* which can result in a better encapsulation and a lower coupling.
*/
public class RegisterCreator {
    private CustomerRegistry customerRegistry;
    private DiscountRegistry discountRegistry;
    private ExternalInventorySystem externalInventorySystem;
    private AccountingSystem accountingSystem;
    
    public RegisterCreator(){
        customerRegistry = new CustomerRegistry();
        discountRegistry = new DiscountRegistry();
        externalInventorySystem = new ExternalInventorySystem();
        accountingSystem = new AccountingSystem();
    }

/**
* Get external inventory system of all items to find and get a valid itemDTO.
* @return externalInventorySystem containing information of all ItemDTOs.
*/
    public ExternalInventorySystem getItemRegistry(){
        return externalInventorySystem;
    }

/**
* Get discountRegistry of all discount information.
* @return discountRegistry containing information of all eligible discounts.
*/
    public DiscountRegistry getDiscountRegistry(){
        return discountRegistry;
    }

/**
* Get customerRegistry of customer information.
* @return customerRegistry It contains information of all customers.
*/
    public CustomerRegistry getCustomerRegistry(){
        return customerRegistry;
    }

/**
* Get external AccountingSystem.
* @return accountingSystem It should be uppdated after ending a sale.
*/
    public AccountingSystem getAccountingSystem(){
        return accountingSystem;
    }
}
