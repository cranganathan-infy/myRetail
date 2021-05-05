package com.assessment.myRetail.product.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import com.assessment.myRetail.MyRetailApplicationIntegrationSpec
import com.assessment.myRetail.product.entity.CurrentPrice
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

class ProductControllerIntegrationSpec extends MyRetailApplicationIntegrationSpec {
    @Autowired
    WebApplicationContext wac

    @Autowired
    ProductController sut

    MockMvc mockMvc

    def 'test ProductController wrong productid'() {
        setup:
        mockMvc = webAppContextSetup(this.wac).build()

        when:
        String testUrl = '/products/0000'
        def result = mockMvc.perform(get(testUrl)).andExpect(status().is4xxClientError()).andReturn()

        then:
        result
    }

    def 'test ProductController when productid is not a number'() {
        setup:
        mockMvc = webAppContextSetup(this.wac).build()

        when:
        String testUrl = '/products/badTCIN'
        def result = mockMvc.perform(get(testUrl)).andExpect(status().is5xxServerError()).andReturn()

        then:
        result
    }

    def 'test ProductController when productid valid'() {
        setup:
        String testUrl = '/products/13860428'
        mockMvc = webAppContextSetup(this.wac).build()

        and:
        sut.productService.restTemplate.exchange(testUrl, HttpMethod.GET, new HttpEntity<Object>(), String) >> """{
                           "product":{
                              "item":{
                                 "tcin":13860428,
                                 "product_description":{
                                    "title":"some product title"
                                 }
                              }
                           }
                        }"""

        when:
        def result = mockMvc.perform(get(testUrl)).andReturn()

        then:
        result
        result.response.status == HttpStatus.OK.value()
    }

    def 'update currentPrice with valid productid'() {
        setup:
        String testUrl = '/products/13860428'
        ObjectMapper objectMapper = new ObjectMapper()
        sut.productService.productRepository.save(new CurrentPrice(13860428, 13.49, 'USD'))
        mockMvc = webAppContextSetup(this.wac).build()
        String requestJson = """{
                      "id":13860428,
                      "name":"The Big Lebowski",
                      "current_price":{"value": 13.09,"currency_code":"USD"}
                      }"""

        when:
        def mvcResult = mockMvc.perform(put(testUrl).content(requestJson).contentType(MediaType.APPLICATION_JSON)).andReturn()

        then:
        Map mvcContent = objectMapper.readValue(mvcResult.response.contentAsString, Map)
        mvcResult.response.status == HttpStatus.ACCEPTED.value()
        mvcContent.current_price.value == 13.09
    }

    def 'update currentPrice with invalid productid'() {
        setup:
        String testUrl = '/products/123123'
        mockMvc = webAppContextSetup(this.wac).build()
        String requestJson = """{
                      "id":123123,
                      "name":"Wrong product",
                      "current_price":{"value": 13.09,"currency_code":"USD"}
                      }"""

        when:
        def mvcResult = mockMvc.perform(put(testUrl).content(requestJson).contentType(MediaType.APPLICATION_JSON)).andReturn()

        then:
        mvcResult.response.status == HttpStatus.NOT_FOUND.value()
    }

}
