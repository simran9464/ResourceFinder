package com.example.disasterproject.database








import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.disasterproject.object_item.LocationModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "disasterProjectDB"
        private const val DATABASE_VERSION = 1

        private const val TABLE_LOCATIONS = "locations"
        private const val COLUMN_ID = "id"
        private const val COLUMN_LOCATION = "location"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createLocationsTable = ("CREATE TABLE $TABLE_LOCATIONS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_LOCATION TEXT)")
        db.execSQL(createLocationsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
        onCreate(db)
    }

    fun addLocation(location: LocationModel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_LOCATION, location.location)
        db.insert(TABLE_LOCATIONS, null, values)
        db.close()
    }

    fun getAllLocations(): List<LocationModel> {
        val locationList = mutableListOf<LocationModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_LOCATIONS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION))
                locationList.add(LocationModel(location))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return locationList
    }

}
