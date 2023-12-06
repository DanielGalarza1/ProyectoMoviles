package uta.fisei.app_005.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseManager extends SQLiteOpenHelper {

    final String CREATE_CLIENTS = "CREATE TABLE Clients (Code Integer PRIMARY KEY AUTOINCREMENT, Name TEXT, " +
            "lastname TEXT, Phone TEXT, Balance REAL)";

    final String CREATE_USERS = "CREATE TABLE Users (Id Integer PRIMARY KEY AUTOINCREMENT, User TEXT, " +
            "Password TEXT)";

    final String DROP_TABLE_CLIENTS = "DROP TABLE IF EXISTS Clients";
    final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS Users";

    public DataBaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLIENTS);
        db.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CLIENTS);
        db.execSQL(DROP_TABLE_USERS);
        onCreate(db);
    }
}
