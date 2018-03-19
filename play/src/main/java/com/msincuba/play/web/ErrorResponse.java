package com.msincuba.play.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.msincuba.play.utils.VariableUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NONE, property = "error", visible = true)
@Slf4j
public class ErrorResponse {

    private String errorId;

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String timestamp;
    private String path;
    private String message;
    private String rootCauseMessage;
    private String throwableClassName;
    private String debugMessage;
    private String requestBody = "";
    private String metadata;
    private String user;
    @JsonIgnore
    private List<ValidationError> validationErrors;
    private List<String> validations;

    private ErrorResponse() {
        timestamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ErrorResponse(Throwable ex) {
        this();
        populateMessages(ex);
    }

    private void populateMessages(Throwable ex) {
        this.rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
        this.errorId = UUID.randomUUID().toString();
        this.message = StringUtils.substringAfter(ExceptionUtils.getRootCauseMessage(ex), ": ");
        this.throwableClassName = ex.getClass().getName();
        this.debugMessage = ExceptionUtils.getStackTrace(ex);
        log.error(rootCauseMessage + ". errorId >" + errorId + "<", ex);
        if (ExceptionUtils.getRootCause(ex) instanceof ConstraintViolationException) {
            ConstraintViolationException cv = (ConstraintViolationException) ExceptionUtils.getRootCause(ex);
            Set<ConstraintViolation<?>> constraintViolations = cv.getConstraintViolations();
            StringBuilder sb = new StringBuilder("Validation failed with these errors:\n");
            if (validations == null) {
                validations = new ArrayList<>(constraintViolations.size());
            }
            constraintViolations.stream().map((violation) -> {
                sb
                        .append("[")
                        .append(VariableUtils.variableToWord(violation.getPropertyPath().toString()))
                        .append("] ")
                        .append(violation.getMessage())
                        .append(" for object [")
                        .append(violation.getRootBeanClass().getSimpleName())
                        .append("]. Supplied invalid vaue is: \"")
                        .append(violation.getInvalidValue())
                        .append("\".");
                return violation;
            }).forEachOrdered((violation) -> {
                validations.add(sb.toString() + " with invalid value: " + violation.getInvalidValue());
            });
            
            this.message = sb.toString().trim();
        }
    }

    public ErrorResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        populateMessages(ex);
    }

    public ErrorResponse(HttpStatus status, Throwable ex, String message) {
        this(status, ex);
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, Throwable ex, WebRequest request) {
        this(status, ex);
        logRequestInformation(request);
    }

    private void logRequestInformation(WebRequest webRequest) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
        HttpServletRequest request = servletWebRequest.getRequest();
        Object body = request.getAttribute("requestBody");
        if (body == null) {
            if (StringUtils.isNotBlank(request.getRemoteUser())) {
                try {
                    String encoding = StringUtils.isBlank(request.getCharacterEncoding()) ? Charset.defaultCharset().name() : request.getCharacterEncoding();
                    ServletInputStream inputStream = request.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
                    this.requestBody = bufferedReader.lines().collect(Collectors.joining());

                } catch (IOException ex) {
                    log.error("Failed to read json", ex);
                } 
            }
        } else {
            this.requestBody = (String) body;
        }
        RequestMetadata requestMetadata = new RequestMetadata(request);
        this.path = StringUtils.isNotBlank(requestMetadata.getRequestURI())
                ? requestMetadata.getRequestURI() : requestMetadata.getServletPath();
        this.metadata = requestMetadata.metadataAsString();
        this.user = request.getRemoteUser();
    }

    public ErrorResponse(HttpStatus status, Throwable ex, String message, WebRequest request) {
        this(status, ex);
        this.message = message;
        logRequestInformation(request);
    }

    private void addSubError(ValidationError subError) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a
     *
     * @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    @Data
    @AllArgsConstructor
    class ValidationError {

        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        ValidationError(String object, String message) {
            this.object = object;
            this.message = message;
        }
    }

}
