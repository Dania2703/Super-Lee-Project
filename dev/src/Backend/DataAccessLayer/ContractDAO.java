package DataAccessLayer;

import BusinessLayer.Contract;
import BusinessLayer.DataBaseController;
import BusinessLayer.Product1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractDAO {
    private Connection c;


    public static ContractDAO getInstance() {
        if(instance == null){
            instance = new ContractDAO();
        }
        return instance;
    }

    private static ContractDAO instance = new ContractDAO();

    private ContractDAO() {
        this.c = DataBaseController.get_connection();
    }


    public boolean checkContractAvailability(String name) throws SQLException {
        return selectByName(name) == null;
    }

    public void insert(ContractDTO contractDTO) throws SQLException {
       // if(!checkContractAvailability(contractDTO.getContract_name()))
         //   return;
        String sql = "INSERT INTO Contract (SupplierID, ContractName, Scheduled, ProductsNumbers, ProductQuantityDiscount) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, contractDTO.getSupplier_id());
        statement.setString(2, contractDTO.getContract_name());
        statement.setBoolean(3, contractDTO.isScheduled());
        statement.setString(4, contractDTO.getProductsNumber().toString());
        statement.setString(5, contractDTO.getProductQuantityDiscount().toString());
        statement.executeUpdate();
    }

    public ContractDTO selectByName(String name) throws SQLException {
        String sql = "SELECT * FROM Contract WHERE Contract Name = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(2, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int supplierID = resultSet.getInt("SupplierID");
            String contractName = resultSet.getString("ContractName");
            Boolean sheduled = resultSet.getBoolean("Scheduled");
            String products_numbers = resultSet.getString("ProductsNumber");
            String products_discount = resultSet.getString("ProductQuantityDiscount");
            return new ContractDTO(contractName, supplierID, sheduled, products_numbers,products_discount);
        } else {
            return null;
        }
    }


    public void delete(String name) throws SQLException {
        String sql = "DELETE FROM Contract WHERE ContractName = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(1, name);
        statement.executeUpdate();
    }

    public List<Contract> selectAll() throws SQLException {
        String query = "SELECT * FROM Contract";
        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Contract> contracts = new ArrayList<>();
            while (resultSet.next()) {
                int supplierID = resultSet.getInt("SupplierID");
                String contractName = resultSet.getString("ContractName");
                Boolean sheduled = resultSet.getBoolean("Scheduled");
                String products_numbers = resultSet.getString("ProductsNumbers");
                String products_discount = resultSet.getString("ProductQuantityDiscount");
                Contract contract= new Contract(contractName, supplierID, sheduled, products_numbers, products_discount);
                contracts.add(contract);
            }
            return contracts;
        }
    }

}
