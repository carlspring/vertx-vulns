package org.carlspring.security.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.OAuth2WebClientOptions;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.auth.oauth2.OAuth2Auth;
import io.vertx.reactivex.ext.auth.oauth2.providers.GithubAuth;
import io.vertx.reactivex.ext.web.client.OAuth2WebClient;
import io.vertx.reactivex.ext.web.client.WebClient;

/**
 * @author carlspring
 */
public class InsecureOAuth2WebClient
        extends AbstractVerticle
{

    @Override
    public void start()
            throws Exception
    {
        Vertx vertx = Vertx.vertx();

        JsonObject config = vertx.getOrCreateContext().config();

        // This WebClient instance has no SSL/TLS enabled.
        WebClient client = WebClient.create(vertx,
                                            new WebClientOptions().setConnectTimeout(60));

        // Create the web client and enable SSL/TLS with a trust store anyway...
        final OAuth2Auth oAuth2Auth = GithubAuth.create(vertx,
                                                        config.getString("clientID"),
                                                        config.getString("clientSecret"));

        OAuth2WebClient oAuth2WebClient = OAuth2WebClient.create(
                client,
                oAuth2Auth,
                // The OAuth2WebClientOptions do not:
                // - Set a leeway for the expiration of the token.
                // - Set a timeout for the token request.
                // as outlined in the documentation:
                // https://vertx.io/docs/vertx-web-client/java/#_oauth2_security
                new OAuth2WebClientOptions());

        oAuth2WebClient.get(8080, "localhost", "/").send(ar -> {
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
