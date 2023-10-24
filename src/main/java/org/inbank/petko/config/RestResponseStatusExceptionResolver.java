package org.inbank.petko.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.IOException;

/**
 * Overrides Spring's {@link DefaultHandlerExceptionResolver} in order to replace WhiteLabel page functionality
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Component
@Slf4j
public class RestResponseStatusExceptionResolver extends DefaultHandlerExceptionResolver {

    /**
     * Change error page logic. If there is Context error - show error.html. If other - index.html.
     * Set servlet exception to request as attribute, in order to be able to set appropriate error message for user
     * @param request  current {@link HttpServletRequest}
     * @param response current {@link HttpServletResponse}
     * @param handler  particular project method, where exception occurred
     * @param ex       current child of {@link Exception}
     * @return  new {@link ModelAndView} with custom index || error page
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        try {
            request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, ex);
        } catch (Exception handlerException) {
            log.warn("Handling of [{}] resulted in Exception", ex.getClass().getName(), handlerException);
        }
        if (ex instanceof BeanInstantiationException) {
            return new ModelAndView("error");
        } else {
            if (ex instanceof ErrorResponse errorResponse) {
                try {
                    return this.handleErrorResponse(errorResponse, request, response, handler);
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
    }

}
