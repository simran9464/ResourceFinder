package com.example.disasterproject.database




import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.disasterproject.object_item.StepsModel

class StepsDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "stepsDB"
        private const val DATABASE_VERSION = 1

        private const val TABLE_STEPS = "steps"
        private const val COLUMN_ID = "id"
        private const val COLUMN_STEPS = "steps"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createStepsTable = ("CREATE TABLE $TABLE_STEPS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_STEPS TEXT)")
        db.execSQL(createStepsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STEPS")
        onCreate(db)
    }

    fun addLocation(steps: StepsModel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STEPS, steps.steps)
        }
        db.insert(TABLE_STEPS, null, values)
        db.close()
    }

    fun getAllLocations(): List<StepsModel> {
        val stepsList = mutableListOf<StepsModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_STEPS", null)

        if (cursor.moveToFirst()) {
            do {
                val steps = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STEPS))
                stepsList.add(StepsModel(steps))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return stepsList
    }
}
