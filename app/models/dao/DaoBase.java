package models.dao;

import exceptions.AppException;
import models.Guest;
import models.Restaurant;
import models.dao.connectors.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DaoBase {
    Connector connector;

    DaoBase(Connector connector) {
        this.connector = connector;
    }

    List<Guest> getGuestsFromDb(String selectQuery) {
        List<Guest> guestList = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                long id = resultSet.getLong("id");
                Guest guest = new Guest(id, firstName, lastName, email, phone);
                guestList.add(guest);
            }
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + selectQuery + "\n" + e.getMessage(), e);
        }
        return guestList;
    }

    List<Restaurant> getRestaurantsFromDb(String selectQuery) {
        List<Restaurant> restList = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)){
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String legalEntity = resultSet.getString("legal_entity");
                String inn = resultSet.getString("inn");
                String address = resultSet.getString("address");
                long id = resultSet.getLong("id");
                Restaurant restaurant = new Restaurant(id, name, legalEntity, inn, address);
                restList.add(restaurant);
            }
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + selectQuery + "\n" + e.getMessage(), e);
        }
        return restList;
    }

    /** Implements method from interface Dao */
    public void addRelation(long guestId, long restId) {
        String add = "INSERT INTO guestrestrelation (guest_id, rest_id) VALUES(?,?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(add)) {
            statement.setLong(1, guestId);
            statement.setLong(2, restId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + add + "\n" + e.getMessage(), e);
        }
    }

    /** Implements method from interface Dao */
    public void deleteRelation(long guestId, long restId) {
        String del = "DELETE FROM guestrestrelation WHERE guest_id=" + guestId + " AND rest_id=" + restId;
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(del);
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + del + "\n" + e.getMessage(), e);
        }
    }
}
