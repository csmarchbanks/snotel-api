package com.csmarchbanks.snotel;

import com.csmarchbanks.snotel.stations.impl.StationsService;
import gov.usda.nrcs.wcc.awdbWebService.AwdbWebService;
import gov.usda.nrcs.wcc.awdbWebService.AwdbWebService_Service;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/snotel/";
    private static AwdbWebService webService;

    public static AwdbWebService getSnotelService(){
        return webService;
    }

    private static void initWebService() {
        try {
            AwdbWebService_Service lookup = new AwdbWebService_Service();

            webService = lookup.getAwdbWebServiceImplPort();
        } catch (Exception e) {
            System.out.println("Problem creating web service client: "
                    + e.getMessage());

        }
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        System.out.println("Initializing AWDB Web Service...");
        long startTime = System.currentTimeMillis();
        initWebService();
        long endTime = System.currentTimeMillis();
        System.out.println("AWDB Web Service initialized in: " + (endTime - startTime) + " ms");

        // pull in all metadata resources on server startup
        System.out.println("Initializing station metadata...");
        startTime = System.currentTimeMillis();
        StationsService.getAllStationsMetadata();
        endTime = System.currentTimeMillis();
        System.out.println("Station metadata initialized in: " + (endTime - startTime) + " ms");

        final ResourceConfig rc = new ResourceConfig().packages("com.csmarchbanks.snotel");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}

