package com.csmarchbanks.snotel.stations.impl;

import gov.usda.nrcs.wcc.awdbWebService.HeightDepth;
import gov.usda.nrcs.wcc.awdbWebService.StationMetaData;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.csmarchbanks.snotel.Main.getSnotelService;

/**
 * Implementation of getting metadata about stations
 * Created by cmarchbanks on 11/8/15.
 */
public class StationsService {
    private static List<StationMetaData> stationMetaDatas;

    public static List<String> getStationTriplets(
            List<String> stationIds,
            List<String> stateCds,
            List<String> hucs,
            List<String> countyNames,
            BigDecimal minLatitude,
            BigDecimal maxLatitude,
            BigDecimal minLongitude,
            BigDecimal maxLongitude,
            BigDecimal minElevation,
            BigDecimal maxElevation,
            List<HeightDepth> heightDepths,
            boolean logicalAnd
    ){
        List<String> networkCds = Arrays.asList("SNTL");
        List<Integer> ordinals = Arrays.asList(1);
        List<String> elementCodes = Arrays.asList("WTEQ");

        return getSnotelService().getStations(stationIds,
                stateCds, networkCds, hucs, countyNames, minLatitude,
                maxLatitude, minLongitude, maxLongitude, minElevation,
                maxElevation, elementCodes, ordinals, heightDepths, logicalAnd);
    }

    public static List<String> getStationTripletsByStates(List<String> stateCds){
        List<String> stationIds = null;
        List<String> hucs = null;
        List<String> countyNames = null;
        BigDecimal minLatitude = null;
        BigDecimal maxLatitude = null;
        BigDecimal minLongitude = null;
        BigDecimal maxLongitude = null;
        BigDecimal minElevation = null;
        BigDecimal maxElevation = null;
        List<HeightDepth> heightDepths = null;
        return getStationTriplets(stationIds, stateCds, hucs, countyNames, minLatitude,
                maxLatitude, minLongitude, maxLongitude, minElevation, maxElevation, heightDepths, true);
    }

    public static List<StationMetaData> getStationsMetadata(String state){

        List<String> stateCds = null;
        if(null != state && !state.isEmpty()){
            stateCds = Arrays.asList(state);
        }

        return getSnotelService().getStationMetadataMultiple(getStationTripletsByStates(stateCds));
    }
}