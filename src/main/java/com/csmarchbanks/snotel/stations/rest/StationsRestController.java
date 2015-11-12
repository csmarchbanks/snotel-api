package com.csmarchbanks.snotel.stations.rest;

import com.csmarchbanks.snotel.stations.impl.StationsService;
import gov.usda.nrcs.wcc.awdbWebService.StationMetaData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for getting metadata about stations
 * Created by cmarchbanks on 11/6/15.
 */
@Path("stations")
public class StationsRestController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StationMetaData> getStations(
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
        return StationsService.getStationsMetadata(stationIds, stateCds, hucs, countyNames,
                minLatitude, maxLatitude, minLongitude, maxLongitude, minElevation, maxElevation, logicalAnd);
    }

    @GET
    @Path("near")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StationMetaData> getStationsNear(
            @QueryParam("lat") BigDecimal latitude,
            @QueryParam("long") BigDecimal longitude,
            @QueryParam("deltaLat") BigDecimal deltaLat,
            @QueryParam("deltaLong") BigDecimal deltaLong,
            @QueryParam("delta") BigDecimal delta
    ){
        deltaLat = deltaLat == null ? delta : deltaLat;
        deltaLong = deltaLong == null ? delta : deltaLong;
        return StationsService.getStationsNear(latitude, longitude, deltaLat, deltaLong);
    }

    @GET
    @Path("{state}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StationMetaData> getStationsByState(@PathParam("state") String state){
        return StationsService.getStationsMetadata(state);
    }
}
