package real.erstate.realestateagency_1.data.room

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class FavDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {
    companion object {
        const val DB_VERSION = 4
        const val DATABASE_NAME = "CoffeeDB"
        const val TABLE_NAME = "favoriteTable"
        const val KEY_ID = "id"
        const val ITEM_TITLE = "itemTitle"
        const val ITEM_IMAGE = "itemImage"
        const val FAVORITE_STATUS = "fStatus"
        const val DIL = "dil"
        const val ROOM = "room"
        const val LOCAL = "local"
        const val SAN = "san"
        const val KM = "km"
        const val PRICE = "price"
    }

    private val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ("
            + "$KEY_ID TEXT, $ITEM_TITLE TEXT, "
            + "$ITEM_IMAGE TEXT, $FAVORITE_STATUS TEXT, "
            + "$DIL TEXT, $ROOM TEXT, "
            + "$LOCAL TEXT, $SAN TEXT, $KM TEXT," +
            "$PRICE TEXT)")

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertEmpty() {
        val db = this.writableDatabase
        val cv = ContentValues()
        for (x in 1..10000) {
            cv.put(KEY_ID, x.toString())
            cv.put(FAVORITE_STATUS, "0")
            db.insert(TABLE_NAME, null, cv)
            cv.clear()
        }
    }

    fun insertIntoTheDatabase(item_title: String, item_image:String, id: String, fav_status: String, dil: String, room: String, san: String, local: String, km: String,itemId:String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(ITEM_TITLE, item_title)
        cv.put(ITEM_IMAGE, item_image)
        cv.put(KEY_ID, id)
        cv.put(DIL, dil)
        cv.put(ROOM, room)
        cv.put(SAN, san)
        cv.put(LOCAL, local)
        cv.put(KM, km)
        cv.put(PRICE, itemId)
        cv.put(FAVORITE_STATUS, fav_status)
        db.insert(TABLE_NAME, null, cv)
        Log.d("FavDB Status", "$item_title, favstatus - $fav_status - $cv")
    }

    fun readAllData(id: String): Cursor {
        val db = this.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $KEY_ID='$id'"
        return db.rawQuery(sql, null)
    }

    fun removeFav(id: String) {
        val db = this.writableDatabase
        val sql = "UPDATE $TABLE_NAME SET $FAVORITE_STATUS ='0' WHERE $KEY_ID='$id'"
        db.execSQL(sql)
        Log.d("remove", id.toString())
    }

    fun selectAllFavoriteList(): Cursor {
        val db = this.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $FAVORITE_STATUS ='1'"
        return db.rawQuery(sql, null,null)
    }
}

