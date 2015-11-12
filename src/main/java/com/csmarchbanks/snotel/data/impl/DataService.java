package com.csmarchbanks.snotel.data.impl;

import gov.usda.nrcs.wcc.awdbWebService.Data;
import gov.usda.nrcs.wcc.awdbWebService.Duration;
import gov.usda.nrcs.wcc.awdbWebService.HeightDepth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.csmarchbanks.snotel.Main.getSnotelService;

/**
 * Implementation of getting data from stations
 * Created by cmarchbanks on 11/11/15.
 */
public class DataService {

    public static List<Data> getData(List<String> stationTriplets){
        String elementCd = "SNWD";
        int ordinal = 1;
        HeightDepth heightDepth = null;
        Duration duration = Duration.DAILY;
        boolean getFlags = false;
        String beginDate;
        String endDate;
        Boolean alwaysReturnDailyFeb29 = false;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();

        endDate = df.format(today);
        beginDate = df.format(yesterday);

        return getSnotelService().getData(stationTriplets, elementCd, ordinal,
                heightDepth, duration, getFlags, beginDate, endDate, alwaysReturnDailyFeb29);
    }

    public static Data getData(String stationTriplet){
        List<Data> data = getData(Arrays.asList(stationTriplet));
        if(null != data && !data.isEmpty()){
            return data.get(0);
        } else {
            return new Data();
        }
    }
}
