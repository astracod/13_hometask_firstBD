package dao;

import db.DBConnect;
import entities.Contact;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ContactBookDao implements ContactDao {

    public static final String SELECT_ALL = "SELECT id, name , phone FROM contacts";
    public static final String SELECT_BY_NAME = "SELECT id, name , phone FROM contacts WHERE name LIKE ?";
    public static final String INSERT_CONTACT = "INSERT INTO contacts (name, phone) VALUES (?,?)";
    private static final String DELETE_BY_ID = "DELETE FROM contacts WHERE id=?";
    private final DBConnect dbConnect;


    @Override
    public List<Contact> getContacts() {

        try (Connection connection = dbConnect.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            return resultSetToListContact(resultSet);
        } catch (SQLException throwables) {
            System.out.println(" ERROR : " + throwables.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void addContact(Contact contact) {

        try (Connection connection = dbConnect.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_CONTACT);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getNumber());
            statement.execute();
        } catch (SQLException throwables) {
            System.out.println(" ERROR : " + throwables.getMessage());
        }
    }

    @Override
    public List<Contact> startsWith(String similarInContacts) {
        try (Connection connection = dbConnect.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
            statement.setString(1, similarInContacts + "%");
            ResultSet resultSet = statement.executeQuery();
            return resultSetToListContact(resultSet);
        } catch (SQLException throwables) {
            System.out.println(" ERROR : " + throwables.getMessage());
            return new ArrayList<>();
        }
    }



    @Override
    public void deleteContact(Integer contactId) {
        try(Connection connection = dbConnect.getConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1,contactId);
            statement.execute();
        } catch (SQLException throwables) {
            System.out.println(" ERROR : " + throwables.getMessage());
        }
    }

    private List<Contact> resultSetToListContact(ResultSet resultSet) throws SQLException {
        List<Contact> contacts = new ArrayList<>(resultSet.getFetchSize());

        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getInt("id"));
            contact.setName(resultSet.getString("name"));
            contact.setNumber(resultSet.getString("phone"));
            contacts.add(contact);
        }
        return contacts;
    }
}
