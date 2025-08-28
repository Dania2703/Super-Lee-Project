package BusinessLayer;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderController {
    private static OrderController instance= new OrderController();
    public Map<Supplier, ArrayList<Order>> orders = new HashMap<>(); ///shortage order
    public Map<Supplier, ArrayList<PeriodicOrder>> periodicOrders = new HashMap<>(); /// periodic orders

    public Map<Integer, Order> orders_id = new HashMap<>(); /// shortage orders
    public Map<Integer, PeriodicOrder> orders_periodic_id = new HashMap<>(); // periodic order

    DataBaseController dataBaseController=DataBaseController.getInstance();

    LocalDate date= LocalDate.now();
    int currentDay = date.getDayOfWeek().getValue();

    public static OrderController getInstance(){
        return instance;
    }

    public String toString(Order order){
        return order.toString();
    }


    public boolean checkSupplierWithProducts(Supplier s,Set<Integer> products_id){
        if (s.getContract() == null){
            return false;
        }
        for(Integer productId: products_id){
            Product1 product = SupplierController.getInstance().getProductById(productId);
            if ( !s.getContract().getProductsNumber().contains(product)){
                return false;
            }
        }
        return true;

    }
    public Supplier findCheapestSupplier(HashMap<Integer, Integer> productAndQuantity){
        double best_price = Integer.MAX_VALUE;
        ArrayList<Supplier> suppliers= SupplierController.getInstance().getAllSuppliers();
        Set<Integer> products_id = productAndQuantity.keySet();
        Supplier supplier=null;
        for(Supplier s: suppliers) {
            if (checkSupplierWithProducts(s, products_id)){
                double price= s.getContract().checkPrice(productAndQuantity);
                if( price <= best_price ){
                    best_price = price;
                    supplier =s;
                }
            }
        }
        return supplier;
    }
    public int AddOrder(int id, List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date, SupplierController supplierController ) throws IllegalAccessException, SQLException {
        Supplier supplier = findCheapestSupplier(productsAndAmount);

        if(supplier == null){
            throw new IllegalAccessException("No supplier for this order");
        }
        if(  products_id==null || productsAndAmount == null || date==null){
            throw new IllegalAccessException("Data order cannot be empty");

        }
        Order order= new Order(id, products_id,supplier,productsAndAmount, date);
        order.calculatPricePerProduct();
        orders_id.put(order.getOrder_id(), order);
        orders.put(supplier,new ArrayList<>());
        orders.get(supplier).add(order);
        dataBaseController.insert_order(order.object2Dto());
        return order.getOrder_id();

    }

    public int AddPeriodicOrder(int id,List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date, int arrival_day, SupplierController supplierController ) throws IllegalAccessException, SQLException {
        Supplier supplier= findCheapestSupplier(productsAndAmount);
        if(supplier == null){
            throw new IllegalAccessException("No supplier for this order");
        }
        if( products_id==null || productsAndAmount == null || date==null ){
            throw new IllegalAccessException("Data order cannot be empty");

        }
        PeriodicOrder periodicOrder= new PeriodicOrder(id, products_id,supplier,productsAndAmount, date, arrival_day);
        periodicOrder.calculatPricePerProduct();
        orders_periodic_id.put(periodicOrder.getOrder_id(), periodicOrder);
        periodicOrders.put(supplier,new ArrayList<>());
        periodicOrders.get(supplier).add(periodicOrder);
        dataBaseController.insert_order(periodicOrder.object2Dto());
        return periodicOrder.getOrder_id();

    }

    public boolean updatePeriodicOrder(int orderId,HashMap<Integer, Integer> productsAndAmount ) throws SQLException {
        PeriodicOrder periodicOrder1 = null;
      //  Order order= orders_id.get(orderId);
    //    ArrayList<PeriodicOrder> periodicOrderArrayList= periodicOrders.get(order.getSupplier());
        for(PeriodicOrder po: orders_periodic_id.values()){
            if (po.getOrder_id() == orderId){
                periodicOrder1 = po;
            }
        }
        if(periodicOrder1 == null){
            return false;
        }
        PeriodicOrder newPeriodicOrder =periodicOrder1.update_periodic_order(productsAndAmount);

        if(newPeriodicOrder != null){
            newPeriodicOrder.calculatPricePerProduct();
            orders_periodic_id.remove(periodicOrder1);
            orders_periodic_id.put(newPeriodicOrder.getOrder_id(), newPeriodicOrder);
            periodicOrders.remove(periodicOrder1.getSupplier());
            periodicOrders.put(newPeriodicOrder.getSupplier(), new ArrayList<>());
            periodicOrders.get(newPeriodicOrder.getSupplier()).add(newPeriodicOrder);
            dataBaseController.updateOrder(newPeriodicOrder.object2Dto());
            return true;
        }

        return false;
    }
    public String DeleteOrder(int id, Order order, SupplierController supplierController) throws IllegalAccessException, SQLException {
        if ( id==0 || order==null){
            throw new IllegalAccessException("Data order cannot be empty");
        }
        Supplier supplier= supplierController.getSupplier( id);
        if(!orders.containsKey(supplier)){
            throw new IllegalAccessException("Data order not found");
        }
        if(order.isPeriodicOrder()){
            orders_periodic_id.remove(order.getOrder_id());
            periodicOrders.get(supplier).remove(order);
        }else{
            orders.get(supplier).remove(order);
            orders_id.remove(order.getOrder_id());
        }

        dataBaseController.delete_Order(order.getOrder_id());
        return "Order deleted successfully";
    }

    public Order showOrder(int id, Order order, SupplierController supplierController ) throws IllegalAccessException {
        Supplier supplier= supplierController.getSupplier( id);
        if(!supplierController.getAllSuppliers().contains(supplier)){
            throw new IllegalAccessException("Supplier not found");
        }
        if(!orders_id.containsKey(order.getOrder_id())){
            throw new IllegalAccessException("Data order cannot be empty");
        }
        for(Order o: orders.get(supplier)){
            if(o.getOrder_id() == order.getOrder_id()){
                return o;
            }
        }
        throw new IllegalAccessException("Order not found for this supplier");
    }


    public  Map<Supplier,ArrayList<Order>> getOrders(){
        return orders ;
    }

    public  Map<Supplier,ArrayList<PeriodicOrder>> getPeriodicOrders(){
        return periodicOrders ;
    }

    public  Map<Integer,Order> getIdOrders(){
        return orders_id;
    }
    public  Map<Integer,PeriodicOrder> getIdProdicOrders(){
        return orders_periodic_id;
    }

    public int getTotalOrder(Order order){
        return order.getTotalPrice();
    }

    public List<Integer> getProducts_id(Order order) {
        return order.getProducts_id();
    }

    public Supplier getSupplier(Order order) {
        return order.getSupplier();
    }

    public HashMap<Integer, Integer> getProductsAndAmount(Order order) {
        return order.getProductsAndAmount();
    }

    public HashMap<Product1, Double> getPricePerProducts(Order order) {
        return order.getPricePerProducts();
    }

    public LocalDate getDate(Order order) {
        return order.getDate();
    }


    public String increment_day() throws SQLDataException, IllegalAccessException {
        if (currentDay == 6){
            currentDay = 0;
        }
        else {
            currentDay++;
        }
        runThroughDay();
        return "Increment Day success";
    }

    public void runThroughDay() throws SQLDataException, IllegalAccessException {
        for(PeriodicOrder po: orders_periodic_id.values()){
            if(po.getArrival_day() == this.currentDay){
                makeOrdertoPeriodic(po.getOrder_id(),po.getProducts_id(), po.getSupplier(),po.getProductsAndAmount(), po.getDate(), SupplierController.getInstance());
            }
        }
    }

    public int makeOrdertoPeriodic(int id, List<Integer> products_id, Supplier supplier,HashMap<Integer, Integer> productsAndAmount, LocalDate date, SupplierController supplierController ) throws IllegalAccessException {
        if(  products_id==null || productsAndAmount == null || date==null){
            throw new IllegalAccessException("Data order cannot be empty");

        }
        Order order= new Order( id,products_id,supplier,productsAndAmount, date);
        order.calculatPricePerProduct();
        orders_id.put(order.getOrder_id(), order);
        orders.put(supplier,new ArrayList<>());
        orders.get(supplier).add(order);
        return order.getOrder_id();

    }

    public String deleteAllOrders() throws SQLException, IllegalAccessException {
        List<Order> order1 = dataBaseController.selectAllOrders();
        for (Order o: order1){
//            DeleteSupplier(s.getSupplier_id());
            dataBaseController.delete_Order(o.getOrder_id());
        }
        return "All orders deleted";
    }




}
