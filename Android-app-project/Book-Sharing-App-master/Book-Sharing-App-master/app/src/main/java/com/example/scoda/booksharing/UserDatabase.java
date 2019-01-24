package com.example.scoda.booksharing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by scoda on 11/20/2016.
 */
public class UserDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserDatabase.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableClass.EntryClass.TABLE_NAME + " (" +
                    TableClass.EntryClass._ID + " INTEGER PRIMARY KEY," +
                    TableClass.EntryClass.NAME + " TEXT,"+
                    TableClass.EntryClass.EMAIL + " TEXT,"+
                    TableClass.EntryClass.PHONE_NUMBER + " TEXT," +
                    TableClass.EntryClass.USER_PASSWORD + TEXT_TYPE +
                    TableClass.EntryClass.USER_LOCATION + "LOCATION" +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableClass.EntryClass.TABLE_NAME;

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public final class TableClass {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public TableClass() {}

        /* Inner class that defines the table contents */
        public  abstract class EntryClass implements BaseColumns {
            public static final String TABLE_NAME = "AllUsers";
            public static final String EMAIL = "UserName";
            public static final String NAME = "Name";
            public static final String USER_PASSWORD = "UserPassword";
            public static final String PHONE_NUMBER = "PhoneNumber";
            public static final String USER_LOCATION = "mCurrentLocation";
        }
    }
}
