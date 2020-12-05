import com.google.gson.Gson;
import dao.ContactDao;
import entities.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class MainServlet extends JsonServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContactDao contactDao = Container.getInstance().getContactDao();
        List<Contact> contacts = contactDao.getContacts();
        jsonResponse(resp,contacts);
    }


}



























