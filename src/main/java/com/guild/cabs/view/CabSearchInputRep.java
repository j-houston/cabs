package com.guild.cabs.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Criteria for searching cabs. Latitude and longitude are required. All fields are optional")
public class CabSearchInputRep extends LatLongRep {

    private Integer _limit = 8;
    private Double _radius = 1000.0;

    public CabSearchInputRep() {
        super();
    }

    public CabSearchInputRep(
        final Double latitude,
        final Double longitude,
        final Integer limit,
        final Double radius
    ) {
        super(
            latitude,
            longitude
        );
        _limit = limit;
        _radius = radius;
    }

    @ApiModelProperty(notes = "The total number of cabs to limit the response to")
    public Integer getLimit() {
        return _limit;
    }

    public void setLimit(final Integer limit) {
        _limit = limit;
    }

    @ApiModelProperty(notes = "The maximum distance (in meters) from the client location for a cab")
    public Double getRadius() {
        return _radius;
    }

    public void setRadius(final Double radius) {
        _radius = radius;
    }
}
