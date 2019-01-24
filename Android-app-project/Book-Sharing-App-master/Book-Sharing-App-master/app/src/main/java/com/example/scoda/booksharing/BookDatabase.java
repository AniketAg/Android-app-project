package com.example.scoda.booksharing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by  on 01-Jan-16.
 */
public class BookDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookDatabase.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableClass.EntryClass.TABLE_NAME + " (" +
                    TableClass.EntryClass._ID + " INTEGER PRIMARY KEY," +
                    TableClass.EntryClass.BOOK_NAME + " TEXT," +
                    TableClass.EntryClass.BOOK_AUTHOR + " TEXT," +
                    TableClass.EntryClass.POSTED_BY + " TEXT," +
                    TableClass.EntryClass.BOOK_PRICE + " TEXT" +
     // Any other options for the CREATE command
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableClass.EntryClass.TABLE_NAME;

    public BookDatabase(Context context) {
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
            public static final String TABLE_NAME = "AllBooks";
            public static final String BOOK_NAME = "BookName";
            public static final String POSTED_BY = "PostedBy";
            public static final String BOOK_AUTHOR = "BookAuthor";
            public static final String BOOK_PRICE = "BookPrice";

        }
    }
}
