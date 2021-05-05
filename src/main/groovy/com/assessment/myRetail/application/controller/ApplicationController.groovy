package com.assessment.myRetail.application.controller

import com.assessment.myRetail.application.service.ApplicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('application')
class ApplicationController {

    @Autowired
    ApplicationService applicationService

    @GetMapping("health-check")
    Map applicationHealthCheck() {
        applicationService.healthCheckInfo()
    }
}
