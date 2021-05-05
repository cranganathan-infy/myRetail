package com.assessment.myRetail.product.domain

import com.assessment.myRetail.product.entity.CurrentPrice
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class Product {

    Long id

    String name

    @JsonProperty('current_price')
    CurrentPrice currentPrice
}
