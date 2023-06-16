package org.carlspring.security.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * In this updated code, the executeBlocking method is used to offload the processing of each JsonObject to a worker
 * thread, ensuring that it doesn't block the event loop. The expensive transformation logic is still performed, but
 * now it's executed on a worker.
 */
public class SecureEventLoopWorkloads extends AbstractVerticle {

    private static final int MAX_INPUT_SIZE = 1_000; // Maximum allowed input size

    @Override
    public void start() {

        vertx.createHttpServer().requestHandler(req -> {
            String input = req.getParam("data"); // User-supplied input

            // Execute processing on a worker
            vertx.executeBlocking(promise -> {
                // Transform the input without validation
                JsonArray jsonArray = new JsonArray(input);

                // Process the input
                JsonObject transformedObject = new JsonObject();
                for (Object jsonObject : jsonArray) {
                    transformedObject = performExpensiveProcessing((JsonObject) jsonObject);
                }

                promise.complete(transformedObject);
            }, result -> {
                if (result.succeeded()) {
                    req.response().end(result.result().toString());
                } else {
                    req.response().setStatusCode(500).end("Internal Server Error");
                }
            });
        }).listen(8080);
    }

    private JsonObject performExpensiveProcessing(JsonObject jsonObject) {
        // Expensive transformation logic here; simulated with a sleep.
        // This can include complex computations, database queries, network calls, etc.
        try {
            Thread.sleep(100); // Simulating processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}