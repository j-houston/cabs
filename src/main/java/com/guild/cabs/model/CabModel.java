package com.guild.cabs.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CabModel {

    @Id
    @Column(name = "id")
    private String _id;

    @Column(nullable = false)
    private Double _latitude;

    @Column(nullable = false)
    private Double _longitude;

    public CabModel() {}

    public CabModel(
        @NotNull final String id,
        final double latitude,
        final double longitude
    ) {
        _id = id;
        _latitude = latitude;
        _longitude = longitude;
    }

    /**
     * @return UUID4. Used as a foreign key in other models
     */
    public String getId() {
        return _id;
    }

    public void setId(final String id) {
        _id = id;
    }

    public Double getLatitude() {
        return _latitude;
    }

    public void setLatitude(Double latitude) {
        _latitude = latitude;
    }

    public Double getLongitude() {
        return _longitude;
    }

    public void setLongitude(Double longitude) {
        _longitude = longitude;
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return _id.equals(((CabModel)obj)._id);
    }
}
