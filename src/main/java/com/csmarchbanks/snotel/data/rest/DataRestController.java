package com.csmarchbanks.snotel.data.rest;

import com.csmarchbanks.snotel.data.impl.DataService;
import com.csmarchbanks.snotel.stations.impl.StationsService;
import gov.usda.nrcs.wcc.awdbWebService.Data;
import gov.usda.nrcs.wcc.awdbWebService.StationMetaData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
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
    public List<Data> getDataForQuery(
            @QueryParam("triplet") List<String> stationIds,
            @QueryParam("state") List<String> stateCds,
            @QueryParam("huc") List<String> hucs,
            @QueryParam("county") List<String> countyNames,
            @QueryParam("minLat") BigDecimal minLatitude,
            @QueryParam("maxLat") BigDecimal maxLatitude,
            @QueryParam("minLong") BigDecimal minLongitude,
            @QueryParam("maxLong") BigDecimal maxLongitude,
            @QueryParam("minEl") BigDecimal minElevation,
            @QueryParam("maxEl") BigDecimal maxElevation,
            @DefaultValue("true") @QueryParam("logicalAnd") Boolean logicalAnd
    ){
        return DataService.getData(stationIds, stateCds, hucs, countyNames,
                minLatitude, maxLatitude, minLongitude, maxLongitude, minElevation, maxElevation, logicalAnd);
    }
}
