package uta.fisei.repasoparaprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private ListView listViewData;
    private List<String> selectedItems = new ArrayList<>();
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
                Intent intent = new Intent(ThirdActivity.this, FirtsActivity.class);
                intent.putStringArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
                setResult(RESULT_OK, intent);
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