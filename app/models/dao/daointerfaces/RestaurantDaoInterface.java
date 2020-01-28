package models.dao.daointerfaces;

import models.Guest;
import models.Restaurant;

import java.util.List;

public interface RestaurantDaoInterface extends Dao<Restaurant> {

    List<Restaurant> getAll();

    Restaurant getById(long id);

    List<Guest> getListById(long id);

    void add(Restaurant restaurant);

    void deleteById(long id);

    void addRelation(long guestId, long restId);

    void deleteRelation(long guestId, long restId);

}
