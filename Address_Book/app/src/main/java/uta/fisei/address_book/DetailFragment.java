package uta.fisei.address_book;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import uta.fisei.address_book.data.DatabaseDescription;

public class DetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    public interface DetailFragmentListener {
        void onContactDeleted();

        void onEditContact(Uri contactUri);
    }

    private static final int CONTACT_LOADER = 0;
    private DetailFragmentListener listener;
    private Uri contactUri;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView streetTextView;
    private TextView cityTextView;
    private TextView stateTextView;
    private TextView zipTextView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DetailFragmentListener) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        Bundle arguments = getArguments();
        if (arguments != null) {
            contactUri = arguments.getParcelable(MainActivity.CONTACT_URI);
        }
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        nameTextView = (TextView) view.findViewById(R.id.nameValueTextView);
        phoneTextView = (TextView) view.findViewById(R.id.phoneValueTextView);
        emailTextView = (TextView) view.findViewById(R.id.emailValueTextView);
        streetTextView = (TextView) view.findViewById(R.id.streetValueTextView);
        cityTextView = (TextView) view.findViewById(R.id.cityValueTextView);
        stateTextView = (TextView) view.findViewById(R.id.stateValueTextView);
        zipTextView = (TextView) view.findViewById(R.id.zipValueTextView);
        getLoaderManager().initLoader(CONTACT_LOADER, null, this);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_details_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                listener.onEditContact(contactUri);
                return true;
            case R.id.action_delete:
                deleteContact();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Campos del fragment
    private Context context;

    // Método para establecer el URI del contacto
    public void setContactUri(Uri uri) {
        contactUri = uri;
    }

    // Al crearse el fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    // Método para eliminar contacto
    private void deleteContact() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirm_title);
        builder.setMessage(R.string.confirm_message);

        builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ContentResolver resolver = context.getContentResolver();
                resolver.delete(contactUri, null, null);

                // Notificar eliminación
                listener.onContactDeleted();
            }

        });

        builder.setNegativeButton(R.string.button_cancel, null);
        builder.show();

    }

    /*
    // Método para eliminar un contacto
    private void deleteContact() {
        // Utilizar FragmentManager para mostrar el DialogFragment de confirmación de eliminación
        confirmDelete.show(getParentFragmentManager(), "confirm delete");
    }

    // DialogFragment para confirmar la eliminación del contacto
    private final DialogFragment confirmDelete = new DialogFragment() {
        // Crear un AlertDialog y devolverlo
        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            // Crear un nuevo AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(R.string.confirm_title); // Establecer el título del diálogo
            builder.setMessage(R.string.confirm_message); // Establecer el mensaje del diálogo

            // Proporcionar un botón OK que simplemente cierra el diálogo
            builder.setPositiveButton(R.string.button_delete,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int button) {
                            // Utilizar ContentResolver de la actividad para invocar
                            // la eliminación en el AddressBookContentProvider
                            getActivity().getContentResolver().delete(contactUri, null, null);

                            // Notificar al escucha (listener) que el contacto ha sido eliminado
                            listener.onContactDeleted();
                        }
                    }
            );

            // Botón de cancelar que no hace nada
            builder.setNegativeButton(R.string.button_cancel, null);

            // Devolver el AlertDialog
            return builder.create();
        }
    };*/


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        CursorLoader cursorLoader;
        switch (id){
            case CONTACT_LOADER:
                cursorLoader = new CursorLoader(getActivity(),
                        contactUri, null, null, null, null);
                break;
            default:
                cursorLoader = null;
                break;
        }
        return cursorLoader;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        if(data != null && data.moveToFirst()){
            int nameIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_NAME);
            int phoneIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_PHONE);
            int emailIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_EMAIL);
            int streetIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_STREET);
            int cityIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_CITY);
            int stateIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_STATE);
            int zipIndex = data.getColumnIndex(DatabaseDescription.Contact.COLUMN_ZIP);
            nameTextView.setText(data.getString(nameIndex));
            phoneTextView.setText(data.getString(phoneIndex));
            emailTextView.setText(data.getString(emailIndex));
            streetTextView.setText(data.getString(streetIndex));
            cityTextView.setText(data.getString(cityIndex));
            stateTextView.setText(data.getString(stateIndex));
            zipTextView.setText(data.getString(zipIndex));
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
    }
}