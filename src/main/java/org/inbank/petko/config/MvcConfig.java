package org.inbank.petko.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

/**
 * Serves to override Spring predefined configurations
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Adds our custom {@link StringToIntegerConverter} converter to App's converters list
     * Adds our custom {@link StringToDoubleConverter} converter to App's converters list
     * @param registry Registry of different data Converters
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new StringToDoubleConverter());
    }

    /**
     * Overrides predefined Spring's {@link DefaultHandlerExceptionResolver} with custom {@link RestResponseStatusExceptionResolver} exception Resolver
     * @param resolvers List of predefined exception Resolvers
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.removeIf(resolver -> resolver instanceof DefaultHandlerExceptionResolver);
        resolvers.add(new RestResponseStatusExceptionResolver());
    }

}
