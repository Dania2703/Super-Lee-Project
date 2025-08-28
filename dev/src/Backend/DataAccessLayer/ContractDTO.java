package DataAccessLayer;

import BusinessLayer.Contract;
import BusinessLayer.Product1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContractDTO {
    private int supplier_id;
    private String contract_name;
    private boolean scheduled;
    private String productsNumber ;
    private String productQuantityDiscount;

    public ContractDTO(String contract_name, int supplier_id, boolean scheduled, String productsNumber , String productQuantityDiscount) {
        this.contract_name = contract_name;
        this.supplier_id = supplier_id;
        this.scheduled = scheduled;
        this.productsNumber = productsNumber;
        this.productQuantityDiscount = productQuantityDiscount;
    }


    public int getSupplier_id() {
        return supplier_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public String getProductsNumber() {
        return productsNumber;
    }


    public  String getProductQuantityDiscount() {
        return productQuantityDiscount;
    }

}
