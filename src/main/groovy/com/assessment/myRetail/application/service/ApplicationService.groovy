package com.assessment.myRetail.application.service

import com.mongodb.internal.build.MongoDriverVersion
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.stereotype.Service

@Slf4j
@Service
class ApplicationService {

    @Autowired
    BuildProperties buildProperties

    Map healthCheckInfo() {
        return [
                build   : buildInfo(),
                database: databaseVersion()
        ]
    }

    Map buildInfo() {
        return [
                name   : buildProperties.name,
                version: buildProperties.version
        ]
    }

    String databaseVersion() {
        return MongoDriverVersion.VERSION
    }
}
