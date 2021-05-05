package com.assessment.myRetail.product.service

import com.assessment.myRetail.product.domain.Product
import com.assessment.myRetail.product.entity.CurrentPrice
import com.assessment.myRetail.product.repository.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class ProductServiceSpec extends Specification {
    ProductService sut = new ProductService(
            restTemplate: Mock(RestTemplate),
            productRepository: Mock(ProductRepository),
            objectMapper: Mock(ObjectMapper)
    )
    Map productData

    def setup() {
        ObjectMapper mapper = new ObjectMapper()
        String productJson = """{
                           "product":{
                              "item":{
                                 "tcin":1111,
                                 "product_description":{
                                    "title":"test product title"
                                 }
                              }
                           }
                        }"""

        productData = mapper.readValue(productJson, Map)

    }

    def 'generateProductResponse happy-path'() {
        setup:
        CurrentPrice currentPriceEntity = new CurrentPrice(id: 111, value: 0.66, currencyCode: 'USD')

        when:
        Product productResponse = sut.generateProductResponse(productData)

        then:
        1 * sut.productRepository.findById(productData.product.item.tcin) >> Optional.of(currentPriceEntity)
        0 * _
        productResponse.currentPrice.value == 0.66
        productResponse.name == 'test product title'
    }

    def 'generateProductResponse when no productId'() {

        when:
        Product productResponse = sut.generateProductResponse([:])

        then:
        0 * _
        !productResponse.name
        !productResponse.currentPrice
    }
}
