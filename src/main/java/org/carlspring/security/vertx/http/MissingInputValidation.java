package org.carlspring.security.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * In this example, the Vert.x HTTP server expects a user-supplied input parameter called "data" via a query parameter
 * or request body. The input is then converted into a {@link JsonArray} and each element of the {@link JsonArray} is
 * iterated and processed sequentially. The processing involves some complex computations, which might work well with
 * small data, but not for large {@link JsonArray}s.
 *
 * An attacker can exploit this vulnerability by injecting a large {@link JsonArray} as the input parameter,
 * overwhelming the server's processing capabilities. For example, if the attacker sends a request with a large
 * {@link JsonArray} containing thousands or millions of elements, the server will iterate through each element,
 * causing a significant delay or even a DoS situation. As the workload is executed directly on the eventloop, such an
 * attack would violate the <a href="https://vertx.io/docs/vertx-core/java/#golden_rule">Golden Rule of Vert.x<a/>,
 * and block other tasks.
 *
 * To mitigate this vulnerability, you should consider implementing appropriate input validation and limiting the
 * amount of data processed in a single request. You can also offload the processing of the {@link JsonArray} to a worker
 * thread or divide the processing into smaller batches to avoid blocking the event loop and handle large inputs more
 * efficiently.
 */
public class MissingInputValidation extends AbstractVerticle {

    @Override
    public void start() {

        vertx.createHttpServer().requestHandler(req -> {
            String input = req.getParam("data"); // User-supplied input

            // Process the input without validation
            JsonArray jsonArray = new JsonArray(input);

            // Process the input
            JsonObject transformedObject = new JsonObject();
            for (Object element : jsonArray) {
                JsonObject jsonObject = (JsonObject) element;
                transformedObject = performExpensiveProcessing(jsonObject);
            }

            // Return the transform input
            req.response().end(transformedObject.toBuffer());
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

