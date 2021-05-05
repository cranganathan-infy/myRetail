package com.assessment.myRetail.product.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class CurrentPrice {

    @Id
    @JsonIgnore
    Long id

    Double value

    @JsonProperty('currency_code')
    String currencyCode
}
