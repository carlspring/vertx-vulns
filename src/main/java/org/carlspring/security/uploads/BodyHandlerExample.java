package org.carlspring.security.uploads;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BodyHandlerExample {
    public static void main(String[] args) {
        // Unlimited
        BodyHandler bodyHandlerUnlimited = BodyHandler.create();
        // 500 MB could result in HEAP problems
        BodyHandler bodyHandlerLimitToHigh = BodyHandler.create().setBodyLimit(1024 * 1024 * 500);
        // 50 MB or less is better
        BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(1024 * 1024 * 50);

        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        Route route = router.route("/*");
        route.handler(bodyHandler);
        route.handler(rtx -> {
            String body = rtx.body().asString();
            rtx.response().end(body);
        });
        vertx.createHttpServer().requestHandler(router).listen(8080).onFailure(e -> {
            e.printStackTrace();
            System.exit(-1);
        }).onSuccess(server -> System.out.println("Server has been started at port 8080"));
    }
}
