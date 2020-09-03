package com.guild.cabs.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jetbrains.annotations.Nullable;

@ApiModel(description = "Basic container of a geospatial location")
public class LatLongRep extends AbstractRep {

    private Double _latitude;
    private Double _longitude;

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
    public @Nullable Double getLatitude() {
        return _latitude;
    }

    public void setLatitude(final @Nullable Double latitude) {
        _latitude = latitude;
    }

    @ApiModelProperty(notes = "Geolocation component: longitude")
    public @Nullable Double getLongitude() {
        return _longitude;
    }

    public void setLongitude(final @Nullable Double longitude) {
        _longitude = longitude;
    }
}
