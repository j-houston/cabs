package com.guild.cabs.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("Container of cabs")
public class CabsPageRep extends AbstractRep {

    private List<CabRep> _cabs;

    public CabsPageRep() {
        super();
    }

    public CabsPageRep(final List<CabRep> cabs) {
        _cabs = cabs;
    }

    @ApiModelProperty("Cabs found in a search")
    public List<CabRep> getCabs() {
        return _cabs;
    }

    public void setCabs(List<CabRep> cabs) {
        _cabs = cabs;
    }
}
