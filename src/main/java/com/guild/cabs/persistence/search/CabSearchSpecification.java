package com.guild.cabs.persistence.search;

import com.guild.cabs.model.CabModel;
import com.guild.cabs.view.CabSearchInputRep;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class CabSearchSpecification {

    public static Specification<CabModel> fromSearchCriteria(final CabSearchInputRep searchInput) {
        return new CabSearchSpecification().createSpecification(searchInput);
    }

    public static Sort getDefaultSort() {
        return Sort.by(
            Sort.Direction.ASC,
            "_id"
        );
    }

    private Specification<CabModel> createSpecification(final CabSearchInputRep searchInput) {
        if (searchInput == null) {
            return Specification.where(null);
        }

        //@formatter:off
        return Specification
            .where(withinM(
                searchInput.getLatitude(),
                searchInput.getLongitude(),
                searchInput.getRadius()
            ));
        //@formatter:on
    }

    private static Specification<CabModel> withinM(
        final Double latitude,
        final Double longitude,
        final Double radiusM
    ) {
        if ((latitude == null) || (longitude == null) || (radiusM == null)) {
            return null;
        }

        return (
            cabModel,
            query,
            builder
        ) -> builder.and(
            new WithinPredicate(
                (CriteriaBuilderImpl)builder,
                cabModel.get("_latitude"),
                cabModel.get("_longitude"),
                latitude,
                longitude,
                radiusM
            )
        );
    }
}
