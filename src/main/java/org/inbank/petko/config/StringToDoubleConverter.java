package org.inbank.petko.config;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

/**
 * Performs custom {@link String} to {@link Double} conversion
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Component
@Slf4j
public class StringToDoubleConverter implements Converter<String, Double> {

    /**
     * If not a number is passed, no need to fire an exception. Just return null
     * @param source input from outside
     * @return parsed double or null
     */
    @Override
    public Double convert(@Nonnull String source) {
        try {
            return source.isEmpty() ? null : NumberUtils.parseNumber(source, Double.class);
        } catch (Exception e) {
            log.warn("Not number is passed. {}", source);
            return null;
        }
    }
}
