package com.csmarchbanks.snotel.stations.impl;

import gov.usda.nrcs.wcc.awdbWebService.Data;
import gov.usda.nrcs.wcc.awdbWebService.Duration;
import gov.usda.nrcs.wcc.awdbWebService.HeightDepth;
import gov.usda.nrcs.wcc.awdbWebService.StationMetaData;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.csmarchbanks.snotel.Main.getSnotelService;

/**
 * Created by cmarchbanks on 11/8/15.
 */
public class Stations {
    private static List<StationMetaData> stationMetaDatas;

    public static List<String> getStationTriplets(List<String> stationIds){
        List<String> networkCds = Arrays.asList("SNTL");
        List<Integer> ordinals = Arrays.asList(1);
        List<String> stationTriplets = getSnotelService().getStations(stationIds,
                stateCds, networkCds, hucs, countyNames, minLatitude,
                maxLatitude, minLongitude, maxLongitude, minElevation,
                maxElevation, elementCodes, ordinals, heightDepths, logicalAnd);
    }

    public static List<Data> getStations(String state){
        List<String> stationIds = null;
        List<String> stateCds = null;
        List<String> hucs = null;
        List<String> countyNames = null;
        BigDecimal minLatitude = null;
        BigDecimal maxLatitude = null;
        BigDecimal minLongitude = null;
        BigDecimal maxLongitude = null;
        BigDecimal minElevation = null;
        BigDecimal maxElevation = null;
        List<String> elementCodes = Arrays.asList("WTEQ");
        List<HeightDepth> heightDepths = null;

        if(null != state && !state.isEmpty()){
            stateCds = Arrays.asList(state);
        }

        /*
         * If (logicalAnd) is true, the getStations() call will return only
         * stations that match ALL of the parameters passed in, otherwise it’ll
         * return stations that match ANY of the parameters passed in.
         */

        boolean logicalAnd = true;

        long startTime = System.currentTimeMillis();
        List<String> stationTriplets = getStationTriplets();
        long midTime = System.currentTimeMillis();
        List<Data> data = getSnotelService()
                .getData(stationTriplets, "SNWD", 1, null, Duration.DAILY, true, "2015-11-8", "2015-11-8", null);
        long endTime = System.currentTimeMillis();
        System.out.println("station triplets: " + (midTime - startTime));
        System.out.println("station metadata: " + (endTime - midTime));
        return data;
    }
}
