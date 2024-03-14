package com.example.examenrec2ev_final

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    private lateinit var editorialLibro: EditText
    private lateinit var anioLanzamiento: EditText
    private lateinit var botonContinuar: Button
    private lateinit var botonAtras: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        editorialLibro = findViewById(R.id.editTextText3)
        anioLanzamiento = findViewById(R.id.editTextText4)
        botonContinuar = findViewById(R.id.button2)
        botonAtras = findViewById(R.id.button3)

        val libro1 = intent.getSerializableExtra("libro") as Libro

        botonContinuar.setOnClickListener {
            val editorial = editorialLibro.text.toString()
            val anio = anioLanzamiento.text.toString()

            if (editorial.isBlank() || anio.isBlank()) {
                showToast("Faltan datos")
            } else {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                val libro2 = Libro(libro1.getNombreLibro(), libro1.getPaginasLibro(), editorial, anio.toInt())
                intent.putExtra("libro2", libro2)
                startActivity(intent)
            }
        }

        botonAtras.setOnClickListener {
            val intent = Intent(this@SecondActivity, MainActivity::class.java)
            intent.putExtra("libro", libro1)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}