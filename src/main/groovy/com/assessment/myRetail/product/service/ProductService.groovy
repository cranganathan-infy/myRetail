package com.assessment.myRetail.product.service

import com.assessment.myRetail.application.logging.KeyValueLogger
import com.assessment.myRetail.product.domain.Product
import com.assessment.myRetail.product.entity.CurrentPrice
import com.assessment.myRetail.product.repository.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Slf4j
@Service
class ProductService {

    @Autowired
    RestTemplate restTemplate

    @Value('${product.pdp.url}')
    String productPdpUrl

    @Value('${product.query.params}')
    String productQueryParams

    @Autowired
    ProductRepository productRepository

    @Autowired
    ObjectMapper objectMapper

    Product fetchProductInfo(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders()
        HttpEntity requestHttpEntity = new HttpEntity(httpHeaders)
        Map productResponseMap = [:]
        Product productResponse = new Product()
        ResponseEntity<String> restResponse
        String pdpUrl = "${productPdpUrl}${id}${productQueryParams}"

        restResponse = restTemplate.exchange(pdpUrl, HttpMethod.GET, requestHttpEntity, String)
        if (restResponse.statusCode.'2xxSuccessful') {
            log.info(KeyValueLogger.log('Response received from product Url', [responseBody: restResponse.body]))
            productResponseMap = objectMapper.readValue(restResponse.body, Map)
            productResponse = generateProductResponse(productResponseMap)
        } else {
            log.error(KeyValueLogger.log('No response form the Url', [url: pdpUrl, response: restResponse.body, httpStatus: restResponse.statusCode]))
        }
        return productResponse
    }

    Product generateProductResponse(Map productData) {
        Map itemData = productData?.product?.item
        Product productResponse = new Product()
        if (itemData?.tcin) {
            productResponse.id = Long.valueOf(itemData.tcin)
            productResponse.name = itemData?.product_description?.title
            productResponse.currentPrice = findCurrentPrice(productResponse.id)
            log.info(KeyValueLogger.log('Product response formed', [productResponse: productResponse]))
        }
        return productResponse
    }

    CurrentPrice findCurrentPrice(Long id) {
        CurrentPrice currentPrice = productRepository.findById(id).orElse(null)
        log.info(KeyValueLogger.log("Current price for the product: ${id} in DB is ${currentPrice?.value}"))
        return currentPrice
    }

    Product updateProductPrice(Product newProduct) {
        CurrentPrice currentPrice = findCurrentPrice(newProduct.id)
        if (currentPrice) {
            currentPrice.value = newProduct.currentPrice.value ?: currentPrice.value
            productRepository.save(currentPrice)
            log.info(KeyValueLogger.log('Price updated successfully', [productId: newProduct.id, newPriceValue: currentPrice.value]))
        } else {
            throw new EmptyResultDataAccessException("Product ${newProduct.id} unavailable to update current price", 1)
        }
        return newProduct
    }

}
