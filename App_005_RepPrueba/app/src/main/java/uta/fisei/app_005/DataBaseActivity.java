package uta.fisei.app_005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uta.fisei.app_005.dal.ClientDAL;
import uta.fisei.app_005.entities.Client;
//import uta.fisei.app_005.dal.ClientDAL;
import uta.fisei.app_005.logic.ClientBLL;
import uta.fisei.app_005.dal.DataBaseManager;

public class DataBaseActivity extends AppCompatActivity {

    private EditText editTextCode;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextPhone;
    private EditText editTextBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        editTextCode = findViewById(R.id.editTextCode);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextBalance = findViewById(R.id.editTextBalance);
    }

    //Primera forma como sin usar clases
    public void onClickButtonInsert(View view) {
        DataBaseManager dataBaseManager = new DataBaseManager(this, "SEXTO_DB",null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseManager.getWritableDatabase();

        //String code = editTextCode.getText().toString();
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String balance = editTextBalance.getText().toString();

        /*sqLiteDatabase.execSQL("INSERT INTO Clients(Name, LastName, Phone, Balance)" +
                " VALUES('X','Y','0987860206',1000)");
        sqLiteDatabase.execSQL("INSERT INTO Clients(Name, LastName, Phone, Balance) " +
                "VALUES('"+name+"','"+lastName+"','"+phone+"',"+balance+")");*/

        ContentValues values = new ContentValues();
        //values.put("Code", code);
        values.put("Name", name);
        values.put("LastName", lastName);
        values.put("Phone", phone);
        values.put("Balance", balance);

        long count = sqLiteDatabase.insert("Clients", null, values);

        if (count == 0){
            Toast.makeText(this,"Registro NO Ingresado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Registro Ingresado CORRECTAMENTE", Toast.LENGTH_SHORT).show();
        }

        ClearFields();

        sqLiteDatabase.close();
    }

    //Insercion con clases y validaciones
    public void onClickButtonInsertWhitClass(View view) {
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String balance = editTextBalance.getText().toString();

        Client client = new Client();
        client.setName(name);
        client.setLastName(lastName);
        client.setPhone(phone);

        try {
            double balanceValue = Double.parseDouble(balance);

            // Utiliza ClientBLL para manejar la inserción y las validaciones
            ClientBLL clientBLL = new ClientBLL(this);
            long count = clientBLL.insertWithValidation(client, balanceValue);

            if (count == 0) {
                Toast.makeText(this, "Registro NO Ingresado, saldo incorrecto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro Ingresado CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }

            ClearFields();

        } catch (NumberFormatException e) {
            // Manejar error de conversión de cadena a double (por ejemplo, si balance no es un número válido)
            Toast.makeText(this, "Ingrese un valor de balance válido", Toast.LENGTH_SHORT).show();
        }
    }


    private void ClearFields() {
        editTextCode.setText("");
        editTextName.setText("");
        editTextLastName.setText("");
        editTextPhone.setText("");
        editTextBalance.setText("");
    }

    //forma del ing sin class
    public void onClickButtonSearch(View view) {
        DataBaseManager dataBaseManager = new DataBaseManager(this, "SEXTO_DB",null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseManager.getWritableDatabase();

        String code = editTextCode.getText().toString();

        String SELECT = "SELECT Name, LastName, Phone , Balance FROM Clients WHERE Code= "+ code;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT , null);

        if (cursor.moveToFirst()){
            editTextName.setText(cursor.getString(0));
            editTextLastName.setText(cursor.getString(1));
            editTextPhone.setText(cursor.getString(2));
            editTextBalance.setText(cursor.getString(3));
        }else {
            Toast.makeText(this,"Registro no Existente",
                    Toast.LENGTH_SHORT).show();
            ClearFields();
        }
        sqLiteDatabase.close();
    }

    //Busqueda con class y ademas coregido el balance 0.0
    public void onClickButtonSearchWithClass(View view) {
        String code = editTextCode.getText().toString();

        Client client = new Client();
        ClientDAL clientDAL = new ClientDAL(this);
        client = clientDAL.selectByPrimaryKey(Integer.parseInt(code));

        if (client != null) {
            Log.d("onClickSearch", "Client found: " + client.getName() + " Balance: " + client.getBalance());

            if (client.getBalance() == 0.0) {
                // Si el saldo es 0.0, muestra el mensaje y realiza las acciones necesarias
                Log.d("onClickSearch", "Client has balance of 0.0");
                Toast.makeText(this, "Registro no Existente", Toast.LENGTH_SHORT).show();
                ClearFields();
            } else {
                // Si el saldo no es 0.0, actualiza los campos de la interfaz de usuario
                editTextName.setText(client.getName());
                editTextLastName.setText(client.getLastName());
                editTextPhone.setText(client.getPhone());
                editTextBalance.setText(String.valueOf(client.getBalance()));
            }
        } else {
            // Si el cliente es null, muestra el mensaje y realiza las acciones necesarias
            Log.d("onClickSearch", "Client not found");
            Toast.makeText(this, "Registro no Existente", Toast.LENGTH_SHORT).show();
            ClearFields();
        }
    }

    //Metodo eliminar sin class
    public void onClickButtonDelete(View view){
        DataBaseManager dataBaseManager = new DataBaseManager(this, "SEXTO_DB",null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseManager.getWritableDatabase();

        String code = editTextCode.getText().toString();

        int count = sqLiteDatabase.delete("Clients","Code="+ code, null);

        if (count == 0){
            Toast.makeText(this,"El Registro NO SE PUDO ELIMINAR", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Registro ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
        }

        ClearFields();

        sqLiteDatabase.close();
    }

    //Eliminar con class
    public void onClickButtonDeleteWithClass(View view) {
        String code = editTextCode.getText().toString();

        try {
            int clientCode = Integer.parseInt(code);

            ClientDAL clientDAL = new ClientDAL(this);
            int count = clientDAL.delete(clientCode);

            if (count == 0) {
                Toast.makeText(this, "El Registro NO SE PUDO ELIMINAR", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }

            ClearFields();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un código válido", Toast.LENGTH_SHORT).show();
            ClearFields();
        }
    }

    //Metodo actulizar sin class
    public void onClickButtonUpdate(View view) {
        DataBaseManager dataBaseManager = new DataBaseManager(this, "SEXTO_DB",null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseManager.getWritableDatabase();

        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String balance = editTextBalance.getText().toString();

        String code = editTextCode.getText().toString();


        ContentValues values = new ContentValues();

        values.put("Name", name);
        values.put("LastName", lastName);
        values.put("Phone", phone);
        values.put("Balance", balance);

        //values.put("Code", code);

        long count = sqLiteDatabase.update("Clients", values, "Code="+ code, null);

        if (count == 0){
            Toast.makeText(this,"Registro NO Actualizado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Registro Actualizado CORRECTAMENTE", Toast.LENGTH_SHORT).show();
        }

        sqLiteDatabase.close();
    }

    //Metodo update con class:
    public void onClickButtonUpdateWithClass(View view) {
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String balance = editTextBalance.getText().toString();
        String code = editTextCode.getText().toString();

        try {
            int clientCode = Integer.parseInt(code);

            Client client = new Client();
            client.setCode(clientCode);
            client.setName(name);
            client.setLastName(lastName);
            client.setPhone(phone);
            client.setBalance(Double.parseDouble(balance));

            ClientDAL clientDAL = new ClientDAL(this);
            int count = clientDAL.update(client);

            if (count == 0) {
                Toast.makeText(this, "Registro NO Actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro Actualizado CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un código válido", Toast.LENGTH_SHORT).show();
        }
    }


    public void onclickButtonSelectAll(View view){
        Intent intent = new Intent(this,ShowAllActivity.class);
        startActivity(intent);
    }
}