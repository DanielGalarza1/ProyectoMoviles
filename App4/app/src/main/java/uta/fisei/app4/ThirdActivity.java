package uta.fisei.app4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    private ListView listViewData;
    private List<String> selectedItems = new ArrayList<>();

    //Mi forma de enviar los datos en una lista uno por uno y mcon boton de confirmacion
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        listViewData = findViewById(R.id.listViewData);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loadData());
        listViewData.setAdapter(adapter);

        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelect = (String) listViewData.getAdapter().getItem(i);
                if (selectedItems.contains(itemSelect)) {
                    // Si el elemento ya está seleccionado, desmarcarlo y quitarlo de la lista
                    selectedItems.remove(itemSelect);
                } else {
                    // Si el elemento no está seleccionado, marcarlo y agregarlo a la lista
                    selectedItems.add(itemSelect);
                }
            }
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar los elementos seleccionados a MainActivity
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                intent.putStringArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }*/

    //Forma ing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        listViewData = findViewById(R.id.listViewData);
        //Agarra los datos y coje la info
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loadData());

        listViewData.setAdapter(adapter);

        //Obtener el item seleccionado dentro del ListView
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelect = (String) listViewData.getAdapter().getItem(i);
                /*Toast.makeText(getApplicationContext(), "Selecciono:" + itemSelect,
                        Toast.LENGTH_SHORT).show();
                 */
                Intent intent = new Intent();
                intent.setData(Uri.parse(itemSelect));

                //comprobar que todo funciona de acuerdo a los parametros
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }

    //este si ocupo para ambos
     private List<String> loadData() {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 20; i++) {
            String item = "Numero: " + i;
            list.add(item);
        }
        return list;
    }
}
