package uta.fisei.app_005;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uta.fisei.app_005.dal.ClientDAL;
import uta.fisei.app_005.dal.DataBaseManager;
import uta.fisei.app_005.entities.Client;

public class ShowAllActivity extends AppCompatActivity {

    private ListView listViewDataClients;
    private ArrayAdapter adapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        listViewDataClients = findViewById(R.id.listViewDataClients);

        list = getDataWithClass();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listViewDataClients.setAdapter(adapter);
    }

    private ArrayList<String> getData() {
        DataBaseManager dataBaseManager = new DataBaseManager(this, "SEXTO_DB",null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseManager.getWritableDatabase();

        String select = "SELECT * FROM Clients";

        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        ArrayList<String> listData = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(0);
                String name = cursor.getString(1);
                String lastName = cursor.getString(2);
                String phone = cursor.getString(3);
                String balance = cursor.getString(4);

                listData.add("Código: " + code);
                listData.add("Nombre: " + name);
                listData.add("Apellido: " + lastName);
                listData.add("Teléfono: " + phone);
                listData.add("Saldo: " + balance);
                listData.add(""); // Espacio entre registros

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this,"No Existen Datos", Toast.LENGTH_SHORT).show();
        }

        sqLiteDatabase.close();

        return listData;
    }

    //Con class
    private ArrayList<String> getDataWithClass() {
        ClientDAL clientDAL = new ClientDAL(this);
        ArrayList<Client> clientsList = clientDAL.selectAll();

        ArrayList<String> listData = new ArrayList<>();

        if (!clientsList.isEmpty()) {
            for (Client client : clientsList) {
                listData.add("Código: " + client.getCode());
                listData.add("Nombre: " + client.getName());
                listData.add("Apellido: " + client.getLastName());
                listData.add("Teléfono: " + client.getPhone());
                listData.add("Saldo: " + client.getBalance());
                listData.add(""); // Espacio entre registros
            }
        } else {
            Toast.makeText(this, "No Existen Datos", Toast.LENGTH_SHORT).show();
        }

        return listData;
    }

}