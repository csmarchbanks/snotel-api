package com.csmarchbanks.snotel.stations.impl;

import gov.usda.nrcs.wcc.awdbWebService.HeightDepth;
import gov.usda.nrcs.wcc.awdbWebService.StationMetaData;

import java.math.BigDecimal;
import java.util.*;

import static com.csmarchbanks.snotel.Main.getSnotelService;
import static com.csmarchbanks.snotel.triplets.impl.TripletsService.getStationTriplets;
import static com.csmarchbanks.snotel.triplets.impl.TripletsService.getStationTripletsByLocation;
import static com.csmarchbanks.snotel.triplets.impl.TripletsService.getStationTripletsByStates;

/**
 * Implementation of getting metadata about stations
 * Created by cmarchbanks on 11/8/15.
 */
public class StationsService {
    private static Map<String, StationMetaData> stationMetaDatas = new HashMap<>();

    public static List<StationMetaData> getStationsMetadata(String state){

        List<String> stateCds = null;
        if(null != state && !state.isEmpty()){
            stateCds = Arrays.asList(state);
        }

        return getStationMetadataFromTriplets(getStationTripletsByStates(stateCds));
    }

    public static List<StationMetaData> getAllStationsMetadata(){
        return getStationsMetadata(null);
    }

    public static List<StationMetaData> getStationMetadataFromTriplets(List<String> stationTriplets){
        List<StationMetaData> stations = new ArrayList<>();
        List<String> newTriplets = new ArrayList<>();

        stationTriplets.forEach(station ->{
            StationMetaData metadata = stationMetaDatas.get(station);
            if(null != metadata){
                stations.add(metadata);
            } else {
                newTriplets.add(station);
            }
        });


        if(!newTriplets.isEmpty()) {
            List<StationMetaData> newStations = getSnotelService().getStationMetadataMultiple(newTriplets);
            stations.addAll(newStations);
            newStations.forEach(station -> stationMetaDatas.put(station.getStationTriplet(), station));
        }

        return stations;
    }

    public static List<StationMetaData> getStationsMetadata(
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
            boolean logicalAnd
    ){
        List<String> stationTriplets = getStationTriplets(stationIds,
                stateCds, hucs, countyNames, minLatitude,
                maxLatitude, minLongitude, maxLongitude, minElevation,
                maxElevation, null, logicalAnd);

        return getStationMetadataFromTriplets(stationTriplets);
    }

    public static List<StationMetaData> getStationsNear(BigDecimal latitude, BigDecimal longitude, BigDecimal deltaLat, BigDecimal deltaLong){
        // default to within half a degree
        deltaLat = null == deltaLat ? new BigDecimal(0.5) : deltaLat;
        deltaLong = null == deltaLong ? new BigDecimal(0.5) : deltaLong;

        BigDecimal minLatitude = latitude.subtract(deltaLat);
        BigDecimal maxLatitude = latitude.add(deltaLat);
        BigDecimal minLongitude = longitude.subtract(deltaLong);
        BigDecimal maxLongitude = longitude.add(deltaLong);

        return getStationMetadataFromTriplets(getStationTripletsByLocation(minLatitude, maxLatitude,
                minLongitude, maxLongitude));
    }
}
