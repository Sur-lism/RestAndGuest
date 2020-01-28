package models.dao;

import exceptions.AppException;
import models.Guest;
import models.Restaurant;
import models.dao.connectors.Connector;
import models.dao.daointerfaces.GuestDaoInterface;

import javax.inject.Inject;
import java.sql.*;
import java.util.List;

public class GuestDao extends DaoBase implements GuestDaoInterface {

    @Inject
    public GuestDao(Connector connector) {
        super(connector);
    }

    @Override
    public List<Guest> getAll() {
        String get = "SELECT * " +
                "FROM guests ";
        return getGuestsFromDb(get);
    }

    @Override
    public void add(Guest guest) {
        String add = "INSERT INTO guests (first_name, last_name, email, phone) VALUES(?,?,?,?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(add)) {
                statement.setString(1, guest.getFirstName());
                statement.setString(2, guest.getLastName());
                statement.setString(3, guest.getEmail());
                statement.setString(4, guest.getPhone());
                statement.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + add + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        String del = "DELETE FROM guests WHERE id=" + id;
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(del);
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + del + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public Guest getById(long id) {
        String get = "SELECT * " +
                "FROM guests " +
                "WHERE id = " + id;
        try (Connection  connection = connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(get)) {
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                return new Guest(id, firstName, lastName, email, phone);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new AppException("Connection failed or wrong query:\n" + get + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public List<Restaurant> getListById(long id) {
        String get = "SELECT id, name, legal_entity, address, inn " +
                "FROM restaurants r " +
                "JOIN guestRestRelation gr ON gr.rest_id = r.id " +
                "WHERE gr.guest_id =" + id;
        return getRestaurantsFromDb(get);
    }

}
