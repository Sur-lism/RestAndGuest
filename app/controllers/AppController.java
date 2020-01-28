package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class AppController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

}

