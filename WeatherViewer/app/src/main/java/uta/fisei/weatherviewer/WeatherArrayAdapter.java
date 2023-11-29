package uta.fisei.weatherviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {

    // Clase para reutilizar vistas a medida que los elementos de la lista se desplazan dentro y fuera de la pantalla
    private static class ViewHolder {
        ImageView conditionImageView;
        TextView dayTextView;
        TextView lowTextView;
        TextView hiTextView;
        TextView humidityTextView;
    }

    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public WeatherArrayAdapter(Context context, List<Weather> forecast) {
        super(context, -1, forecast);
    }

    // Método para crear y devolver una vista para un elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Weather para la posición actual
        Weather day = getItem(position);

        ViewHolder viewHolder; // Objeto ViewHolder para contener vistas reutilizadas

        // Verificar si la vista actual se puede reutilizar
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            // Inicializar vistas del ViewHolder con vistas de la lista
            viewHolder.conditionImageView = convertView.findViewById(R.id.conditionImageView);
            viewHolder.dayTextView = convertView.findViewById(R.id.dayTextView);
            viewHolder.lowTextView = convertView.findViewById(R.id.lowTextView);
            viewHolder.hiTextView = convertView.findViewById(R.id.hiTextView);
            viewHolder.humidityTextView = convertView.findViewById(R.id.humidityTextView);

            // Establecer el objeto ViewHolder como etiqueta de la vista
            convertView.setTag(viewHolder);
        } else {
            // Si la vista se puede reutilizar, obtener el ViewHolder de la etiqueta
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Si el ícono de condición del tiempo ya se descargó, úsalo;
        // de lo contrario, descarga el ícono en un hilo separado
        if (bitmaps.containsKey(day.iconURL)) {
            viewHolder.conditionImageView.setImageBitmap(bitmaps.get(day.iconURL));
        } else {
            // Descargar y mostrar la imagen de la condición del tiempo
            new LoadImageTask(viewHolder.conditionImageView).execute(day.iconURL);
        }

        // Obtener otros datos del objeto Weather y colocarlos en las vistas
        Context context = getContext(); // Para cargar recursos de cadena
        viewHolder.dayTextView.setText(context.getString(R.string.day_description, day.dayOfWeek, day.description));
        viewHolder.lowTextView.setText(context.getString(R.string.low_temp, day.minTemp));
        viewHolder.hiTextView.setText(context.getString(R.string.high_temp, day.maxTemp));
        viewHolder.humidityTextView.setText(context.getString(R.string.humidity, day.humidity));

        return convertView; // Devolver el elemento de lista completado para mostrar
    }


    // Clase AsyncTask para descargar imágenes de forma asíncrona
    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView; // muestra la miniatura

        // Almacena ImageView en el que se establecerá el Bitmap descargado
        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        // Carga la imagen; params[0] es la URL de la imagen en formato String
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]); // crea URL para la imagen

                // abre una HttpURLConnection, obtiene su InputStream
                // y descarga la imagen
                connection = (HttpURLConnection) url.openConnection();

                try (InputStream inputStream = connection.getInputStream()) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.put(params[0], bitmap); // caché para uso posterior
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return bitmap;
        }

        // Establece la imagen de la condición del tiempo en el elemento de lista
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
