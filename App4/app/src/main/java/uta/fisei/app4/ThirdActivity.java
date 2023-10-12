package uta.fisei.app4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private ListView listViewData;
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

    private List<String> loadData() {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 20; i++){
            String item = "Numero: "+ i;
            list.add(item);
        }

        return list;
    }

}