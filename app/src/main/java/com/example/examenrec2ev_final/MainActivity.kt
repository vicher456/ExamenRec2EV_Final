package com.example.examenrec2ev_final

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var nombreLibro: EditText
    private lateinit var paginasLibro: EditText
    private lateinit var botonContinuar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombreLibro = findViewById(R.id.editTextText)
        paginasLibro = findViewById(R.id.editTextText2)
        botonContinuar = findViewById(R.id.button)

        botonContinuar.setOnClickListener {
            val nombre = nombreLibro.text.toString()
            val paginas = paginasLibro.text.toString()

            if (nombre.isBlank() || paginas.isBlank()) {
                showToast("Faltan datos")
            } else {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                val libro = Libro(nombre, paginas.toInt(), "", 0)
                intent.putExtra("libro", libro)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}