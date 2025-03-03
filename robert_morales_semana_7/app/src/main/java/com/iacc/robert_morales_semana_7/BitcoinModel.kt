package com.iacc.robert_morales_semana_7

data class Serie(
    val fecha: String,
    val valor: Double
)

data class BitcoinData(
    val version: String,
    val autor: String,
    val codigo: String,
    val nombre: String,
    val unidad_medida: String,
    val serie: List<Serie>
)