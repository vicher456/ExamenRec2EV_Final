package com.example.examenrec2ev_final

import java.io.Serializable

data class Libro(

    private var nombreLibro: String,
    private var paginasLibro: Int,
    private var editorialLibro: String,
    private var anioLanzamiento: Int

): Serializable {

    fun getNombreLibro(): String {
        return nombreLibro
    }

    fun getPaginasLibro(): Int {
        return paginasLibro
    }

    fun getEditorialLibro(): String {
        return editorialLibro
    }

    fun getAnioLanzamiento(): Int {
        return anioLanzamiento
    }

    override fun toString(): String {
        return "Libro [Nombre: $nombreLibro, Paginas: $paginasLibro, Editorial: $editorialLibro, Anio: $anioLanzamiento]"
    }

}