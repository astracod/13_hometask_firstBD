import dao.ContactDao;
import dto.AddContactRequest;
import dto.StatusResponse;
import entities.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class MainServlet extends JsonServlet {

    private ContactDao contactDao = Container.getInstance().getContactDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        List<Contact> contacts = contactDao.getContacts();
        jsonResponse(resp, contacts);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        StatusResponse statusResponse = new StatusResponse();

        try {
            AddContactRequest addContactRequest = jsonRequest(req, AddContactRequest.class);
            Contact contact = new Contact(null, addContactRequest.getName(), addContactRequest.getPhone());
            contactDao.addContact(contact);
            statusResponse.setStatus(StatusResponse.Status.OK);
        } catch (Exception e) {
            statusResponse.setStatus(StatusResponse.Status.FAIL);
            statusResponse.setMessage(e.getMessage());
        }

        jsonResponse(resp,statusResponse);
    }
}



























