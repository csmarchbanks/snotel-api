package com.csmarchbanks.snotel.data.rest;

import com.csmarchbanks.snotel.data.impl.DataService;
import gov.usda.nrcs.wcc.awdbWebService.Data;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Controller for getting data from stations
 * Created by cmarchbanks on 11/11/15.
 */
@Path("data")
public class DataRestController {

    @GET
    @Path("station/{stationTriplet}")
    @Produces(MediaType.APPLICATION_JSON)
    public Data getDataForStation(@PathParam("stationTriplet") String stationTriplet) {
        return DataService.getData(stationTriplet);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getDataForStations(
            @QueryParam("triplet") List<String> stationTriplets
    ) {
        return DataService.getData(stationTriplets);
    }
}
