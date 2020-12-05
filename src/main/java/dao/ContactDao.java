package dao;

import entities.Contact;

import java.util.List;

public interface ContactDao {
    List<Contact> getContacts();
    void addContact(Contact contact);
}
