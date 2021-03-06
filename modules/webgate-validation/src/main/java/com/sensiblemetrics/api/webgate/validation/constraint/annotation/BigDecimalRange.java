package com.sensiblemetrics.api.webgate.validation.constraint.annotation;

import com.sensiblemetrics.api.webgate.validation.constraint.validator.BigDecimalRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = BigDecimalRangeValidator.class)
public @interface BigDecimalRange {
    /**
     * Returns {@code long} minimum precision
     *
     * @return minimum precision
     */
    long minPrecision() default Long.MIN_VALUE;

    /**
     * Returns {@code long} maximum precision
     *
     * @return maximum precision
     */
    long maxPrecision() default Long.MAX_VALUE;

    /**
     * Returns {@code int} scale
     *
     * @return scale
     */
    int scale() default 0;

    /**
     * Returns {@link String} validation message
     *
     * @return {@link String} validation message
     */
    String message() default "{com.sensiblemetrics.api.webgate.validation.constraint.annotation.BigDecimalRange.message}";

    /**
     * Returns {@link Class} groups array
     *
     * @return {@link Class} groups array
     */
    Class<?>[] groups() default {};

    /**
     * Returns {@link Class} array of {@link Payload}s
     *
     * @return {@link Class} array of {@link Payload}s
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link BigDecimalRange} annotations on the same element.
     *
     * @see BigDecimalRange
     */
    @Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER
    })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /**
         * Returns {@link BigDecimalRange} collection
         *
         * @return {@link BigDecimalRange} collection
         */
        BigDecimalRange[] value();
    }
}
