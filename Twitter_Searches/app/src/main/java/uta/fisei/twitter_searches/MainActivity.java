package uta.fisei.twitter_searches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SEARCHES = "searches";

    private EditText queryEditText;
    private EditText tagEditText;
    private FloatingActionButton saveFloatingActionButton;
    private SharedPreferences savedSearches;
    private List<String> tags;
    private SearchesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queryEditText = ((TextInputLayout) findViewById(R.id.queryTextInputLayout)).getEditText();
        queryEditText.addTextChangedListener(textWatcher);
        tagEditText = ((TextInputLayout)findViewById(R.id.tagTextInputLayout)).getEditText();
        tagEditText.addTextChangedListener(textWatcher);
        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);
        tags = new ArrayList<>(savedSearches.getAll().keySet());
        //We then call Collections.sort to sort our ArrayList.
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchesAdapter(tags, itemClickListener, itemLongClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider(this));
        saveFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        saveFloatingActionButton.setOnClickListener(saveButtonListener);

        updateSaveFAB();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateSaveFAB();

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    private void updateSaveFAB(){


        if (queryEditText.getText().toString().isEmpty() || tagEditText.getText().toString().isEmpty())
            saveFloatingActionButton.hide();
        else
            saveFloatingActionButton.show();
    }


    private final View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String query = queryEditText.getText().toString();
            String tag = tagEditText.getText().toString();

            if (!query.isEmpty() && !tag.isEmpty()){

                ((InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);

                addTaggedSearch(tag, query);
                queryEditText.setText("");
                tagEditText.setText("");
                queryEditText.requestFocus();
            }
        }
    };



    private void addTaggedSearch(String tag, String query) {
        SharedPreferences.Editor preferencesEditor = savedSearches.edit();

        // Verificar si ya existe una búsqueda con el mismo tag
        if (tags.contains(tag)) {
            // Puedes manejar esto según tus requisitos, por ejemplo, mostrar un diálogo para confirmar el reemplazo
            showReplaceConfirmationDialog(tag, query, preferencesEditor);
        } else {
            // Si no existe, agregar la nueva búsqueda
            preferencesEditor.putString(tag, query);
            preferencesEditor.apply();

            // Verificar si es un nuevo tag
            if (!tags.contains(tag)) {
                // Agregar el nuevo tag a la lista y ordenarla
                tags.add(tag);
                Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private void showReplaceConfirmationDialog(final String tag, final String query, final SharedPreferences.Editor preferencesEditor) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.replace_search_confirmation, tag))
                .setPositiveButton(getString(R.string.replace), (dialog, which) -> {
                    // Reemplazar la búsqueda existente
                    preferencesEditor.putString(tag, query).apply();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }



    private final View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //First we get the Text of the View touched. This is the tag for a search.
            String tag = ((TextView) v).getText().toString();
            String urlString = getString(R.string.search_URL) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            //Show results in web browser.
            startActivity(webIntent);

        }
    };

    private final View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            final String tag = ((TextView) v).getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.share_edit_delete_title, tag));
            builder.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0: //user clicks Share
                            shareSearch(tag);
                            break;
                        case 1: //user clicks Edit
                            //display the search'query and tag in the EditTexts
                            tagEditText.setText(tag);
                            queryEditText.setText(savedSearches.getString(tag, ""));
                            break;
                        case 2://user clicks Delete
                            deleteSearch(tag);
                            break;
                    }
                }
            });

            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.create().show(); //create and show the AlertDialog.
            return true;
        }
    };


    private void shareSearch(String tag){

        String urlString = getString(R.string.search_URL) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message, urlString));
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_search)));

    }

    private void deleteSearch(final String tag){

        //First we create an AlertDialog.Builder to start building our AlertDialog.
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);

        //We set its message with our formatted String and format specifier.
        confirmBuilder.setMessage(getString(R.string.confirm_message, tag));

        //Then we config its negative (CANCEL) button to dismiss the AlertDialog if clicked.
        confirmBuilder.setNegativeButton(getString(R.string.cancel), null);

        //Then we config its positive (DELETE) button to remove the search.
        confirmBuilder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //We remove the tag from the "tags" List.
                tags.remove(tag);
                //Then we remove it from SharedPreferences, for which we must first get a SP.Editor object.
                SharedPreferences.Editor preferencesEditor = savedSearches.edit();
                preferencesEditor.remove(tag); //remove from SP.
                preferencesEditor.apply();     //commit the change.
                //notify the Adapter the underlying data has changed so it can update itself.
                adapter.notifyDataSetChanged();
            }
        });

        confirmBuilder.create().show();
    }

}
