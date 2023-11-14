package uta.fisei.app_005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    private void ClearFields() {
        editTextCode.setText("");
        editTextName.setText("");
        editTextLastName.setText("");
        editTextPhone.setText("");
        editTextBalance.setText("");
    }

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
}