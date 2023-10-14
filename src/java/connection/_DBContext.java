package connection;

import dao.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Helper;

public class _DBContext {

    protected Connection connection;

    private String HOSTNAME = "khuathieu.database.windows.net";
    private String PORT = "1433";
    private Properties properties = Helper.getPropertiesByFileName("constant/const.properties");

    public _DBContext() {
        try {
            String url = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";database=" + properties.getProperty("database.name");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, properties.getProperty("database.username"), properties.getProperty("database.password"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(_DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {// tcst
        Properties properties = Helper.getPropertiesByFileName("constant/const.properties");
        System.out.println(properties.getProperty("database.name"));
        System.out.println(new _DBContext().getConnection());
    }

}
