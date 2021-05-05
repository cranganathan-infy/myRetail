package com.assessment.myRetail.application.controller

import com.assessment.myRetail.application.service.ApplicationService
import spock.lang.Specification

class ApplicationControllerSpec extends Specification {

    ApplicationController sut = new ApplicationController(
            applicationService: Mock(ApplicationService)
    )

    def "test ApplicationHealthCheck"() {
        when:
        sut.applicationHealthCheck()

        then:
        1 * sut.applicationService.healthCheckInfo() >> [name: 'myRetail', version: 'test']
        0 * _
    }
}
