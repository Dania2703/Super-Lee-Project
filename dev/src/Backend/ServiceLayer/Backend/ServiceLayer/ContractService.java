package Backend.ServiceLayer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BusinessLayer.Contract;
import BusinessLayer.ContractController;
import BusinessLayer.Product1;
import BusinessLayer.SupplierController;

public class ContractService {
    public ContractController contractController = new ContractController();
    public SupplierController supplierController;

    public ContractService(SupplierService supplierController) {
        this.supplierController = supplierController.getSuppliersController();
    }

    public double checkPrice(int supplier_id,HashMap<Integer, Integer> productAndQuantity){
        try {
            double output = contractController.checkPrice(supplier_id,productAndQuantity);
            return output;
        } catch (Exception e) {
            return 0;
        }
    }
    public String makeContract(int id, String contractName, boolean scheduled, HashMap<Product1, Map<Integer, Double>> productQuantityDiscount) {
        try {
            String output = contractController.makeContract( id, contractName, scheduled, productQuantityDiscount,supplierController);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String deleteContract(int id) {
        try {
            String output = contractController.deleteContract( id, supplierController);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Contract> getAllContracts() {
        try {
            ArrayList<Contract> output = contractController.getAllContracts();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public String deleteAllContracts() {
        try {
            String output = contractController.deleteAllcontracts();
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}
