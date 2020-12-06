import com.google.gson.Gson;
import dao.ContactBookDao;
import dao.ContactDao;
import db.DBConnect;

public class Container {

    private static Container container;

    public static Container getInstance() {
        return container == null ? container = new Container() : container;
    }


    private DBConnect dbConnect;
    private ContactDao contactDao;
    private Gson gson;

    private Container() {
        dbConnect = new DBConnect(
                "jdbc:postgresql://localhost:5432/contact_book",
                "postgres",
                ""
        );
        contactDao = new ContactBookDao(dbConnect);
        gson = new Gson();
    }

    public ContactDao getContactDao() {
        return contactDao;
    }

    public Gson getGson() {
        return gson;
    }
}
