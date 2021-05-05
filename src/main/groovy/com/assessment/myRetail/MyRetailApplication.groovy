package com.assessment.myRetail

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = ['classpath:properties/default.properties', 'classpath:properties/${environment}.properties'])
class MyRetailApplication {

    static void main(String[] args) {
        SpringApplication.run(MyRetailApplication, args)
    }

}
