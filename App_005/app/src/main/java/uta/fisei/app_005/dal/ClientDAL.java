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
    private DataBaseManager dataBaseManager;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public ClientDAL(Context context) {
        this.context = context;
    }

    public void openReadable(){
        dataBaseManager = new DataBaseManager(context, "SEXTO_TI",null,1);
        sqLiteDatabase = dataBaseManager.getReadableDatabase();
    }

    private void open(boolean openMode){
        dataBaseManager = new DataBaseManager(context, "SEXTO_TI", null, 1);

        if (openMode) {
            sqLiteDatabase = dataBaseManager.getWritableDatabase();
        }else {
            sqLiteDatabase = dataBaseManager.getReadableDatabase();
        }
    }

    public long insert(Client client){
        open(true);
        long count = 0;

        try {
            ContentValues values = new ContentValues();

            values.put("Name", client.getName());
            values.put("LastName", client.getLastName());
            values.put("Phone", client.getPhone());
            values.put("Balance", client.getBalance());

            count = sqLiteDatabase.insert("Clients", null, values);

        }catch (Exception e){
            throw e;
        }finally {
            sqLiteDatabase.close();
        }
        return count;
    }

    public Client selectByPrimaryKey(int code){
        open(false);

        Client client = null;
        try {
            String SELECT = "SELECT Name, LastName, Phone , Balance " +
                            "FROM Clients " +
                            "WHERE Code= "+ code;

            Cursor cursor = sqLiteDatabase.rawQuery(SELECT , null);

            if (cursor.moveToFirst()){
                client.setName(cursor.getString(0));
                client.setLastName(cursor.getString(1));
                client.setPhone(cursor.getString(2));
                client.setBalance(Double.parseDouble(cursor.getString(3)));
            }
        }catch (Exception e){
            throw e;
        }finally {
            sqLiteDatabase.close();
        }
        return client;
    }

    public ArrayList<Client> selectAll(){
        open(false);

        ArrayList<Client> clientsList = null;

        try {
            String SELECT = "SELECT Code, Name, LastName, Phone , Balance " +
                            "FROM Clients";

            Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
            if (cursor.moveToFirst()){
                clientsList = new ArrayList<Client>();
                do {
                    Client client = new Client();
                    client.setCode(Integer.parseInt(cursor.getString(0)));
                    client.setName((cursor.getString(1)));
                    client.setLastName((cursor.getString(2)));
                    client.setPhone((cursor.getString(3)));
                    client.setBalance(Double.parseDouble(cursor.getString(4)));

                    clientsList.add(client);
                }
                while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw e;
        }finally {
            sqLiteDatabase.close();
        }
        return clientsList;
    }


    public int delete (int code){
        open(true);

        int count = 0;

        try {
            count = sqLiteDatabase.delete("Clients","Code="+ code, null);
        }catch (Exception e){
            throw e;
        }finally {
            sqLiteDatabase.close();
        }
        return count;
    }

    public int update(Client client){
        open(true);

        int count = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("Name", client.getName());
            values.put("LastName", client.getLastName());
            values.put("Phone", client.getPhone());
            values.put("Balance", client.getBalance());

            count = sqLiteDatabase.update("Clients", values, "Code="+ client.getCode(), null);

        }catch (Exception e){
            throw e;
        }finally {
            sqLiteDatabase.close();
        }
        return count;
    }



}
