package com.assessment.myRetail.application.config

import spock.lang.Specification

class SwaggerConfigSpec extends Specification {
    SwaggerConfig sut = new SwaggerConfig()

    def "test SwaggerConfig"() {
        expect:
        sut.api()
    }
}
