package com.guild.cabs.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel(description = "A single cab in the system")
public class CabRep extends LatLongRep {

    private String _id;

    public CabRep() {
        super();
    }

    public CabRep(
        final String id,
        final double latitude,
        final double longitude
    ) {
        super(
            latitude,
            longitude
        );
        _id = id;
    }

    @ApiModelProperty(notes = "Identifier for this object to be used in subsequent API calls.")
    public String getId() {
        return _id;
    }

    public void setId(final String id) {
        _id = id;
    }

    @JsonIgnore
    public String getOrCreateId() {
        return _id == null ? newId() : _id;
    }

    public static String newId() {
        return UUID.randomUUID()
            .toString();
    }
}
