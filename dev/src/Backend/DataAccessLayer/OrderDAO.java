package DataAccessLayer;

import BusinessLayer.DataBaseController;
import BusinessLayer.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection c;
    private static OrderDAO instance = null;
    public static OrderDAO getInstance(){
        if (instance==null){
            instance = new OrderDAO();
        }
        return instance;
    }

    private OrderDAO(){
        this.c= DataBaseController.get_connection();
    }

    public void insert(OrderDTO orderDTO) throws SQLException {
        c= DataBaseController.connect();
        PreparedStatement statement = null;
        try {
            //statement = c.prepareStatement("INSERT INTO Supplier(ID,Name,PhoneNumber,SupplierType,BankAccount,PaymentType)  VALUES (?, ?, ?, ?, ?, ?)");


            statement = c.prepareStatement("INSERT INTO Order1(orderID,supplierID,ProductsAndAmount,date) VALUES (?, ?, ?, ?)");
            statement.setInt(1, orderDTO.getOrder_id());
            statement.setInt(2, orderDTO.getSupplier());
            statement.setString(3, orderDTO.getProductsAndAmount());
            statement.setString(4, orderDTO.getDate());
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        /*
        String query = "INSERT INTO Order (orderID, supplierID, ProductsAndAmount, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, order.getOrder_id());
            statement.setInt(2, order.getSupplier());
            statement.setString(3, order.getProductsAndAmount());
            statement.setString(4, order.getDate());
            statement.executeUpdate();
        }

         */
    }

    public boolean checkOrderAvailability(int id) throws SQLException {
        return selectByID(id) == 0;
    }

    public int selectByID(int id) throws SQLException {
        String sql = "SELECT * FROM Order1 WHERE orderID = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ID = resultSet.getInt("ID");
            return ID;
        } else {
            return 0;
        }
    }

    public String update(OrderDTO orderDTO) throws SQLException {

        String sql = "UPDATE Order1 SET supplierID = ?, ProductsAndAmount = ?, date = ?" +
                "WHERE orderID = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, orderDTO.getOrder_id());
            statement.setInt(2, orderDTO.getSupplier());
            statement.setString(3, orderDTO.getProductsAndAmount());
            statement.setString(4, orderDTO.getDate());

            statement.executeUpdate();
            return "order updated successfully\n";
        }
    }

    public String delete_Order(int ID) throws SQLException {
        String deleteOrderQuery = "DELETE FROM Order1 WHERE orderID = ?";
        try (PreparedStatement deleteSupplierStatement = c.prepareStatement(deleteOrderQuery)) {
            deleteSupplierStatement.setInt(1, ID);
            deleteSupplierStatement.executeUpdate();
            return "Supplier deleted successfully\n";
        }

    }

    public List<Order> selectAll() throws SQLException {
        String sql = "SELECT * FROM Order1";
        PreparedStatement statement = c.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("orderID");
            int supplierID = resultSet.getInt("supplierID");
            String productsAndAmount = resultSet.getString("ProductsAndAmount");
            String date = resultSet.getString("date");
            Order order = new Order(id,supplierID,productsAndAmount,date);
            orders.add(order);
        }

        return orders;
    }
}
