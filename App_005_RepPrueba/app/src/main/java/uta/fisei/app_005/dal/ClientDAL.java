package uta.fisei.app_005.dal;

import uta.fisei.app_005.entities.Client;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import uta.fisei.app_005.dal.DataBaseManager;

public class ClientDAL {
    private static final String DB_NAME = "SEXTO_DB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Clients";
    private static final String COLUMN_CODE = "Code";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_LAST_NAME = "LastName";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_BALANCE = "Balance";

    private DataBaseManager dataBaseManager;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public ClientDAL(Context context) {
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

    public long insert(Client client) {
        open(true);
        long count = 0;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, client.getName());
            values.put(COLUMN_LAST_NAME, client.getLastName());
            values.put(COLUMN_PHONE, client.getPhone());
            values.put(COLUMN_BALANCE, client.getBalance());

            count = sqLiteDatabase.insert(TABLE_NAME, null, values);

        } finally {
            closeDatabase();
        }
        return count;
    }

    public Client selectByPrimaryKey(int code) {
        open(false);

        Client client = null;

        try {
            String SELECT = "SELECT " + COLUMN_NAME + ", " + COLUMN_LAST_NAME + ", " +
                    COLUMN_PHONE + ", " + COLUMN_BALANCE +
                    " FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_CODE + " = " + code;

            Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);

            client = new Client();
            if (cursor.moveToFirst()) {
                client.setName(cursor.getString(0));
                client.setLastName(cursor.getString(1));
                client.setPhone(cursor.getString(2));
                client.setBalance(Double.parseDouble((cursor.getString(3))));
            }
        } finally {
            closeDatabase();
        }
        return client;
    }

    public ArrayList<Client> selectAll() {
        open(false);

        ArrayList<Client> clientsList = null;

        try {
            String SELECT = "SELECT " + COLUMN_CODE + ", " + COLUMN_NAME + ", " +
                    COLUMN_LAST_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_BALANCE +
                    " FROM " + TABLE_NAME;

            Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
            if (cursor.moveToFirst()) {
                clientsList = new ArrayList<Client>();
                do {
                    Client client = new Client();
                    client.setCode(Integer.parseInt(cursor.getString(0)));
                    client.setName(cursor.getString(1));
                    client.setLastName(cursor.getString(2));
                    client.setPhone(cursor.getString(3));
                    client.setBalance(Double.parseDouble(cursor.getString(4)));

                    clientsList.add(client);
                } while (cursor.moveToNext());
            }
        } finally {
            closeDatabase();
        }
        return clientsList;
    }

    public int delete(int code) {
        open(true);

        int count = 0;

        try {
            count = sqLiteDatabase.delete(TABLE_NAME, COLUMN_CODE + "=" + code, null);
        } finally {
            closeDatabase();
        }
        return count;
    }

    public int update(Client client) {
        open(true);

        int count = 0;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, client.getName());
            values.put(COLUMN_LAST_NAME, client.getLastName());
            values.put(COLUMN_PHONE, client.getPhone());
            values.put(COLUMN_BALANCE, client.getBalance());

            count = sqLiteDatabase.update(TABLE_NAME, values, COLUMN_CODE + "=" + client.getCode(), null);

        } finally {
            closeDatabase();
        }
        return count;
    }

    private void closeDatabase() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }
}
