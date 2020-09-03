package com.guild.cabs.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Basic container of a geospatial location")
public class LatLongRep extends AbstractRep {

    private double _latitude;
    private double _longitude;

    public LatLongRep() {
        super();
    }

    public LatLongRep(
        final double latitude,
        final double longitude
    ) {
        _latitude = latitude;
        _longitude = longitude;
    }

    @ApiModelProperty(notes = "Geolocation component: latitude")
    public double getLatitude() {
        return _latitude;
    }

    public void setLatitude(final double latitude) {
        _latitude = latitude;
    }

    @ApiModelProperty(notes = "Geolocation component: longitude")
    public double getLongitude() {
        return _longitude;
    }

    public void setLongitude(final double longitude) {
        _longitude = longitude;
    }
}
