package DataAccessLayer;

import BusinessLayer.DataBaseController;
import BusinessLayer.Product1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product1DAO {
    private Connection c;
    private static Product1DAO instance = null;

    public static Product1DAO getInstance(){
        if(instance==null){
            instance = new Product1DAO();
        }
        return instance;
    }

    private Product1DAO() {
        this.c = DataBaseController.get_connection();
    }


    public boolean checkProductAvailability(int id) throws SQLException {
        return selectById(id) == null;
    }

    public void insert(Product1DTO productDTO) throws SQLException {
      //  if(!checkProductAvailability(productDTO.getProduct_id()))
        //    return;
        String sql = "INSERT INTO Product (ProductID, Price, ProductName) VALUES (?, ?, ?)";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, productDTO.getProduct_id());
        statement.setInt(2, productDTO.getList_price());
        statement.setString(3, productDTO.getProduct_name());
        statement.executeUpdate();
    }

    public Product1DTO selectById(int id) throws SQLException {
        String sql = "SELECT * FROM Product WHERE id = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int price = resultSet.getInt("Price");
            String name = resultSet.getString("Product Name");

            return new Product1DTO(id, price, name);
        } else {
            return null;
        }
    }


    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE ProductID = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public List<Product1> selectAll() throws SQLException {
        String query = "SELECT * FROM Product";
        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Product1> products = new ArrayList<>();
            while (resultSet.next()) {
                int ID= resultSet.getInt("ProductID");
                int price= resultSet.getInt("Price");
                String name = resultSet.getString("ProductName");
                Product1 product= new Product1(ID,price, name);
                products.add(product);
            }
            return products;
        }
    }


}
