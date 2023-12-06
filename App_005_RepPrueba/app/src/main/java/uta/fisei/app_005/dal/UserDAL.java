package uta.fisei.app_005.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAL {

    private static final String DB_NAME = "SEXTO_DB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_USER = "User";
    private static final String COLUMN_PASSWORD = "Password";

    private DataBaseManager dataBaseManager;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public UserDAL(Context context) {
        this.context = context;
    }

    private void open(boolean openMode) {
        dataBaseManager = new DataBaseManager(context, DB_NAME, null, DB_VERSION);

        if (openMode) {
            sqLiteDatabase = dataBaseManager.getWritableDatabase();
        } else {
            sqLiteDatabase = dataBaseManager.getReadableDatabase();
        }
    }

    public long addUser(String username, String password) {
        open(true);
        long count = 0;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER, username);
            values.put(COLUMN_PASSWORD, password);

            count = sqLiteDatabase.insert(TABLE_NAME, null, values);

        } finally {
            closeDatabase();
        }
        return count;
    }

    public boolean login(String username, String password) {
        open(false);

        try {
            Cursor cursor = sqLiteDatabase.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER + " = ? AND " + COLUMN_PASSWORD + " = ?",
                    new String[]{username, password});

            return cursor.moveToFirst();
        } finally {
            closeDatabase();
        }
    }

    private void closeDatabase() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }
}

