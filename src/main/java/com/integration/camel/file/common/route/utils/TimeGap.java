package com.integration.camel.file.common.route.utils;


import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TimeGap {
    public String calculateTimeDifference(Date startTime, Date endTime) {
        long diffInMillis = endTime.getTime() - startTime.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
        return String.format("%d hours, %d minutes, %d seconds, %d milliseconds", hours, minutes, seconds, diffInMillis);
    }
}