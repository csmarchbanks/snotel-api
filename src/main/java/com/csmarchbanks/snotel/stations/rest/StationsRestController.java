package com.csmarchbanks.snotel.stations.rest;

import com.csmarchbanks.snotel.stations.impl.Stations;
import gov.usda.nrcs.wcc.awdbWebService.Data;
import gov.usda.nrcs.wcc.awdbWebService.StationMetaData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by cmarchbanks on 11/6/15.
 */
@Path("stations")
public class StationsRestController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getStations(){
        return Stations.getStations(null);
    }

    @GET
    @Path("{state}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getStationsByState(@PathParam("state") String state){
        return Stations.getStations(state);
    }
}
