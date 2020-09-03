package com.guild.cabs.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Criteria for searching cabs. Latitude and longitude are required. All fields are optional")
public class CabSearchInputRep extends LatLongRep {

    private int _limit = 8;
    private double _radius = 1000.0;

    public CabSearchInputRep() {
        super();
    }

    public CabSearchInputRep(
        final double latitude,
        final double longitude,
        final int limit,
        final double radius
    ) {
        super(
            latitude,
            longitude
        );
        _limit = limit;
        _radius = radius;
    }

    @ApiModelProperty(notes = "The total number of cabs to limit the response to")
    public int getLimit() {
        return _limit;
    }

    public void setLimit(final int limit) {
        _limit = limit;
    }

    @ApiModelProperty(notes = "The maximum distance (in meters) from the client location for a cab")
    public double getRadius() {
        return _radius;
    }

    public void setRadius(final double radius) {
        _radius = radius;
    }
}
