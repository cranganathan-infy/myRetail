package com.assessment.myRetail.product.controller

import com.assessment.myRetail.product.domain.Product
import com.assessment.myRetail.product.service.ProductService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.PositiveOrZero

@Slf4j
@RestController
@RequestMapping('products')
class ProductController {

    @Autowired
    ProductService productService

    @GetMapping("{id}")
    ResponseEntity fetchProductById(@PathVariable @PositiveOrZero Long id) {
        new ResponseEntity<Product>(productService.fetchProductInfo(id), HttpStatus.OK)
    }

    @PutMapping('{id}')
    ResponseEntity updateProductById(@PathVariable @PositiveOrZero Long id, @RequestBody Product product) {
        if (product.id != id) {
            return new ResponseEntity('The product id does not match, Check your request', HttpStatus.BAD_REQUEST)
        }
        Product productUpdated = productService.updateProductPrice(product)
        return new ResponseEntity<Product>(productUpdated, HttpStatus.ACCEPTED)
    }
}
