import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class MyWorkWithDb {
    static String userName = "postgres";
    static String password = "";
    static String connectionURL = "jdbc:postgresql://localhost:5432/contact_book";

    // кеширование и удаление 2 элемента в базе
/*        ResultSet resultSet = getData();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("NAME")
                    + " : " + resultSet.getString("PhoneNumber")
                    + " Date -> " + resultSet.getDate("Date"));
        }
        System.out.println("----------------------------------------------------");
        CachedRowSet cachedRowSet = (CachedRowSet) resultSet;
        cachedRowSet.setTableName("ContactsBook");
        cachedRowSet.absolute(2);
        cachedRowSet.deleteRow();
        cachedRowSet.beforeFirst();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("NAME")
                    + " : " + resultSet.getString("PhoneNumber")
                    + " Date -> " + resultSet.getDate("Date"));
        }
        cachedRowSet.acceptChanges(DriverManager.getConnection(connectionURL, userName, password));*/

    // защищенноая запись в БД
      /*   try (Connection connection = DriverManager.getConnection(connectionURL, userName, password);
             Statement statement = connection.createStatement()) {

            // удаление таблицы из БД
            //  statement.executeUpdate("DROP TABLE ContactsBook");


            statement.executeUpdate("CREATE TABLE ContactsBook(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR (50), PhoneNumber VARCHAR (30), Date DATE)");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into ContactsBook(NAME ,PhoneNumber,Date) VALUES ('Dmitriy','123456',?)");
            preparedStatement.setDate(1,new Date(1606849197399L));
            preparedStatement.execute();*/


    // не защещенная запись,обновление и удаление в БД
          /*  statement.executeUpdate("INSERT INTO ContactsBook (NAME,PhoneNumber) values ('Dmitriy','123456')");
            statement.executeUpdate("insert into ContactsBook set NAME = 'Vasiliy',  PhoneNumber = '987654'");
            statement.executeUpdate("UPDATE ContactsBook SET Date =  '2020-12-25'  WHERE Id = 3");
            statement.executeUpdate("DELETE FROM ContactsBook WHERE ID = 2");*/

//        обычный вывод в консоль с БД
     /*       ResultSet resultSet = statement.executeQuery("select * from ContactsBook");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("NAME")
                        + " : " + resultSet.getString("PhoneNumber")
                        + " Date -> " + resultSet.getDate("Date"));
            }*/
//           scroll
            /*Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet1 = statement1.executeQuery("select * from ContactsBook");
            while (resultSet1.next()) {
                System.out.println(resultSet1.getInt("id") + " " + resultSet1.getString("NAME")
                        + " : " + resultSet1.getString("PhoneNumber")
                        + " Date -> " + resultSet1.getDate("Date"));
            }
            System.out.println("-----------------------------------------------------------------------------------------");
            while (resultSet1.previous()) {
                System.out.println(resultSet1.getInt("id") + " " + resultSet1.getString("NAME")
                        + " : " + resultSet1.getString("PhoneNumber")
                        + " Date -> " + resultSet1.getDate("Date"));
            }*/

    // Добавление в БД
  /*          Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet2 = statement2.executeQuery(" SELECT * FROM  ContactsBook");

            resultSet2.moveToInsertRow();
            resultSet2.updateString("NAME", "Vladimir");
            resultSet2.updateString("PhoneNumber","963258");
            resultSet2.updateString("Date","2020-12-12");
            resultSet2.insertRow();
            while (resultSet2.next()) {
                System.out.println(resultSet2.getInt("id") + " " + resultSet2.getString("NAME")
                        + " : " + resultSet2.getString("PhoneNumber")
                        + " Date -> " + resultSet2.getDate("Date"));
            }*/


    static ResultSet getData() throws SQLException {

        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS ContactsBook");

            statement.executeUpdate("CREATE TABLE ContactsBook(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR (50), PhoneNumber VARCHAR (30), Date DATE)");
            statement.execute("insert into ContactsBook(NAME ,PhoneNumber,Date) VALUES ('Dmitriy','123456','2020-12-1')", Statement.RETURN_GENERATED_KEYS);
            statement.execute("insert into ContactsBook(NAME ,PhoneNumber,Date) VALUES ('Vladimir','987654','2020-12-2')", Statement.RETURN_GENERATED_KEYS);
            statement.execute("insert into ContactsBook(NAME ,PhoneNumber,Date) VALUES ('Artur','753159','2020-12-3')", Statement.RETURN_GENERATED_KEYS);

            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();


            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement1.executeQuery(" SELECT * FROM  ContactsBook");
            cachedRowSet.populate(resultSet);
            return cachedRowSet;
        }
    }
}
