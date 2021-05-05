package com.assessment.myRetail.application.config

import spock.lang.Specification

class ApplicationConfigSpec extends Specification {

    ApplicationConfig sut = new ApplicationConfig()

    def "RestTemplate"() {
        expect:
        sut.restTemplate()

    }

    def "ObjectMapper"() {
        expect:
        sut.objectMapper()
    }
}
