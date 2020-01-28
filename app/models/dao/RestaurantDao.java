package models.dao;

import exceptions.AppException;
import models.Guest;
import models.Restaurant;
import models.dao.connectors.Connector;
import models.dao.daointerfaces.RestaurantDaoInterface;

import javax.inject.Inject;
import java.sql.*;
import java.util.List;

public class RestaurantDao extends DaoBase implements RestaurantDaoInterface {

    @Inject
    public RestaurantDao(Connector connector) {
        super(connector);
    }

    @Override
    public List<Restaurant> getAll() {
        String get = "SELECT * " +
                "FROM restaurants";
        return getRestaurantsFromDb(get);
    }

    @Override
    public void add(Restaurant restaurant) {
        String add = "INSERT INTO restaurants (name, legal_entity, inn, address) VALUES(?,?,?,?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(add)) {
                statement.setString(1, restaurant.getName());
                statement.setString(2, restaurant.getLegalEntity());
                statement.setString(3, restaurant.getInn());
                statement.setString(4, restaurant.getAddress());
                statement.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + add + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        String del = "DELETE FROM restaurants WHERE id=" + id;
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(del);
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + del + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public Restaurant getById(long id) {
        String get = "SELECT * " +
                "FROM restaurants " +
                "WHERE id = " + id;
        try (Connection  connection = connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(get)) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String legalEntity = resultSet.getString("legal_entity");
                String inn = resultSet.getString("inn");
                String address = resultSet.getString("address");
                return new Restaurant(id, name, legalEntity, inn, address);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + get + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public List<Guest> getListById(long id) {
        String get = "SELECT id, first_name, last_name, email, phone " +
                "FROM guests g " +
                "JOIN guestRestRelation gr ON gr.guest_id = g.id " +
                "WHERE gr.rest_id =" + id;
        return getGuestsFromDb(get);
    }

}
