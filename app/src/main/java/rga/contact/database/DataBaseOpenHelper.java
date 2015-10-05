package rga.contact.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

	public DataBaseOpenHelper(String name, int version, Context context) {
		super(context, name, null, version);
	}

    @Override
	public void onCreate(SQLiteDatabase database) {
        database.execSQL(ContactDaoSqlite.CREATE_TABLE_CONTACT);
    }

    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "	+ ContactDaoSqlite.TABLE_CONTACT);
		onCreate(db);
	}
}
