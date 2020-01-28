package models.dao.daointerfaces;

import models.Guest;
import models.Restaurant;

import java.util.List;

public interface GuestDaoInterface extends Dao<Guest> {

    List<Guest> getAll();

    Guest getById(long id);

    List<Restaurant> getListById(long id);

    void add(Guest guest);

    void deleteById(long id);

    void addRelation(long guestId, long restId);

    void deleteRelation(long guestId, long restId);

}
