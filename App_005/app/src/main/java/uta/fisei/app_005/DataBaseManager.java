package uta.fisei.app_005;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseManager extends SQLiteOpenHelper {

    final String CREATE_CLIENTS = "CREATE TABLE Clients (Code Integer PRIMARY KEY AUTOINCREMENT, Name TEXT, " +
                                  "lastname TEXT, Phone TEXT, Balance REAL)";

    final String DROP_TABLE = "DROP TABLE IF EXISTS CLIENTS";

    public DataBaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // sql para definir la estrutura de la DB.
        db.execSQL(CREATE_CLIENTS);
        //aqui tambien vale crear relaciones
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // sql para realizar cambios en la estructura de la base de datos.
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_CLIENTS);
    }
}
