package Backend.ServiceLayer;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import BusinessLayer.Contact;
import BusinessLayer.Contract;
import BusinessLayer.ContractController;
import BusinessLayer.Order;
import BusinessLayer.OrderController;
import BusinessLayer.PeriodicOrder;
import BusinessLayer.Product1;
import BusinessLayer.Supplier;
import BusinessLayer.SupplierController;

public class Service {
     SupplierService supplierService;
     OrderService orderService;
     ContractService contractService;

    public Service() {
        this.supplierService = new SupplierService();
        this.orderService = new OrderService(supplierService);
        this.contractService = new ContractService(supplierService);
    }

    public SupplierController getSuppliersController(){
        return supplierService.getSuppliersController();
    }

    public OrderController getOrderController(){ return orderService.getOrderController();}

    public ContractController getContractController(){ return contractService.contractController;}

    public Supplier AddSupplier( int id,String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType, ArrayList<String> companies) {
        return supplierService.AddSupplier(id,name,phoneNumber,intSupplierType,BankAccount,paymentType,companies);
    }

    public String deleteAllsuplier(){
        return supplierService.deleteAllsuplier();
    }

    public String deleteAllContacts(){
        return supplierService.deleteAllcontacts();
    }

    public String deleteAllProducts(){
        return supplierService.deleteAllProducts();
    }

    public String deleteAllContracts(){
        return contractService.deleteAllContracts();
    }

    public String deleteAllOrders(){
        return orderService.deleteAllOrders();
    }

    public String DeleteSupplier(int id) {
        return supplierService.DeleteSupplier(id);
    }

    public String AddContact(int id,int contact_id, String name, String phone_number, String email) {
        return supplierService.AddContact( id,contact_id,name,phone_number,email);
    }

    public boolean AddProduct(int price, String product_name,int pid) {
        return supplierService.AddProduct(price,product_name,pid);
    }

    public String AddCompany(int id, String companyName){
        return supplierService.AddCompany( id,companyName);
    }

    public String DeleteCompany(int id, String companyName){
        return supplierService.DeleteCompany( id,companyName);
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
    public Supplier getSupplier(int id){
        return supplierService.getSupplier(id);
    }

    public HashMap<Integer, Product1> getAllProducts(){
        return supplierService.getAllProducts();
    }

    public double checkPrice(int supplier_id,HashMap<Integer, Integer> productAndQuantity){
        return contractService.checkPrice(supplier_id, productAndQuantity);
    }

    public String makeContract(int id, String contractName, boolean scheduled, HashMap<Product1, Map<Integer, Double>> productQuantityDiscount) {
        return contractService.makeContract( id,contractName,scheduled,productQuantityDiscount);
    }

    public String deleteContract(int id) {
        return contractService.deleteContract( id);
    }

    public ArrayList<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    public int AddPeriodicOrder(int id, List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date, int arrival_day ) throws IllegalAccessException, SQLException {
        return orderService.AddPeriodicOrder(id,products_id,productsAndAmount,date, arrival_day);
    }
    public boolean updatePeriodicOrder(int orderId,HashMap<Integer, Integer> productsAndAmount ){
        return orderService.updatePeriodicOrder(orderId, productsAndAmount);
    }
        public int AddOrder(int id, List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date ) throws IllegalAccessException, SQLException {
        return orderService.AddOrder(id,products_id,productsAndAmount,date );
    }

    public String DeleteOrder(int id, Order order) {
        return orderService.DeleteOrder( id,order);
    }

    public String toStringOrder(Order order){
        return orderService.toString(order);
    }
        public Order showOrder( int id,Order order) {
        return orderService.showOrder( id,order);
    }



    public ArrayList<Contact> getContacts(int id){
        return supplierService.getContacts(id);
    }

    public ArrayList<String> getCompanies(int id){
        return supplierService.getCompanies(id);
    }

    public Contract getContract(int id){
        return supplierService.getContract(id);
    }

    public String toStringSupplier(int id){
        return supplierService.toString(id);
    }

    public Map<Supplier, ArrayList<Order>> getOrders() {
        return orderService.getOrders();
    }
    public Map<Supplier, ArrayList<PeriodicOrder>> getPeriodicOrders() {
        return orderService.getPeriodicOrders();
    }

    public Map<Integer, Order> getIdOrders() {
        return orderService.getIdOrders();
    }
    public Map<Integer, PeriodicOrder> getIdPeriodicOrders() {
        return orderService.getIdProdicOrders();
    }

    public int getTotalOrder(Order order) {
        return orderService.getTotalOrder(order);
    }

    public List<Integer> getProducts_id(Order order){
        return orderService.getProducts_id(order);
    }

    public Supplier getSupplier(Order order) {
        return orderService.getSupplier(order);
    }

    public HashMap<Integer, Integer> getProductsAndAmount(Order order) {
        return orderService.getProductsAndAmount(order);
    }

    public HashMap<Product1, Double> getPricePerProducts(Order order) {
        return orderService.getPricePerProducts(order);
    }

    public String Advance_day(){
        return orderService.Advance_day();
    }
    }
