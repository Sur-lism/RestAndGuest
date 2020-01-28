package controllers;

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
import java.util.List;

import static java.util.Collections.emptyList;

public class GuestController extends Controller {

    private GuestService guestService;

    private RestaurantService restaurantService;

    private FormFactory formFactory;

    @Inject
    public GuestController(GuestService guestService, RestaurantService restaurantService, FormFactory formFactory) {
        this.guestService = guestService;
        this.restaurantService = restaurantService;
        this.formFactory = formFactory;
    }

    public Result guest(long id) {
        Guest guest = null;
        List<Restaurant> restaurantsForGuest = emptyList();
        List<Restaurant> allRestaurants = emptyList();
        try {
            guest = guestService.getById(id);
            if (guest == null) {
                return notFound(views.html.guest.render(null, emptyList(), emptyList(),
                        "There's no guest with id " + id));
            }
            restaurantsForGuest = guestService.getListById(id);
            allRestaurants = restaurantService.getAll();
            return ok(views.html.guest.render(guest, restaurantsForGuest,
                    allRestaurants, ""));
        } catch (Exception e) {
            return internalServerError(views.html.guest.render(guest, restaurantsForGuest, allRestaurants,
                    "Error: " + e.getMessage()));
        }
    }

    public Result guests() {
        try {
            return ok(views.html.guests.render(guestService.getAll()));
        } catch (Exception e) {
            // TODO: show error message
            return internalServerError(views.html.guests.render(emptyList()));
        }
    }

    // TODO: 1. check guest is correct - return badRequest
    public Result addGuestDB(Http.Request request) {
        Guest guest = null;
        try {
            guest = formFactory.form(Guest.class).bindFromRequest(request).get();
        } catch (Exception e) {
            // TODO: show error message
            return badRequest(views.html.guests.render(guestService.getAll()));
        }
        try {
            guestService.add(guest);
        } catch (Exception e) {
            // TODO: show error message
            return redirect(routes.GuestController.guests());
        }
        return redirect(routes.GuestController.guests());
    }

    public Result deleteGuest(long id) {
        guestService.deleteById(id);
        return guests();
    }

    public Result addRelation(play.mvc.Http.Request request){
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        long guestId = Long.parseLong(requestData.get("guestId"));
        long restId = Long.parseLong(requestData.get("restId"));
        guestService.addRelation(guestId, restId);
        return redirect(routes.GuestController.guest(guestId));
    }

    public Result deleteRelation(play.mvc.Http.Request request){
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        long guestId = Long.parseLong(requestData.get("guestId"));
        long restId = Long.parseLong(requestData.get("restId"));
        guestService.deleteRelation(guestId, restId);
        return redirect(routes.GuestController.guest(guestId));
    }

}
