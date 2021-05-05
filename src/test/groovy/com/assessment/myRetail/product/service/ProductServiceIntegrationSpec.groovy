package com.assessment.myRetail.product.service

import com.assessment.myRetail.MyRetailApplicationIntegrationSpec
import com.assessment.myRetail.product.domain.Product
import com.assessment.myRetail.product.entity.CurrentPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class ProductServiceIntegrationSpec extends MyRetailApplicationIntegrationSpec {

    @Autowired
    ProductService sut
    CurrentPrice currentPrice111
    CurrentPrice currentPrice222

    def setup() {
        currentPrice111 = new CurrentPrice(id: 111, value: 1.99, currencyCode: 'USD')
        currentPrice222 = new CurrentPrice(id: 222, value: 2.29, currencyCode: 'USD')
        sut.productRepository.saveAll([currentPrice111, currentPrice222])
    }

    def 'findCurrentPrice happypath'() {

        when:
        CurrentPrice currentPrice = sut.findCurrentPrice(111)

        then:
        currentPrice
        currentPrice.id == 111
        currentPrice.value == 1.99
        currentPrice.currencyCode == 'USD'
    }

    def 'findCurrentPrice no productid'() {
        when:
        CurrentPrice currentPrice = sut.findCurrentPrice(123)

        then:
        !currentPrice
    }

    def 'fetchProductInfo with valid product'() {

        when:
        Product resultProduct = sut.fetchProductInfo(222)

        then:
        resultProduct
        resultProduct.currentPrice.value == 2.29
    }

    def 'fetchProductInfo with invalid product and exceptionHandler'() {

        when:
        sut.fetchProductInfo(000)

        then:
        HttpClientErrorException clientErrorException = thrown()
        clientErrorException.message == """404 : [{" product ":{" item ":{}}}]"""
        clientErrorException.statusCode == HttpStatus.NOT_FOUND
    }

    def 'updateProductPrice with valid product'() {
        setup:
        CurrentPrice newCurrentPrice = new CurrentPrice(id: 222, currencyCode: 'USD', value: 5.99)
        Product productRequest = new Product(222, 'somename', newCurrentPrice)

        when:
        Product updatedProduct = sut.updateProductPrice(productRequest)

        then:
        updatedProduct.currentPrice.id == 222
        updatedProduct.currentPrice.value == 5.99
    }

    def 'updateProductPrice with invalid product'() {
        setup:
        CurrentPrice newCurrentPrice = new CurrentPrice(id: 123, currencyCode: 'USD', value: 5.99)
        Product productRequest = new Product(123, 'invalidproduct', newCurrentPrice)
        when:
        sut.updateProductPrice(productRequest)

        then:
        thrown(EmptyResultDataAccessException)
    }

}
