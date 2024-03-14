package com.example.examenrec2ev_final

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList

class ThirdActivity : AppCompatActivity() {

    private lateinit var nombreLibro: TextView
    private lateinit var paginasLibro: TextView
    private lateinit var editorialLibro: TextView
    private lateinit var anioLanzamiento: TextView
    private lateinit var botonContinuar: Button
    private lateinit var botonAtras: Button
    private lateinit var botonVolver: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val dbHelper = DatabaseHelper(this)

        nombreLibro = findViewById(R.id.textView)
        paginasLibro = findViewById(R.id.textView2)
        editorialLibro = findViewById(R.id.textView3)
        anioLanzamiento = findViewById(R.id.textView4)
        botonContinuar = findViewById(R.id.button4)
        botonAtras = findViewById(R.id.button5)
        botonVolver = findViewById(R.id.button6)

        val libro2 = intent.getSerializableExtra("libro2") as Libro

        val listaLibros = ArrayList<Libro>()

        nombreLibro.text = libro2.getNombreLibro()
        paginasLibro.text = libro2.getPaginasLibro().toString()
        editorialLibro.text = libro2.getEditorialLibro()
        anioLanzamiento.text = libro2.getAnioLanzamiento().toString()

        botonContinuar.setOnClickListener {
            val librosCreados = dbHelper.lectura()
            val libroDG = libro2.toString()

            var yaExiste = false
            for (libroCreado in librosCreados) {
                if (libroCreado.toString() == libroDG) {
                    yaExiste = true
                }
            }

            if (yaExiste) {
                showToast("Libro ya existente en la base de datos")
            } else {
                dbHelper.escribir(libro2)
                listaLibros.add(libro2)
                showToast("Libro Guardado en la base de datos")

                val intent = Intent(this@ThirdActivity, SaveActivity::class.java)
                intent.putExtra("libro2", libro2)
                startActivity(intent)
            }
        }

        botonAtras.setOnClickListener {
            val intent = Intent(this@ThirdActivity, SecondActivity::class.java)
            intent.putExtra("libro2", libro2)
            startActivity(intent)
        }

        botonVolver.setOnClickListener {
            val intent = Intent(this@ThirdActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}