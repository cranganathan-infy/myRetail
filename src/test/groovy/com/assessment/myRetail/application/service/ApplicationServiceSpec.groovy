package com.assessment.myRetail.application.service

import com.mongodb.internal.build.MongoDriverVersion
import org.springframework.boot.info.BuildProperties
import spock.lang.Specification

class ApplicationServiceSpec extends Specification {

    ApplicationService sut = new ApplicationService(buildProperties: Mock(BuildProperties))

    def "test HealthCheckInfo"() {
        when:
        Map result = sut.healthCheckInfo()

        then:
        1 * sut.buildProperties.name >> 'somebuildname'
        1 * sut.buildProperties.version >> 'someversion'
        result == [build   : [name: 'somebuildname', version: 'someversion'],
                   database: MongoDriverVersion.VERSION]

    }

    def "test BuildInfo"() {
        when:
        Map result = sut.buildInfo()

        then:
        1 * sut.buildProperties.name >> 'somebuildname'
        1 * sut.buildProperties.version >> 'someversion'
        result == [name: 'somebuildname', version: 'someversion']
    }

    def "test DatabaseVersion"() {
        when:
        String dbVersion = sut.databaseVersion()

        then:
        dbVersion

    }
}
