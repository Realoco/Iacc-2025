package com.iacc.robert_morales_semana_6.database
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.iacc.robert_morales_semana_6.entidades.Infraccion

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "Infracciones_RMR_Semana_6_DB"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "infracciones"

        const val COLUMN_ID = "id"
        const val COLUMN_RUT = "rut_inspector"
        const val COLUMN_LOCAL = "nombre_local"
        const val COLUMN_DIRECCION = "direccion"
        const val COLUMN_INFRACCION = "infraccion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RUT TEXT NOT NULL,
                $COLUMN_LOCAL TEXT NOT NULL,
                $COLUMN_DIRECCION TEXT NOT NULL,
                $COLUMN_INFRACCION TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarInfraccion(infraccion: Infraccion): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RUT, infraccion.rutInspector)
            put(COLUMN_LOCAL, infraccion.nombreLocal)
            put(COLUMN_DIRECCION, infraccion.direccion)
            put(COLUMN_INFRACCION, infraccion.infraccion)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun obtenerInfracciones(): List<Infraccion> {
        val lista = mutableListOf<Infraccion>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        while (cursor.moveToNext()) {
            val infraccion = Infraccion(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCAL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFRACCION))
            )
            lista.add(infraccion)
        }
        cursor.close()
        db.close()
        return lista
    }

    fun actualizarInfraccion(id: Int, rutInspector: String, nombreLocal: String, direccion: String, infraccion: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RUT, rutInspector)
            put(COLUMN_LOCAL, nombreLocal)
            put(COLUMN_DIRECCION, direccion)
            put(COLUMN_INFRACCION, infraccion)
        }
        val filasActualizadas = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return filasActualizadas
    }


}
