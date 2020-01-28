import com.google.inject.AbstractModule;
import models.dao.GuestDao;
import models.dao.RestaurantDao;
import models.dao.connectors.Connector;
import models.dao.connectors.PostgresConnector;
import models.dao.daointerfaces.GuestDaoInterface;
import models.dao.daointerfaces.RestaurantDaoInterface;

public class Module extends AbstractModule {
    protected void configure() {
        bind(Connector.class).to(PostgresConnector.class);
        bind(GuestDaoInterface.class).to(GuestDao.class);
        bind(RestaurantDaoInterface.class).to(RestaurantDao.class);
    }
}