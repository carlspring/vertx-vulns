package org.carlspring.security.sql;

import java.io.IOException;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;

/**
 * @author carlspring
 */
public class SqlInjectionExample
{

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        // Define the JDBC connection options
        JsonObject jdbcConfig = new JsonObject().put("url", "jdbc:postgresql://localhost:5432/mydatabase")
                                                .put("driver_class", "org.postgresql.Driver")
                                                .put("user", "myuser")
                                                .put("password", "mypassword");

        // Create an SQLClient instance using the JDBCClient
        JDBCClient jdbcClient = JDBCClient.createShared(vertx, jdbcConfig);
        SQLClient sqlClient = jdbcClient;

        HttpServerRequest request = null; // Simulating user-controlled input

        // Extracting user input from HTTP request parameter
        String userInput = request.getParam("userInput");

        // Constructing SQL query using user input
        String sqlQuery = "SELECT * FROM users WHERE username = '" + userInput + "'";

        // Executing SQL query using Vert.x SQLClient
        sqlClient.query(sqlQuery, result -> {
            if (result.succeeded())
            {
                // Handle query result
            }
            else
            {
                // Handle query failure
            }
        });

        // Executing a blocking operation with user input
        vertx.executeBlocking(future -> {
            String command = "ls " + userInput;
            try
            {
                // Execute command
                Process process = Runtime.getRuntime().exec(command);
                // ... Process command output
            }
            catch (IOException e)
            {
                // Handle command execution failure
            }
        }, result -> {
            if (result.succeeded())
            {
                // Handle blocking operation result
            }
            else
            {
                // Handle blocking operation failure
            }
        });
    }

}
