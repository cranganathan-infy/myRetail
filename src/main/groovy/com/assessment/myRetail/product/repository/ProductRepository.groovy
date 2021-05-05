package com.assessment.myRetail.product.repository

import com.assessment.myRetail.product.entity.CurrentPrice
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository extends MongoRepository<CurrentPrice, Long> {
}
