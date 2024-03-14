package com.example.examenrec2ev_final

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SaveActivity : AppCompatActivity() {

    private lateinit var datosGuardado: TextView
    private lateinit var botonBorrar: Button
    private lateinit var botonAtras: Button
    private lateinit var botonVolver: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val dbHelper = DatabaseHelper(this)

        datosGuardado = findViewById(R.id.textView5)
        botonBorrar = findViewById(R.id.button7)
        botonAtras = findViewById(R.id.button8)
        botonVolver = findViewById(R.id.button9)

        val libro3 = intent.getSerializableExtra("libro2") as Libro

        val listaLibros = dbHelper.lectura()

        var dG = ""
        for (i in listaLibros.indices) {
            dG += "Libro ${i + 1}: ${listaLibros[i]}\n\n"
        }
        datosGuardado.text = dG

        botonBorrar.setOnClickListener {
            dbHelper.borrar()
            datosGuardado.text = "No hay datos"
            showToast("Libros Borrados de la base de datos")
        }

        botonAtras.setOnClickListener {
            val intent = Intent(this@SaveActivity, ThirdActivity::class.java)
            intent.putExtra("libro2", libro3)
            startActivity(intent)
        }

        botonVolver.setOnClickListener {
            val intent = Intent(this@SaveActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

// Clase DatabaseHelper
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "LibrosFavoritos.db"
        private const val TABLA_LIBROS = "tabla"
        private const val KEY_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PAGINAS = "paginas"
        private const val COLUMN_EDITORIAL = "editorial"
        private const val COLUMN_ANIO = "anio"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_LIBROS ($KEY_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT, $COLUMN_PAGINAS INTEGER, $COLUMN_EDITORIAL TEXT," +
                "$COLUMN_ANIO INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_LIBROS")
        onCreate(db)
    }

    fun borrar() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLA_LIBROS")
        onCreate(db)
    }

    fun escribir(libro: Libro): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, libro.getNombreLibro())
            put(COLUMN_PAGINAS, libro.getPaginasLibro())
            put(COLUMN_EDITORIAL, libro.getEditorialLibro())
            put(COLUMN_ANIO, libro.getAnioLanzamiento())
        }
        val id = db.insert(TABLA_LIBROS, null, values)
        db.close()
        return id
    }


    @SuppressLint("Range")
    fun lectura(): ArrayList<Libro> {
        val lectura = ArrayList<Libro>()
        val selectQuery = "SELECT * FROM $TABLA_LIBROS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val paginas = cursor.getInt(cursor.getColumnIndex(COLUMN_PAGINAS))
                val editorial = cursor.getString(cursor.getColumnIndex(COLUMN_EDITORIAL))
                val anio = cursor.getInt(cursor.getColumnIndex(COLUMN_ANIO))

                lectura.add(Libro(nombre, paginas, editorial, anio))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lectura
    }

}