package com.guild.cabs.exceptions.advice;

import com.guild.cabs.exceptions.BaseCabsException;
import com.guild.cabs.view.ErrorOutputRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {
    "com.guild.cabs.rest"
})
public class CabsRestControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(CabsRestControllerAdvice.class);

    @ExceptionHandler({
        Throwable.class
    })
    public ResponseEntity<ErrorOutputRep> handleCabsException(
        final HttpServletRequest request,
        final Throwable ex
    ) throws Throwable {
        if (ex instanceof BaseCabsException) {
            return handleCabsException((BaseCabsException)ex);
        }
        LOG.error(
            ex.getMessage(),
            ex
        );
        throw ex;
    }

    private ResponseEntity<ErrorOutputRep> handleCabsException(BaseCabsException ex) {
        // We test a lot of exceptions. Suppress logging those, but otherwise always log
        return new ResponseEntity<ErrorOutputRep>(
            new ErrorOutputRep(ex),
            ex.getStatusCode()
        );
    }
}
