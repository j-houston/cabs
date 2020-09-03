package com.guild.cabs.view;

import com.guild.cabs.exceptions.BaseCabsException;
import com.guild.cabs.exceptions.CabsErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

@ApiModel(description = "All errors on the API should include a response body in this format.")
public class ErrorOutputRep extends AbstractRep {

    private final CabsErrorCode _errorCode;
    private final String _message;
    private final Map<String, Object> _metadata;
    private final HttpStatus _statusCode;

    public ErrorOutputRep(
        @NotNull final CabsErrorCode errorCode,
        @NotNull final String message,
        @NotNull final HttpStatus statusCode
    ) {
        _errorCode = errorCode;
        _message = message;
        _metadata = new HashMap<>();
        _statusCode = statusCode;
    }

    public ErrorOutputRep(@NotNull final BaseCabsException ex) {
        this(
            ex.getErrorCode(),
            ex.getMessage(),
            ex.getStatusCode()
        );

        addExceptionMetadata(ex);
    }

    private void addExceptionMetadata(@NotNull final BaseCabsException ex) {
        final PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(ex);
        for (PropertyDescriptor property : properties) {
            final Class<?> propertyClass = property.getReadMethod()
                .getDeclaringClass();
            if (
                BaseCabsException.class.equals(propertyClass)
                    || !BaseCabsException.class.isAssignableFrom(propertyClass)
            ) {
                continue;
            }

            final String propertyName = property.getName();
            try {
                final Object propertyValue = property.getReadMethod()
                    .invoke(ex);
                _metadata.put(
                    propertyName,
                    propertyValue
                );
            } catch(Exception e) {
                _metadata.put(
                    propertyName,
                    e.getMessage()
                );
            }
        }
    }

    @ApiModelProperty(notes = "High level exception code that indicates the class of error this is.")
    public CabsErrorCode getErrorCode() {
        return _errorCode;
    }

    @ApiModelProperty(notes = "Human readable error message, though NOT TRANSLATED!")
    public String getMessage() {
        return _message;
    }

    @ApiModelProperty(notes = "Optional properties relevant to the ERROR and REASON code combination")
    public Map<String, Object> getMetadata() {
        return _metadata;
    }

    @ApiModelProperty(
        notes = "Returned in the REST API as the HTTP status code, copied here for clarity and debugging."
    )
    public HttpStatus getStatusCode() {
        return _statusCode;
    }
}
