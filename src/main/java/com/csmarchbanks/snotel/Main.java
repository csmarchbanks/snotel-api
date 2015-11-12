package com.csmarchbanks.snotel;

import gov.usda.nrcs.wcc.awdbWebService.AwdbWebService;
import gov.usda.nrcs.wcc.awdbWebService.AwdbWebService_Service;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceFeature;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

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
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        initWebService();
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

