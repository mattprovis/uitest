package com.mattprovis.uitest.config;

import com.mattprovis.uitest.CapturedExceptionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class should be added as an interceptor in WebMvcConfig. It gets notified when an Error occurs in a Controller (see DispatcherServlet:944).
 * Unfortunately, it won't get called for normal Exceptions so those had to be handled differently, in the ExceptionReporter class.
 */
@Component
public class ErrorReporter extends HandlerInterceptorAdapter {

    @Autowired
    private CapturedExceptionHolder capturedExceptionHolder;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            if (ex.getCause() != null) {
                capturedExceptionHolder.setCapturedException(ex.getCause());
            } else {
                capturedExceptionHolder.setCapturedException(ex);
            }
        }
    }
}
