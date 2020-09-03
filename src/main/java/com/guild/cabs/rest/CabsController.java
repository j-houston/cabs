package com.guild.cabs.rest;

import com.guild.cabs.exceptions.CabNotFoundException;
import com.guild.cabs.exceptions.CabValidationException;
import com.guild.cabs.exceptions.InvalidCabSearchException;
import com.guild.cabs.handlers.ICabHandler;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.CabSearchInputRep;
import com.guild.cabs.view.CabsPageRep;
import com.guild.cabs.view.LatLongRep;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cabs")
@Api
public class CabsController {

    private final ICabHandler _cabHandler;

    @Autowired
    public CabsController(final ICabHandler cabHandler) {
        _cabHandler = cabHandler;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Search for cabs in the area.", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The requested cabs that meet the expected criteria."),
    })
    public ResponseEntity<CabsPageRep> searchCabs(
        @ApiParam(
            value = "Complete list of all of the search criteria that can be added the request "
                + "as request parameters (no bodies on GETs).",
            required = false
        ) final CabSearchInputRep searchCriteria
    ) throws InvalidCabSearchException {
        final CabsPageRep foundCabs = _cabHandler.searchCabs(searchCriteria);
        return new ResponseEntity<CabsPageRep>(
            foundCabs,
            HttpStatus.OK
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
        value = "Create a new cab with initial latitude and longitude location.", httpMethod = "PUT",
        consumes = "application/json", produces = "application/json"
    )
    @ApiResponses(value = {
        @ApiResponse(
            code = 200, message = "A new cab was created [NOTE: Requirements doc says 200; Perhaps consider a 201?]"
        )
    })
    public ResponseEntity<CabRep> createCab(
        @ApiParam(value = "Initial properties to assign to a new cab.", required = true) @RequestBody final LatLongRep locationRep
    ) throws CabValidationException {
        final CabRep newCab = _cabHandler.createNewCab(locationRep);
        return new ResponseEntity<CabRep>(
            newCab,
            HttpStatus.OK
        );
    }

    @DeleteMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Nuke all cabs from orbit. It's the only way to be sure.", httpMethod = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The requirements doc says 200 with no body. Can I suggest 204?"),
    })
    public ResponseEntity<Void> nukeCabsFromOrbit() {
        _cabHandler.destroyAllCabs();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(path = "/{cabId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Get a cab by its ID.", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The requested cab."),
    })
    public ResponseEntity<CabRep> getCab(
        @ApiParam(value = "ID of the cab to get", required = true) @PathVariable final String cabId
    ) throws CabNotFoundException {
        final CabRep foundCab = _cabHandler.getCab(cabId);
        return new ResponseEntity<CabRep>(
            foundCab,
            HttpStatus.OK
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping(
        path = "/{cabId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ApiOperation(
        value = "Update an existing cab's latitude and longitude location.", httpMethod = "PUT",
        consumes = "application/json", produces = "application/json"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The requested cab location was updated."),
    })
    public ResponseEntity<CabRep> updateCab(
        @ApiParam(value = "ID of the cab to update", required = true) @PathVariable final String cabId,
        @ApiParam(
            value = "Defines the properties to set on the location. Note that the cab ID is reset to "
                + "the ID given in the URL path.",
            required = true
        ) @RequestBody final CabRep cabInput
    ) throws CabNotFoundException,
        CabValidationException {
        cabInput.setId(cabId);
        final CabRep updatedCab = _cabHandler.updateCab(cabInput);

        return new ResponseEntity<CabRep>(
            updatedCab,
            HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/{cabId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Delete a cab by its ID.", httpMethod = "DELETE", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The requirements doc says 200 with no body. Can I suggest 204?"),
    })
    public ResponseEntity<Void> deleteCab(
        @ApiParam(value = "ID of the cab to destroy", required = true) @PathVariable final String cabId
    ) {
        _cabHandler.destroyCab(cabId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
