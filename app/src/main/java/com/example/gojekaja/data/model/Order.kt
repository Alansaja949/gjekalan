package com.example.gojekaja.data.model

data class Order(
    val id: String,
    val serviceType: String,
    val origin: String,
    val destination: String,
    val price: Double,
    val status: String
)