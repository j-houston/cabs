package com.guild.cabs.persistence.search;

import com.guild.cabs.utils.SpatialUtils;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.Expression;
import java.io.Serializable;

public class WithinPredicate extends AbstractSimplePredicate implements Expression<Boolean>, Serializable {

    private final Expression<Double> _latitudeMatchExpression;
    private final Expression<Double> _longitudeMatchExpression;
    private final Expression<Double> _latitudeExpression;
    private final Expression<Double> _longitudeExpression;
    private final Expression<Double> _mRadiusExpression;

    public WithinPredicate(
        @NotNull final CriteriaBuilderImpl criteriaBuilder,
        @NotNull final Expression<Double> latitudeMatchExpression,
        @NotNull final Expression<Double> longitudeMatchExpression,
        final double latitude,
        final double longitude,
        final double radiusM
    ) {
        super(criteriaBuilder);
        _latitudeMatchExpression = latitudeMatchExpression;
        _longitudeMatchExpression = longitudeMatchExpression;
        _latitudeExpression = criteriaBuilder.literal(latitude);
        _longitudeExpression = criteriaBuilder.literal(longitude);
        _mRadiusExpression = criteriaBuilder.literal(radiusM);
    }

    @Override
    public void registerParameters(ParameterRegistry registry) {
        // Nothing to register
    }

    @Override
    public String render(
        boolean isNegated,
        RenderingContext renderingContext
    ) {
        return String.format(
            "ST_DistanceSphere(" + "ST_SetSRID(ST_MakePoint(%s, %s, 0.0), %s), "
                + "ST_SetSRID(ST_MakePoint(%s, %s, 0.0), %s)" + ") <= %s ",
            ((Renderable)_longitudeMatchExpression).render(renderingContext),
            ((Renderable)_latitudeMatchExpression).render(renderingContext),
            SpatialUtils.SRID,
            ((Renderable)_longitudeExpression).render(renderingContext),
            ((Renderable)_latitudeExpression).render(renderingContext),
            SpatialUtils.SRID,
            ((Renderable)_mRadiusExpression).render(renderingContext)
        );
    }
}
