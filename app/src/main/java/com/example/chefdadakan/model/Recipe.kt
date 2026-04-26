package com.example.chefdadakan.model

data class Recipe(
    val idMeal: String,
    val strMeal: String,           // food
    val strCategory: String,       // category
    val strMealThumb: String,      // URL image
    val strInstructions: String    // cooking instruction
)