package se.kth.iv1350.pos.view;

import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.CustomerDTO;
import se.kth.iv1350.pos.controller.Controller;

/**
* This is the program's only View class, which is used for calling all the methods by cashier in controller.
* Since there is no actural activities from view, there is a hard-code to show the whole sale rocess.
*/ 
public class View {
    private Controller controller;

/**
* Create the View.
* @param controller The Controller should be entered.
*/
    public View(Controller controller)
    {
        this.controller = controller;
    }

/**
* Hard-coded method calls to run the sale process.
* All the methods calling pass through controller.
* Everything that is returned by the controller as well as the receipt will be printed out.
*/
    public void runFakeScenario(){
        System.out.println("\n");
        System.out.println("Customer id 1234 walks into the store.");
        CustomerDTO customer = new CustomerDTO(1234, 20, "SWE");

        ItemDTO cola = new ItemDTO(778020, "Cola", "500ml", 0.2, 15);
        int colaId = cola.getId();

        ItemDTO chips = new ItemDTO(520001, "OLW chips", "250g", 0.2, 30);
        int chipsId = chips.getId();

        ItemDTO glass = new ItemDTO(339800, "Hagendas", "Strawberry", 0.2, 70);
        int glassId = glass.getId();

        int customerId = customer.getId();
        double paidAmount = 400;

        System.out.println("The customer starts the sale.");
        controller.startSale();

        System.out.println("The customer buys 10 Cola.");
        controller.enterItem(colaId, 10);

        System.out.println("The customer buys 2 chipss.");
        controller.enterItem(chipsId, 3);

        System.out.println("The customer buys 2 Hagendas.");
        controller.enterItem(glassId, 2);

        System.out.println("The customer asks for discounts.");
        controller.sendDiscountRequest(customerId);

        System.out.println("The customer asks to end the sale.");
        controller.endSale();

        System.out.println("The customer pays and get the receipt.");
        System.out.println("\n");
        controller.printReceipt(paidAmount);
    }
}
