package models.dao.daointerfaces;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    T getById(long id);

    void add(T object);

    void deleteById(long id);

    void addRelation(long guestId, long restId);

    void deleteRelation(long guestId, long restId);

}
