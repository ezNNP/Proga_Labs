import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database_Connector {

    private String url;
    private String user;
    private String password;


    public Database_Connector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            System.out.println("Внатуре, даже здесь сломаете?");
            e.printStackTrace();
        }

        if (connection == null) {
            throw new NullPointerException();
        } else {
            return connection;
        }
    }
}