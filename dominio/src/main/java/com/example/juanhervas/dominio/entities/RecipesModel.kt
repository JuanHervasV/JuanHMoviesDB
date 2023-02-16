package com.example.juanhervas.dominio.entities

data class RecipesModel(
    var id: String,
    var name: String,
    var origin: String,
    var latitude: String,
    var longitude: String,
    var ingredients: String,
    var preparation: String,
    var images: String
)