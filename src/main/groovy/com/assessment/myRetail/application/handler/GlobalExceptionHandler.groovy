package com.assessment.myRetail.application.handler

import com.assessment.myRetail.application.logging.KeyValueLogger
import groovy.util.logging.Slf4j
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest

import javax.servlet.http.HttpServletRequest

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler([HttpClientErrorException, EmptyResultDataAccessException])
    ResponseEntity<Object> handle404(Exception ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, webRequest, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception)
    ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, webRequest, HttpStatus.INTERNAL_SERVER_ERROR, true)
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest webRequest, HttpStatus httpStatus, boolean unknownError = false) {
        Map exceptionResponse = unknownError ? generateErrorMap(ex, webRequest) : [httpStatus: httpStatus, message: ex.message]
        log.error(KeyValueLogger.log('Exception occurred', generateErrorMap(ex, webRequest)))
        return new ResponseEntity<Object>(exceptionResponse, httpStatus)
    }

    private static Map generateErrorMap(Exception exception, WebRequest webRequest) {
        return [
                notice     : "An error has occurred. Please provide this information to the developers.",
                clazz      : exception.class.simpleName,
                message    : exception.message,
                description: webRequest.getDescription(true),
                httpMethod : (webRequest instanceof HttpServletRequest) ? (webRequest as HttpServletRequest).method : "inapplicable",
                parameters : webRequest.parameterMap.collect { key, val -> "'$key':'$val'" }.join(', '),
                headers    : webRequest.headerNames?.collectEntries { [(it): webRequest.getHeader(it)] }
        ]
    }
}
