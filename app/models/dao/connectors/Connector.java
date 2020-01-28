package models.dao.connectors;

import java.sql.Connection;

public interface Connector {

    Connection getConnection();

}
