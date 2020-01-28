package services;

import models.Guest;
import models.Restaurant;
import models.dao.daointerfaces.RestaurantDaoInterface;

import javax.inject.Inject;
import java.util.List;

public class RestaurantService {

    private RestaurantDaoInterface restaurantDao;

    @Inject
    public RestaurantService(RestaurantDaoInterface restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    public List<Restaurant> getAll() {
        return restaurantDao.getAll();
    }

    public void add(Restaurant restaurant) {
        restaurantDao.add(restaurant);
    }

    public void deleteById(long id) {
        restaurantDao.deleteById(id);
    }

    public Restaurant getById(long id) {
        return restaurantDao.getById(id);
    }

    public List<Guest> getListById(long id) {
        return restaurantDao.getListById(id);
    }

    public void addRelation(long guestId, long restId) {
        restaurantDao.addRelation(guestId, restId);
    }

    public void deleteRelation(long guestId, long restId) {
        restaurantDao.deleteRelation(guestId, restId);
    }
}
