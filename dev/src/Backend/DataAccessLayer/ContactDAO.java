package DataAccessLayer;

import BusinessLayer.Contact;
import BusinessLayer.DataBaseController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private Connection c;
    private static ContactDAO instance = null;

    public static ContactDAO getInstance(){
        if(instance==null){
            instance = new ContactDAO();
        }
        return instance;
    }

    private ContactDAO() {
        this.c = DataBaseController.get_connection();
    }

    public boolean checkContactAvailability(int id) throws SQLException {
        return selectByID(id) == null;
    }

    public ContactDTO selectByID(int id) throws SQLException {
        String sql = "SELECT * FROM Contacts WHERE ID = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ID= resultSet.getInt("ID");
            String Name= resultSet.getString("Name");
            String Phone_number = resultSet.getString("Phone Number");
            String email = resultSet.getString("Email");
            return  new ContactDTO(ID, Name, Phone_number, email);
        } else {
            return null;
        }
    }

    public String insert(ContactDTO contact) throws SQLException {
        if (!checkContactAvailability(contact.getContact_id()))
            return  "Contact already exists in the system.\n";;
        String query = "INSERT INTO Contacts (ID, Name, PhoneNumber, Email) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, contact.getContact_id());
        statement.setString(2, contact.getName());
        statement.setString(3, contact.getPhone_number());
        statement.setString(4, contact.getEmail());
        statement.executeUpdate();
        return "Contact added successfully\n";

    }

    public String update(ContactDTO contact) throws SQLException {
        String query = "UPDATE Contacts SET Name = ?, PhoneNumber = ?, Email = ? WHERE ID = ?";
        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, contact.getContact_id());
            statement.setString(2, contact.getName());
            statement.setString(3, contact.getPhone_number());
            statement.setString(4, contact.getEmail());
            statement.executeUpdate();
            return "Contact updated successfully\n";
        }
    }

    public String delete(int id) throws SQLException {
        String query = "DELETE FROM Contacts WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return "Contact deleted successfully\n";
        }
    }



    public List<Contact> selectAll() throws SQLException {
        String query = "SELECT * FROM Contacts";
        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                int ID= resultSet.getInt("ID");
                String Name= resultSet.getString("Name");
                String Phone_number = resultSet.getString("PhoneNumber");
                String email = resultSet.getString("Email");
                Contact contact= new Contact(ID, Name, Phone_number, email);
                contacts.add(contact);
            }
            return contacts;
        }
    }



    public void deleteData() throws SQLException{
        try (Statement statement = c.createStatement()) {
            String deleteContactsQuery = "DELETE FROM Contacts";
            statement.executeUpdate(deleteContactsQuery);
        }
    }


}
