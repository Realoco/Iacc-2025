package com.iacc.robert_morales_semana_6.entidades


data class Infraccion(
    val id: Int = 0,
    val rutInspector: String,
    val nombreLocal: String,
    val direccion: String,
    val infraccion: String
)
