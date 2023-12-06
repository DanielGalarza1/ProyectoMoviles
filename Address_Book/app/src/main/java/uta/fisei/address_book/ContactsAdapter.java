package uta.fisei.address_book;

import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uta.fisei.address_book.data.DatabaseDescription;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{
    public interface ContactClickListener{
        void onClick(Uri contactUri);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView textView;
        private long rowID;
        public ViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            clickListener.onClick(DatabaseDescription.Contact.buildContactUri(rowID));
                        }
                    }
            );
        }
        public void setRowID(long rowID){
            this.rowID = rowID;
        }
    }
    private Cursor cursor = null;
    private final ContactClickListener clickListener;
    public ContactsAdapter(ContactClickListener clickListener){
        this.clickListener = clickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            int idIndex = cursor.getColumnIndex(DatabaseDescription.Contact._ID);
            int nameIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_NAME);

            if (idIndex != -1 && nameIndex != -1) {
                holder.setRowID(cursor.getLong(idIndex));
                holder.textView.setText(cursor.getString(nameIndex));
            }
        }
    }
    @Override
    public int getItemCount(){
        return (cursor != null) ? cursor.getCount() : 0;
    }
    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}
