package com.assessment.myRetail.stub.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/stubs/v3/pdp/tcin/')
class ProductStubController {

    @GetMapping("{tcin}")
    ResponseEntity fetchPdpResponse(@PathVariable Long tcin) {
        if (tcin instanceof Long && tcin > 1) {
            new ResponseEntity(getDummyResponse(tcin), HttpStatus.OK)
        } else {
            new ResponseEntity("""{" product ":{" item ":{}}}""", HttpStatus.NOT_FOUND)
        }
    }

    String getDummyResponse(Long tcin) {
        String responseJson = """{
                           "product":{
                              "item":{
                                 "tcin":${tcin},
                                 "product_description":{
                                    "title":"product title for ${tcin}"
                                 }
                              }
                           }
                        }"""
        return responseJson
    }

}
