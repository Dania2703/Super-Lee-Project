package BusinessLayer;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractController {
    private static ContractController instance=new ContractController();;
    private ArrayList<Contract> contracts ;

    public ContractController() {
        this.contracts = new ArrayList<>();
    }

    public static ContractController getInstance(){
        return instance;
    }

    DataBaseController dataBaseController=DataBaseController.getInstance();

    public double checkPrice(int supplier_id,HashMap<Integer, Integer> productAndQuantity){
        double price=0;
        for(Contract contract: contracts){
            if(contract.getSupplier_id() == supplier_id){
                price = contract.checkPrice(productAndQuantity);
            }
        }
        return price;
    }

    public String makeContract(int id, String contractName, boolean scheduled, HashMap<Product1, Map<Integer, Double>> productQuantityDiscount, SupplierController supplierController) throws IllegalAccessException, SQLException {
        if(contractName==null || productQuantityDiscount==null){
            throw new IllegalAccessException("Data empty");
        }
        HashMap<Product1, Map<Integer, Double>> productQuantityDiscount1= new HashMap<>();
        ArrayList<Product1> productsNumber1 = new ArrayList<>();
        for (Product1 p: productQuantityDiscount.keySet()){
            if(!supplierController.getAllProducts().containsKey(p.getProduct_id())){
                throw new IllegalAccessException("Product not found");
            }
            productQuantityDiscount1.put(p, productQuantityDiscount.get(p));
            productsNumber1.add(p);
        }
        Supplier supplier= supplierController.getSupplier(id);
        Contract contract= new Contract(contractName, supplier.getSupplier_id(),scheduled,productsNumber1,productQuantityDiscount1);
        contracts.remove(supplier.getContract());
        supplier.newContract(contract);
        contracts.add(contract);
        dataBaseController.insertContract(contract.objectDTOContract());
        return "Contract made successfully";
    }

    public String deleteContract(int id, SupplierController supplierController) throws IllegalAccessException, SQLException {
        if( id==0){
            throw new IllegalAccessException("The supplier cannot be empty");
        }

        if(supplierController.getSupplier(id) == null){
            throw new IllegalAccessException("The supplier not found");
        }
        Supplier supplier= supplierController.getSupplier(id);
        contracts.remove(supplier.getContract());
        supplier.deleteContract();
        dataBaseController.deleteContract(supplier.getContract().getContract_name());
        return "Contract deleted successfully";
    }

    public ArrayList<Contract> getAllContracts(){
        return contracts;
    }

    public String deleteAllcontracts() throws SQLException, IllegalAccessException {
        List<Contract> contracts1 = dataBaseController.selectAllContracts();
        for (Contract c: contracts1){
//            DeleteSupplier(s.getSupplier_id());
            dataBaseController.deleteContract(c.getContract_name());
        }
        return "All contracts deleted";
    }
}
