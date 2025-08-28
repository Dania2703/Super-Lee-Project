package DataAccessLayer;

import BusinessLayer.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDAO {
    private Connection c;
    private static SuppliersDAO instance = null;
    public static SuppliersDAO getInstance(){
        if (instance==null){
            instance = new SuppliersDAO();
        }
        return instance;
    }

    private SuppliersDAO(){
        this.c= DataBaseController.get_connection();
    }

    public boolean checkSupplierAvailability(String name) throws SQLException {
        return selectByName(name) == 0;
    }

    public int selectByName(String name) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE name = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(2, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("ID");
            return id;
        } else {
            return 0;
        }
    }


    public String  insert(SuppliersDTO suppliersDTO) throws SQLException {
       // if (!checkSupplierAvailability(suppliersDTO.getName()))
         //     return  "Supplier already exists in the system.\n";;
        c= DataBaseController.connect();
        PreparedStatement statement = null;
        try {

            statement = c.prepareStatement("INSERT INTO Supplier(ID,Name,PhoneNumber,SupplierType,BankAccount,PaymentType)  VALUES (?, ?, ?, ?, ?, ?)");

           statement.setInt(1, suppliersDTO.getSupplier_id());
            statement.setString(2, suppliersDTO.getName());
            statement.setString(3, suppliersDTO.getPhoneNumber());
            statement.setInt(4, suppliersDTO.getSupplierTypeInt());
            statement.setString(5, suppliersDTO.getBankAccount());
            statement.setString(6, suppliersDTO.getPaymentType());
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return "s";
    }

    public String update(SuppliersDTO suppliersDTO) throws SQLException {

        String sql = "UPDATE Supplier SET Name = ?, PhoneNumber = ?, SupplierType = ?, BankAccount = ?, PaymentType = ?" +
                "WHERE ID = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, suppliersDTO.getSupplier_id());
            statement.setString(2, suppliersDTO.getName());
            statement.setString(3, suppliersDTO.getPhoneNumber());
            statement.setInt(4, suppliersDTO.getSupplierTypeInt());
            statement.setString(5, suppliersDTO.getBankAccount());
            statement.setString(6, suppliersDTO.getPaymentType());
            statement.executeUpdate();
            return "Supplier updated successfully\n";
        }
    }

    public String delete_Supplier(int ID) throws SQLException {
        String deleteSupplierQuery = "DELETE FROM Supplier WHERE ID = ?";
        try (PreparedStatement deleteSupplierStatement = c.prepareStatement(deleteSupplierQuery)) {
            deleteSupplierStatement.setInt(1, ID);
            deleteSupplierStatement.executeUpdate();


        }
        String deleteContactQuery = "DELETE FROM Supplier_Contact WHERE SupplierID = ?";
        try (PreparedStatement deleteSupplierStatement = c.prepareStatement(deleteContactQuery)) {
            deleteSupplierStatement.setInt(1, ID);
            deleteSupplierStatement.executeUpdate();
        }
        return "Supplier deleted successfully\n";

    }


    public List<Supplier> selectAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";
        PreparedStatement statement = c.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Supplier> suppliers = new ArrayList<>();
        while (resultSet.next()) {
            int id= resultSet.getInt("ID");
            String name = resultSet.getString("Name");
            String phoneNumber = resultSet.getString("PhoneNumber");
            int supplierType = resultSet.getInt("SupplierType");
            String bankAccount = resultSet.getString("BankAccount");
            String paymentType = resultSet.getString("PaymentType");

            Supplier supplier=new Supplier(id, name,phoneNumber,supplierType,bankAccount,paymentType, new ArrayList<>());
          //  SuppliersDTO supplier = new SuppliersDTO( name, phoneNumber,supplierType,bankAccount,paymentType );
            suppliers.add(supplier);
        }

        return suppliers;
    }


    public String insert_supplier_contact(int supplier_id, int contact_id) throws SQLException{
        String query = "INSERT INTO Supplier_Contact (SupplierID,ContactID)" + "VALUES (?,?)";
        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, supplier_id);
            statement.setInt(2, contact_id);
            statement.executeUpdate();
            return "Supplier Contact added successfully\n";
        }

    }

    public String delete_supplier_contact(int supp_id, int contact_id) throws SQLException{
        String query = "DELETE FROM Supplier_Contact WHERE SupplierID = ? AND ContactID = ?";
        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, supp_id);
            statement.setInt(2, contact_id);
            statement.executeUpdate();
        }
        return "Supplier Contact deleted successfully\n";
    }



}
