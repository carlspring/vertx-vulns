package org.carlspring.security.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

/**
 * @author carlspring
 */
public class InsecureWebClient2
        extends AbstractVerticle
{

    @Override
    public void start()
    {
        Vertx vertx = Vertx.vertx();

        // Create the web client and enable SSL, but trust all client certificates anyway
        WebClient client = WebClient.create(vertx,
                                            new WebClientOptions().setConnectTimeout(60)
                                                                  .setSsl(true)
                                                                  // Production code cannot trust all certificates blindly
                                                                  .setTrustAll(true));

        client.get(8080, "localhost", "/").send(ar -> {
            if (ar.succeeded())
            {
                System.out.println("Got HTTP response with status " + ar.result().statusCode());
            }
            else
            {
                ar.cause().printStackTrace();
            }
        });
    }

}
