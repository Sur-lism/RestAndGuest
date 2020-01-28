package services;

import models.Guest;
import models.Restaurant;
import models.dao.daointerfaces.GuestDaoInterface;

import javax.inject.Inject;
import java.util.List;

public class GuestService {

    private GuestDaoInterface guestDao;

    @Inject
    public GuestService(GuestDaoInterface guestDao) {
        this.guestDao = guestDao;
    }

    public List<Guest> getAll() {
        return guestDao.getAll();
    }

    public void add(Guest guest) {
        guestDao.add(guest);
    }

    public void deleteById(long id) {
        guestDao.deleteById(id);
    }

    public Guest getById(long id) {
        return guestDao.getById(id);
    }

    public List<Restaurant> getListById(long id) {
        return guestDao.getListById(id);
    }

    public void addRelation(long guestId, long restId) {
        guestDao.addRelation(guestId, restId);
    }

    public void deleteRelation(long guestId, long restId) {
        guestDao.deleteRelation(guestId, restId);
    }

}
