package org.inbank.petko.config;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

/**
 * Performs custom {@link String} to {@link Integer} conversion
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Component
@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {

    /**
     * If not a number is passed, no need to fire an exception. Just return null
     * @param source input from outside
     * @return parsed int or null
     */
    @Override
    public Integer convert(@Nonnull String source) {
        try {
            return source.isEmpty() ? null : NumberUtils.parseNumber(source, Integer.class);
        } catch (Exception e) {
            log.warn("Not number is passed. {}", source);
            return null;
        }
    }
}
