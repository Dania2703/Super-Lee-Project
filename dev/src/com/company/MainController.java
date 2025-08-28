package com.company;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Backend.ServiceLayer.Service;
import BusinessLayer.Contact;
import BusinessLayer.Contract;
import BusinessLayer.Order;
import BusinessLayer.PeriodicOrder;
import BusinessLayer.Product1;
import BusinessLayer.Supplier;

public class MainController {
    private static Service service=new Service();


    public MainController() {
      
    }

    public static void mainMenu() throws IllegalAccessException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int option;
        try {
            do {
                System.out.println("Please choose an option:");
                System.out.println("1. Start the system with initialize data");
                System.out.println("2. Suppliers");
                System.out.println("3. Contracts");
                System.out.println("4. Orders");
                System.out.println("5. Show Data System");
                System.out.println("6. clear Data");
                System.out.println("0. Exit");

                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        initializeSystem();
                        break;
                    case 2:
                        Suppliers();
                        break;

                    case 3:
                        Contracts();
                        break;
                    case 4:
                        Orders();
                        break;

                    case 5:
                        showDataSystem();
                        break;

                    case 6:
                        clearData();
                        break;

                    case 0:
                    Main.vmain();
                    break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        continue;
                }
            } while (option != 0);
        } catch (NumberFormatException | IOException e) {
            System.out.println("Invalid input. Please try again.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearData(){
        service.deleteAllsuplier();
        service.deleteAllProducts();
        service.deleteAllContacts();
        service.deleteAllContracts();
        service.deleteAllOrders();
    }
    public static void initializeSystem() throws IllegalAccessException, SQLException {
        ArrayList<String> companies1= new ArrayList<>();
        ArrayList<String> companies2= new ArrayList<>();
        ArrayList<String> companies3= new ArrayList<>();
        companies1.add("tara");
        companies1.add("tnova");
        companies2.add("pepsi");
        companies3.add("pepsi");
        companies3.add("Aosem");

        service.AddSupplier( 1,"Aseel", "0544123454", 1, "Masad", "Cash", companies1);
        service.AddSupplier( 2,"Tom", "0544532733", 2, "Markntel", "CreditCard", companies2);
        service.AddSupplier( 3,"Jon", "0512345678", 3, "Poalim", "Cash", companies3);

        service.AddContact(1, 111111111, "Sam", "0587654321", "sam@gmail.com");
        service.AddProduct(10, "milk",11);
        service.AddProduct(12, "yogurt",22);
        service.AddProduct(20, "tona", 33);

        ///////// make contract
        Map<Integer, Double> QuantityDiscount1 = new HashMap<>();
        QuantityDiscount1.put(100,10.0);

        Map<Integer, Double> QuantityDiscount2 = new HashMap<>();
        QuantityDiscount2.put(100,50.0);
        HashMap<Product1, Map<Integer,Double>> productQuantityDiscount1 = new HashMap<>();
        Product1 p11= service.getAllProducts().get(11);
        Product1 p22 = service.getAllProducts().get(22);
        Product1 p33 = service.getAllProducts().get(33);
        productQuantityDiscount1.put(p11, QuantityDiscount1);
        productQuantityDiscount1.put(p33, QuantityDiscount2);
        productQuantityDiscount1.put(p22, new HashMap<>());
        service.makeContract(2, "2", true, productQuantityDiscount1);


        Map<Integer, Double> QuantityDiscount = new HashMap<>();
        QuantityDiscount.put(100,10.0);
        HashMap<Product1, Map<Integer,Double>> productQuantityDiscount = new HashMap<>();
        Product1 p1= service.getAllProducts().get(11);
        Product1 p2 = service.getAllProducts().get(22);
        Product1 p3 = service.getAllProducts().get(33);
        productQuantityDiscount.put(p1, QuantityDiscount);
        productQuantityDiscount.put(p2, new HashMap<>());
        productQuantityDiscount.put(p3, new HashMap<>());
        service.makeContract(1, "1", true, productQuantityDiscount);


        ///////////make order
        ArrayList<Integer> productsId= new ArrayList<>();
        productsId.add(p1.getProduct_id());
        productsId.add(p2.getProduct_id());
        HashMap<Integer,Integer> quantity = new HashMap<>();
        quantity.put(11, 150);
        quantity.put(22,1500);
       // service.AddOrder( 1,productsId, quantity, LocalDate.of(2024,6,7));

        ArrayList<Integer> productsId1= new ArrayList<>();
        productsId1.add(p11.getProduct_id());
        productsId1.add(p33.getProduct_id());
        HashMap<Integer,Integer> quantity1 = new HashMap<>();
        quantity1.put(11, 100);
        quantity1.put(33,1000);
       // service.AddPeriodicOrder(2,productsId1,quantity1, LocalDate.of(2024,7,3), 4);


        HashMap<Integer,Integer> quantity2 = new HashMap<>();
        quantity2.put(11, 100);
       // quantity1.put(33,1000);
        service.updatePeriodicOrder(2, quantity2);
    }

    public static void Suppliers() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.add Supplier");
            System.out.println("2.delete Supplier");
            System.out.println("3.add a new company");
            System.out.println("4.delete company");
            System.out.println("5.add a new product");
            System.out.println("6.add a new contact");
            System.out.println("7.Get all the suppliers");

            System.out.println("0. Exit");


            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
//                        System.out.println("Enter the supplier id");
//                        int id = Integer.parseInt(reader.readLine());
                        System.out.println("Enter a supplier ID");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.println("Enter a supplier name:");
                        String name = (reader.readLine());
                        System.out.println("Enter phone number:");
                        String phoneNumber = reader.readLine();
                        System.out.println("Enter supplier type:\n1)ScheduledSupplier \n2)OnDemandOrdering \n3)PickAndDelivery");
                        int type = Integer.parseInt(reader.readLine());
                        System.out.println("Enter supplier bank account:");
                        String bankAccount = reader.readLine();
                        System.out.println("Enter payment type:");
                        String paymentType = reader.readLine();

                        ArrayList<String> companies = new ArrayList<>();
                        System.out.println("Enter number of companies the supplier works with:");
                        int number = Integer.parseInt(reader.readLine());
                        System.out.println("Enter the companies name:");
                        for (int i = 0; i < number; i++) {
                            companies.add(reader.readLine());
                        }
                        int newID = service.AddSupplier( id,name, phoneNumber, type, bankAccount, paymentType, companies).getSupplier_id();
                        System.out.println("Supplier is added and his ID is: " + newID + "\n");
                        break;

                    case 2:
                        System.out.println("Enter supplier ID to delete:");
                        int supplierID = Integer.parseInt(reader.readLine());
                        service.DeleteSupplier(supplierID);
                        break;

                    case 3:
                        System.out.println("Enter a supplier ID:");
                        int supplierID2 = Integer.parseInt(reader.readLine());
                        System.out.println();
                        System.out.println("Enter company name:");
                        String company = reader.readLine();
                        System.out.println();
                        service.AddCompany(supplierID2, company);
                        break;

                    case 4:
                        System.out.println("Enter a supplier id:");
                        int supplierID3 = Integer.parseInt(reader.readLine());
                        System.out.println();
                        System.out.println("Enter company name:");
                        String company1 = reader.readLine();
                        System.out.println();
                        service.DeleteCompany(supplierID3, company1);
                        break;

                    case 5:
                        System.out.println("Enter product name:");
                        String productName = reader.readLine();
                        System.out.println("Enter product price:");
                        int price = Integer.parseInt(reader.readLine());
                        System.out.println("Enter product ID:");
                        int pid = Integer.parseInt(reader.readLine());
                        while (!service.AddProduct(price, productName,pid)){
                            System.out.println("this id is taken Enter another product ID:");
                            pid = Integer.parseInt(reader.readLine());
                        }
                        System.out.println("Product is added and his ID is: " + pid + "\n");
                        break;

                    case 6:
                        System.out.println("Enter supplier id:");
                        int supplierID4 = Integer.parseInt(reader.readLine());
                        System.out.println("Enter contact id:");
                        int contactId = Integer.parseInt(reader.readLine());
                        System.out.println("Enter contact name:");
                        String contactName = reader.readLine();
                        System.out.println("Enter contact phone number:");
                        String phoneNumber5 = reader.readLine();
                        System.out.println("Enter contact email:");
                        String contactEmail = reader.readLine();
                        service.AddContact(supplierID4, contactId, contactName, phoneNumber5, contactEmail);
                        break;

                    case 7:
                        for (Supplier s : service.getAllSuppliers()) {
                            System.out.println(s.toString());
                        }
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } while (action != 0);
    }


    public static void Contracts() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.add a new contract with supplier");
            System.out.println("2.delete contract with supplier");
            System.out.println("3.Get all the contracts");
            System.out.println("0. Exit");


            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        System.out.println("Enter a supplier id:");
                        int supplierID = Integer.parseInt(reader.readLine());
                        System.out.println("Enter a contract name:");
                        String contractName = reader.readLine();
                        System.out.println("Is the supplying scheduled?(yes/no)");
                        boolean scheduled = "yes".equals(reader.readLine());

                        System.out.println("Enter number of products:");
                        int numberProducts = Integer.parseInt(reader.readLine());
                        int counter = 1;

                        System.out.println("now enter the id for each product and the needed quantity for the discount:");
                        HashMap<Product1, Map<Integer, Double>> productQuantityDiscount = new HashMap<>();

                        while (numberProducts != 0) {
                            Map<Integer, Double> QuantityDiscount = new HashMap<>();
                            System.out.println("Enter product number " + counter +" Id:");
                            int productId = Integer.parseInt(reader.readLine());
                            Product1 p = service.getAllProducts().get(productId);
                            System.out.println("Enter the needed quantity for discount:");
                            int productQuantity = Integer.parseInt(reader.readLine());
                            System.out.println("Enter the discount:");
                            double discount = Integer.parseInt(reader.readLine());
                            QuantityDiscount.put(productQuantity, discount);
                            productQuantityDiscount.put(p, QuantityDiscount);
                            System.out.println("Is there more discounts for different quantities for this product?(yes/no)");
                            String answer = reader.readLine();

                            while (answer.equals("yes")) {
                                System.out.println("Enter the needed quantity for discount:");
                                productQuantity = Integer.parseInt(reader.readLine());
                                System.out.println("Enter the discount:");
                                discount = Integer.parseInt(reader.readLine());
                                QuantityDiscount.put(productQuantity, discount);
                                productQuantityDiscount.put(p, QuantityDiscount);
                                System.out.println("Is there more discounts for different quantities for this product?(yes/no)");
                                answer = reader.readLine();
                            }
                            counter++;
                            numberProducts--;
                        }
                        String st = service.makeContract(supplierID, contractName, scheduled, productQuantityDiscount);
                        System.out.println(st + "\n");
                        break;

                    case 2:
                        System.out.println("Enter supplier id:");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.println(service.deleteContract(id));
                        break;

                    case 3:
                        ArrayList<Contract>  contractList =service.getAllContracts();
                        if(contractList.isEmpty()){
                            System.out.println("There is No Contracts");
                        }
                        else{
                            for(Contract c: contractList){
                                System.out.println(c);
                            }
                        }
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (action != 0);
    }


    public static void Orders() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.make a new order");
            System.out.println("2.make a new periodic order");
            System.out.println("3.update an exist periodic order ");
            System.out.println("4.delete order");
            System.out.println("5.Get order");
            System.out.println("0. Exit");

            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        System.out.println("Enter an order ID:");
                        int id= Integer.parseInt(reader.readLine());
                        System.out.println("Enter order date(yyyy-MM-dd):");
                        String inputDate = reader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(inputDate, formatter);
//                        System.out.println("Enter order id:");
//                        int inputId = Integer.parseInt(reader.readLine());

                        ArrayList<Integer> productsId = new ArrayList<>();
                        HashMap<Integer, Integer> productsAndQuantity = new HashMap<>();
                        System.out.println("how many products do you want to order?:");
                        int numberofitems = Integer.parseInt(reader.readLine());
                        int counter = 1;
                        while (numberofitems != 0) {
                            System.out.println("Enter product number " + counter +" Id:");
                            int product_Id = Integer.parseInt(reader.readLine());
                            System.out.println("how many items of this products do you want to order?:");
                            int quantity = Integer.parseInt(reader.readLine());
                            productsId.add(product_Id);
                            productsAndQuantity.put(product_Id, quantity);
                            counter++;
                            numberofitems--;
                        }
                        System.out.println("Order added successfully. your order id is (" + service.AddOrder(id, productsId, productsAndQuantity, date) +")\n");
                        break;

                    case 2:
                        System.out.println("Enter an order ID:");
                        int id1= Integer.parseInt(reader.readLine());
                        System.out.println("Enter order date(yyyy-MM-dd):");
                        String inputDate1 = reader.readLine();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        // Parse the input date string to LocalDate
                        LocalDate date1 = LocalDate.parse(inputDate1, dateFormatter);
//                        System.out.println("Enter order id:");
//                        int inputId = Integer.parseInt(reader.readLine());

                        ArrayList<Integer> productsId1 = new ArrayList<>();
                        HashMap<Integer, Integer> productsAndQuantity1 = new HashMap<>();
                        System.out.println("how many products do you want to order?:");
                        int numberofitems1 = Integer.parseInt(reader.readLine());
                        int counter1 = 1;
                        while (numberofitems1 != 0) {
                            System.out.println("Enter product number " + counter1 +" Id:");
                            int product_Id = Integer.parseInt(reader.readLine());
                            System.out.println("how many items of this products do you want to order?:");
                            int quantity = Integer.parseInt(reader.readLine());
                            productsId1.add(product_Id);
                            productsAndQuantity1.put(product_Id, quantity);
                            counter1++;
                            numberofitems1--;
                        }
                        System.out.println("Enter the arrival day for this periodic order:");
                        int arrival_day = Integer.parseInt(reader.readLine());
                      System.out.println("Periodic Order added successfully. your order id is (" + service.AddPeriodicOrder( id1,productsId1, productsAndQuantity1, date1, arrival_day) +")\n");
                        break;

                    case 3:
                        System.out.println("Enter periodic order id to update it:");
                        int periodic_id = Integer.parseInt(reader.readLine());
                        ArrayList<Integer> productsId2 = new ArrayList<>();
                        HashMap<Integer, Integer> productsAndQuantity2 = new HashMap<>();
                        System.out.println("make a new report to the order\n how many products do you want to order?:");
                        int numberofitems2 = Integer.parseInt(reader.readLine());
                        int counter2 = 1;
                        while (numberofitems2 != 0) {
                            System.out.println("Enter product number " + counter2 +" Id:");
                            int product_Id = Integer.parseInt(reader.readLine());
                            System.out.println("how many items of this products do you want to order?:");
                            int quantity = Integer.parseInt(reader.readLine());
                            productsId2.add(product_Id);
                            productsAndQuantity2.put(product_Id, quantity);
                            counter2++;
                            numberofitems2--;
                        }
                        if(service.updatePeriodicOrder( periodic_id, productsAndQuantity2)){
                            System.out.println("The Periodic Order updated successfully.");
                        }else{
                            System.out.println("The Periodic Order cant updated.");

                        }


                    case 4:
                        System.out.println("Enter supplier id:");
                        int id11 = Integer.parseInt(reader.readLine());
                        System.out.println("Enter order Id:");
                        int orderId = Integer.parseInt(reader.readLine());
                        Order order = service.getIdOrders().get(orderId);
                        System.out.println(service.DeleteOrder(id11, order));
                        break;

                    case 5:
                      //  System.out.println("Enter supplier id:");
                        //int supplierId = Integer.parseInt(reader.readLine());
                        System.out.println("enter order Id:");
                        int orderId1 = Integer.parseInt(reader.readLine());
                        if(service.getIdOrders().get(orderId1) != null){
                            System.out.println(service.getIdOrders().get(orderId1));
                        }
                        if (service.getIdPeriodicOrders().get(orderId1) != null){
                            System.out.println(service.getIdPeriodicOrders().get(orderId1));
                        }
                     //   Order order1 = service.getIdOrders().get(orderId1);
                       // System.out.println(service.showOrder(supplierId, order1));

//                        System.out.println("Enter Order Id:");
//                        int orderId = Integer.parseInt(reader.readLine());
//                        Order order1 = service.getIdOrders().get(orderId);
//                        System.out.println(order1);
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while (action != 0);

    }

    public static <List> void showDataSystem() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.Show a Supplier Data");
            System.out.println("2.Show all Suppliers Data ");
            System.out.println("3.Show Supplier Contacts");
            System.out.println("4.Show Supplier Contract");
            System.out.println("5.Show Supplier Companies");
            System.out.println("6.Show Supplier Orders");
            System.out.println("7.Show Order Data");
            System.out.println("8.Show Order Price");
            System.out.println("9.Show Order Supplier");
            System.out.println("10.Show all Contracts");
            System.out.println("0. Exit");


            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        System.out.println("Enter Supplier id:");
                        int id = Integer.parseInt(reader.readLine());

                        Supplier s = service.getSupplier(id);
                        System.out.println(s);
                     //   service.toStringSupplier(phoneNumber);
                        break;
                    case 2:
                        ArrayList<Supplier> SupplierList = service.getAllSuppliers();
                        for (Supplier supplier: SupplierList){
                            System.out.println(supplier);
                        }
                        break;

                    case 3:
                        System.out.println("Enter Supplier id:");
                        int supplierId = Integer.parseInt(reader.readLine());
                        ArrayList<Contact> contactsList =service.getContacts(supplierId);
                        if (contactsList == null){
                            System.out.println("No Contacts");
                        }
                        else{

                            for (Contact contact: contactsList){
                                System.out.println(contact);
                            }
                        }

                        break;

                    case 4:
                        System.out.println("Enter Supplier id:");
                        int supplierId2 = Integer.parseInt(reader.readLine());
                        Contract contract1= service.getContract(supplierId2);
                        System.out.println(contract1);
                        break;

                    case 5:
                        System.out.println("Enter Supplier id:");
                        int supplierId1 = Integer.parseInt(reader.readLine());
                        ArrayList<String> companiesList=service.getCompanies(supplierId1);
                        if(companiesList == null){
                            System.out.println("No Companies for this supplier");
                        }
                        for(String c: companiesList){
                            System.out.println(c);
                        }
                        break;

                    case 6:
                        System.out.println("Enter Supplier id:");
                        int supplierId3 = Integer.parseInt(reader.readLine());
                        Supplier supplier = service.getSupplier(supplierId3);
                        ArrayList<Order> orderList = service.getOrders().get(supplier);
                        ArrayList<PeriodicOrder> orderList1 = service.getPeriodicOrders().get(supplier);
                        if(orderList == null){
                            System.out.println("No Orders");
                        }
                        else{
                            for(Order o: orderList){
                                System.out.println(o);
                            }
                            for(PeriodicOrder po: orderList1){
                                System.out.println(po);
                            }
                        }

                        break;

                    case 7:
                        System.out.println("Enter Order Id:");
                        int orderId = Integer.parseInt(reader.readLine());
                        Order order1 = service.getIdOrders().get(orderId);
                        if (order1 != null){
                            System.out.println(order1);
                        }
                        PeriodicOrder order2 = service.getIdPeriodicOrders().get(orderId);
                        if (order2 != null){
                            System.out.println(order2);
                        }
                        break;

                    case 8:
                        System.out.println("Enter Order Id:");
                        int orderId1 = Integer.parseInt(reader.readLine());
                        Order order = service.getIdOrders().get(orderId1);
                        if (order !=null){
                            int totalOrder = service.getTotalOrder(order);
                            System.out.println(totalOrder);
                        }
                        PeriodicOrder periodicOrder1 = service.getIdPeriodicOrders().get(orderId1);
                        if (periodicOrder1 !=null){
                            int totalOrder = service.getTotalOrder(periodicOrder1);
                            System.out.println(totalOrder);
                        }
                        break;

                    case 9:
                        System.out.println("Enter Order Id:");
                        int orderId2 = Integer.parseInt(reader.readLine());
                        Order order3 = service.getIdOrders().get(orderId2);
                        if (order3 != null){
                            Supplier supplier1= service.getSupplier(order3);
                            System.out.println(supplier1);
                        }
                        PeriodicOrder periodicOrder2= service.getIdPeriodicOrders().get(orderId2);
                        if (periodicOrder2 != null){
                            Supplier supplier1= service.getSupplier(periodicOrder2);
                            System.out.println(supplier1);
                        }

                       // service.toStringSupplier(supplier1.getPhoneNumber());
                        break;

                    case 10:
                        ArrayList<Contract>  contractList =service.getAllContracts();
                        if(contractList == null){
                            System.out.println("No Contracts");
                        }
                        else{
                            for(Contract c: contractList){
                                System.out.println(c);
                            }
                        }
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while (action != 0);
    }
}
