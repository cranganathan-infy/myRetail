package com.assessment.myRetail

import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [MyRetailApplication], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = MyRetailApplication, initializers = ConfigFileApplicationContextInitializer)
abstract class MyRetailApplicationIntegrationSpec extends Specification {
    static {
        System.setProperty('environment', 'integration')
        System.setProperty('spring.main.allow-bean-definition-overriding', 'true')
    }
}
