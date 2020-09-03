package com.guild.cabs.handlers.impl;

import com.guild.cabs.exceptions.CabNotFoundException;
import com.guild.cabs.exceptions.CabValidationException;
import com.guild.cabs.exceptions.InvalidCabSearchException;
import com.guild.cabs.handlers.ICabHandler;
import com.guild.cabs.model.CabModel;
import com.guild.cabs.persistence.ICabRepository;
import com.guild.cabs.persistence.search.CabSearchSpecification;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.CabSearchInputRep;
import com.guild.cabs.view.CabsPageRep;
import com.guild.cabs.view.LatLongRep;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CabHandlerImpl implements ICabHandler {

    private final ICabRepository _cabRepository;

    @Autowired
    public CabHandlerImpl(final ICabRepository cabRepository) {
        _cabRepository = cabRepository;
    }

    @Override
    public @NotNull CabRep createNewCab(@NotNull final LatLongRep initialLocation) throws CabValidationException {
        final CabModel cabInput = new CabModel(
            CabRep.newId(),
            initialLocation.getLatitude(),
            initialLocation.getLongitude()
        );
        final CabModel newModel = validateAndSaveCabModel(cabInput);
        return modelToRep(newModel);
    }

    @Override
    public void destroyAllCabs() {
        _cabRepository.deleteAll();
    }

    @Override
    public void destroyCab(@NotNull final String cabId) {
        final Optional<CabModel> optCab = _cabRepository.findById(cabId);
        optCab.ifPresent(_cabRepository::delete);
    }

    @Override
    public @NotNull CabRep getCab(@NotNull String cabId) throws CabNotFoundException {
        final CabModel newModel = getCabModelOrThrow(cabId);
        return modelToRep(newModel);
    }

    public @NotNull CabModel getCabModelOrThrow(@NotNull final String cabId) throws CabNotFoundException {
        final Optional<CabModel> optCab = _cabRepository.findById(cabId);
        if (!optCab.isPresent()) {
            throw new CabNotFoundException(cabId);
        }
        return optCab.get();
    }

    @Override
    public @NotNull CabRep modelToRep(@NotNull final CabModel cabModel) {
        return new CabRep(
            cabModel.getId(),
            cabModel.getLatitude(),
            cabModel.getLongitude()
        );
    }

    public @NotNull CabsPageRep modelsToReps(@NotNull final List<CabModel> cabModels) {
        final List<CabRep> cabReps = new ArrayList<CabRep>(cabModels.size());
        for (CabModel cabModel : cabModels) {
            cabReps.add(modelToRep(cabModel));
        }
        return new CabsPageRep(cabReps);
    }

    @Override
    public @NotNull CabsPageRep searchCabs(@NotNull final CabSearchInputRep searchCriteria)
        throws InvalidCabSearchException {
        validateSearchCriteria(searchCriteria);

        final Specification<CabModel> searchSpecification = CabSearchSpecification.fromSearchCriteria(searchCriteria);
        final Page<CabModel> searchResultsPage = _cabRepository.findAll(
            searchSpecification,
            PageRequest.of(
                0,
                searchCriteria.getLimit(),
                CabSearchSpecification.getDefaultSort()
            )
        );

        return modelsToReps(searchResultsPage.getContent());
    }

    @Override
    public @NotNull CabRep updateCab(@NotNull final CabRep cabInput) throws CabNotFoundException,
        CabValidationException {
        final CabModel existingModel = getCabModelOrThrow(cabInput.getId());
        existingModel.setLatitude(cabInput.getLatitude());
        existingModel.setLongitude(cabInput.getLongitude());
        final CabModel newModel = validateAndSaveCabModel(existingModel);
        return modelToRep(newModel);
    }

    private CabModel validateAndSaveCabModel(@NotNull final CabModel cabInput) throws CabValidationException {
        validateCabModel(cabInput);
        return _cabRepository.save(cabInput);
    }

    public void validateCabModel(@NotNull final CabModel cabInput) throws CabValidationException {
        final Double latitude = cabInput.getLatitude();
        if ((latitude == null) || (latitude > 90) || (latitude < -90)) {
            throw new CabValidationException(
                String.format(
                    "latitude [%s] was not between +90 and -90",
                    latitude
                )
            );
        }

        final Double longitude = cabInput.getLongitude();
        if ((longitude == null) || (longitude > 180) || (longitude < -180)) {
            throw new CabValidationException(
                String.format(
                    "longitude [%s] was not between +180 and -180",
                    latitude
                )
            );
        }
    }

    public void validateSearchCriteria(@NotNull final CabSearchInputRep searchCriteria)
        throws InvalidCabSearchException {
        final double latitude = searchCriteria.getLatitude();
        if ((latitude > 90) || (latitude < -90)) {
            throw new InvalidCabSearchException(searchCriteria);
        }

        final double longitude = searchCriteria.getLongitude();
        if ((longitude > 180) || (longitude < -180)) {
            throw new InvalidCabSearchException(searchCriteria);
        }

        if (searchCriteria.getRadius() < 0) {
            throw new InvalidCabSearchException(searchCriteria);
        }

        if (searchCriteria.getLimit() < 0) {
            throw new InvalidCabSearchException(searchCriteria);
        }
    }
}
