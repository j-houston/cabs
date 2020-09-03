package com.guild.cabs.utils;

import org.jetbrains.annotations.NotNull;

public class SpatialUtils {

    public static final double EARTH_RADIUS_M = 6378137;
    public static final int SRID = 4326;

    public static @NotNull double[] getLocationFromSourceAndDistance(
        final double sourceLatitude,
        final double sourceLongitude,
        final double distanceMeters
    ) {
        final double azimuth = Math.random() * 360.0;
        final double deltaMetersN = distanceMeters * Math.sin(azimuth);
        final double deltaMetersE = distanceMeters * Math.cos(azimuth);

        final double offsetLatRadians = deltaMetersN / EARTH_RADIUS_M;
        final double offsetLongRadians = deltaMetersE / (EARTH_RADIUS_M * Math.cos(Math.toRadians(sourceLatitude)));

        final double newLatitude = sourceLatitude + (Math.toDegrees(offsetLatRadians));
        final double newLongitude = sourceLongitude + (Math.toDegrees(offsetLongRadians));

        return new double[] {
            newLatitude, newLongitude
        };
    }
}
