package com.projectIndustriel.server.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static long[] convertToUnixTimestamps(String dateString) {
        // Define formatter for ddMMyyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        // Parse the input string into a LocalDate
        LocalDate localDate = LocalDate.parse(dateString, formatter);

        // Define Paris Timezone
        ZoneId parisZone = ZoneId.of("Europe/Paris");

        // Start of the day (00:00:00)
        ZonedDateTime startOfDay = localDate.atStartOfDay(parisZone);

        // End of the day (23:59:59)
        ZonedDateTime endOfDay = localDate.atTime(LocalTime.MAX).atZone(parisZone);

        // Convert to Unix timestamps (in seconds)
        long startUnix = startOfDay.toEpochSecond();
        long endUnix = endOfDay.toEpochSecond();

        return new long[]{startUnix, endUnix};
    }
}
