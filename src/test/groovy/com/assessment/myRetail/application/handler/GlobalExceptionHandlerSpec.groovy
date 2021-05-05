package com.assessment.myRetail.application.handler

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import spock.lang.Specification
import spock.lang.Subject
import javax.servlet.http.HttpServletRequest

class GlobalExceptionHandlerSpec extends Specification {

    @Subject
    GlobalExceptionHandler sut
    HttpServletRequest httpServletRequest

    def setup() {
        sut = new GlobalExceptionHandler()
        httpServletRequest = new MockHttpServletRequest(HttpMethod.GET.name(), '/handle/exceptions')
        httpServletRequest.addHeader('headerA', 'valueA')
        httpServletRequest.addHeader('headerB', 'valueB')
        httpServletRequest.setParameter('param1', 'value1')
    }

    def "test HandleAllOtherExceptions"() {
        setup:
        String exceptionMessage = 'some exception message'
        Map headers = [headerA: 'valueA', headerB: 'valueB']
        WebRequest webRequest = new ServletWebRequest(httpServletRequest)
        Exception exception = new Exception(exceptionMessage)

        when:
        ResponseEntity responseEntity = sut.handleAllOtherExceptions(exception, webRequest)

        then:
        responseEntity
        responseEntity.statusCode == INTERNAL_SERVER_ERROR
        responseEntity.body
        responseEntity.body.notice == 'An error has occurred. Please provide this information to the developers.'
        responseEntity.body.clazz == 'Exception'
        responseEntity.body.description == 'uri=/handle/exceptions;client=127.0.0.1'
        responseEntity.body.httpMethod == 'inapplicable'
        responseEntity.body.parameters == "'param1':'[value1]'"
        responseEntity.body.headers == headers
    }

    def 'test Handle404 when no product from rest call'() {
        WebRequest webRequest = new ServletWebRequest(httpServletRequest)
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, 'http client exception')

        when:
        ResponseEntity clientErrorResponseEntity = sut.handle404(httpClientErrorException, webRequest)

        then:
        clientErrorResponseEntity.statusCode == HttpStatus.NOT_FOUND
    }

    def 'test Handle404 when no product in DB'() {
        setup:
        WebRequest webRequest = new ServletWebRequest(httpServletRequest)
        EmptyResultDataAccessException emptyResultDataAccessException = new EmptyResultDataAccessException('No data found', 1)

        when:
        ResponseEntity emptyDataResponseEntity = sut.handle404(emptyResultDataAccessException, webRequest)

        then:
        emptyDataResponseEntity.statusCode == HttpStatus.NOT_FOUND
    }
}
