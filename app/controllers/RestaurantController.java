package controllers;

import exceptions.AppException;
import models.Guest;
import models.Restaurant;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.GuestService;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class RestaurantController extends Controller {

    private RestaurantService restaurantService;

    private GuestService guestService;

    private FormFactory formFactory;

    @Inject
    public RestaurantController(RestaurantService restaurantService, GuestService guestService, FormFactory formFactory) {
        this.restaurantService = restaurantService;
        this.guestService = guestService;
        this.formFactory = formFactory;
    }

    public Result restaurant(long id) {
        Restaurant restaurant = null;
        List<Guest> guestsForRest = Collections.emptyList();
        String errorMessage = "";
        try {
            restaurant = restaurantService.getById(id);
            if (restaurant != null) {
                guestsForRest = restaurantService.getListById(id);
            } else {
                errorMessage = "There's no restaurant with id " + id;
            }
        } catch (AppException e) {
            errorMessage = "Error: " + e.getMessage();
        }
        return ok(views.html.restaurant.render(restaurant, guestsForRest,
                guestService.getAll(), errorMessage));
    }

    public Result restaurants() {
        return ok(views.html.restaurants.render(restaurantService.getAll()));
    }

    public Result addRestDB(Http.Request request) {
        Restaurant restaurant = formFactory.form(Restaurant.class).bindFromRequest(request).get();
        restaurantService.add(restaurant);
        return redirect(routes.RestaurantController.restaurants());
    }

    public Result deleteRestaurant(long id) {
        restaurantService.deleteById(id);
        return restaurants();
    }


    public Result addRelation(play.mvc.Http.Request request){
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        long guestId = Long.parseLong(requestData.get("guestId"));
        long restId = Long.parseLong(requestData.get("restId"));
        restaurantService.addRelation(guestId, restId);
        return redirect(routes.RestaurantController.restaurant(restId));
    }

    public Result deleteRelation(play.mvc.Http.Request request){
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        long guestId = Long.parseLong(requestData.get("guestId"));
        long restId = Long.parseLong(requestData.get("restId"));
        restaurantService.deleteRelation(guestId, restId);
        return redirect(routes.RestaurantController.restaurant(restId));
    }

}
