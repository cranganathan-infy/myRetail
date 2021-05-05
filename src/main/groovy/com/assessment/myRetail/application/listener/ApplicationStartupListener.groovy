package com.assessment.myRetail.application.listener

import com.assessment.myRetail.application.logging.KeyValueLogger
import com.assessment.myRetail.product.entity.CurrentPrice
import com.assessment.myRetail.product.repository.ProductRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Slf4j
@Component
class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ProductRepository productRepository

    @Override
    void onApplicationEvent(ApplicationReadyEvent event) {
        log.info(KeyValueLogger.log('Application ready event received by myRetail', [hello: 'world', environment: System.getProperty('environment')]))
        productRepository.saveAll([
                new CurrentPrice(id: 13860428, value: 13.49, currencyCode: 'USD'), new CurrentPrice(id: 54456119, value: 4.39, currencyCode: 'USD'),
                new CurrentPrice(id: 13264003, value: 5.99, currencyCode: 'USD'), new CurrentPrice(id: 12954218, value: 0.99, currencyCode: 'USD')
        ])
    }
}
