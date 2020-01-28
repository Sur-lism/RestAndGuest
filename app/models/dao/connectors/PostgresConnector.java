package models.dao.connectors;

import com.typesafe.config.ConfigFactory;
import exceptions.AppException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnector implements Connector {

    public Connection getConnection() {
        Connection connection;
        String url = ConfigFactory.load().getString("db.default.url");
        String user = ConfigFactory.load().getString("db.default.username");
        String pass = ConfigFactory.load().getString("db.default.password");

        try {
            Class.forName(ConfigFactory.load().getString("db.default.driver"));
            connection = DriverManager.getConnection(url, user, pass);
        } catch(SQLException e){
            throw new AppException("Invalid DB-connection with:\n url:" + url + "\n user:" + user + "\n" + e.getMessage(), e);
        } catch(ClassNotFoundException e){
            throw new AppException("JDBC driver is not found.\n" + e.getMessage(), e);
        }
        return connection;
    }

}
