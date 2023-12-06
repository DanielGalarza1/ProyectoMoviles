package uta.fisei.weatherviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private List<Weather> weatherList = new ArrayList<>();
    private WeatherArrayAdapter weatherArrayAdapter;
    private ListView weatherListView;

    // Configurar Toolbar, ListView y FAB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // código autogenerado para inflar el diseño y configurar la Toolbar
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Crear ArrayAdapter para vincular weatherList a weatherListView
        weatherListView = (ListView) findViewById(R.id.weatherListView);
        weatherArrayAdapter = new WeatherArrayAdapter(this, weatherList);
        weatherListView.setAdapter(weatherArrayAdapter);

        // Configurar FAB para ocultar el teclado y comenzar la solicitud del servicio web
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // obtener texto de locationEditText
                EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
                String cityName = locationEditText.getText().toString().trim();

                // Verificar si el nombre de la ciudad está vacío
                if (cityName.isEmpty()) {
                    // Mostrar un mensaje indicando que se debe ingresar la ciudad usando Toast
                    Toast.makeText(MainActivity.this, R.string.empy_city, Toast.LENGTH_LONG).show();
                    // Limpiar la lista y notificar al adaptador
                    weatherList.clear();
                    weatherArrayAdapter.notifyDataSetChanged();
                } else {
                    // Crear URL del servicio web y comenzar la tarea
                    URL url = createURL(cityName);
                    if (url != null) {
                        dismissKeyboard(locationEditText);
                        GetWeatherTask getLocalWeatherTask = new GetWeatherTask();
                        getLocalWeatherTask.execute(url);
                    } else {
                        // Mostrar mensaje de URL inválida usando Toast
                        Toast.makeText(MainActivity.this, R.string.invalid_url, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Crear URL del servicio web de openweathermap.org usando la ciudad
    private URL createURL(String city) {
        String apiKey = getString(R.string.api_key);
        String baseUrl = getString(R.string.web_service_url);

        try {
            // Crear URL para la ciudad especificada y unidades imperiales (Fahrenheit)
            String urlString = baseUrl + URLEncoder.encode(city, "UTF-8") +
                    "&units=imperial&cnt=16&APPID=" + apiKey;
            return new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // La URL estaba mal formada
    }

    // Realiza la llamada al servicio web REST para obtener datos meteorológicos y
// guarda los datos en un archivo HTML local
    private class GetWeatherTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        Snackbar.make(findViewById(R.id.coordinatorLayout),
                                R.string.read_error, Snackbar.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    return new JSONObject(builder.toString());
                } else {
                    Snackbar.make(findViewById(R.id.coordinatorLayout),
                            R.string.connect_error, Snackbar.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        R.string.connect_error, Snackbar.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return null;
        }

        // Procesa la respuesta JSON y actualiza el ListView
        @Override
        protected void onPostExecute(JSONObject weather) {
            if (weather != null) {
                convertJSONtoArrayList(weather);
                weatherArrayAdapter.notifyDataSetChanged(); // volver a enlazar con ListView
                weatherListView.smoothScrollToPosition(0); // desplazar hacia arriba
            } else {
                // Limpiar la lista y notificar al adaptador
                weatherList.clear();
                weatherArrayAdapter.notifyDataSetChanged();
            }
        }
    }

    private void convertJSONtoArrayList(JSONObject forecast) {
        weatherList.clear();
        try {
            // Imprimir el contenido del objeto JSON antes de procesarlo
            Log.d("ConvertJSON", "Contenido del objeto JSON: " + forecast.toString());

            // Verificar si la clave "list" está presente en el objeto JSON
            if (forecast != null && forecast.has("list")) {
                JSONArray list = forecast.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject day = list.getJSONObject(i);

                    // Verificar si la clave "main" y "humidity" están presentes en el objeto "day"
                    if (day.has("main") && day.getJSONObject("main").has("humidity")) {
                        double humidity = day.getJSONObject("main").getDouble("humidity");

                        // Verificar si la clave "weather" está presente en el objeto "day"
                        if (day.has("weather") && day.getJSONArray("weather").length() > 0) {
                            JSONObject weather = day.getJSONArray("weather").getJSONObject(0);

                            // Obtener otros valores necesarios
                            double minTemperature = day.getJSONObject("main").getDouble("temp_min");
                            double maxTemperature = day.getJSONObject("main").getDouble("temp_max");

                            weatherList.add(new Weather(
                                    day.getLong("dt"), // date/time timestamp
                                    minTemperature, // min temp
                                    maxTemperature, // max temp
                                    humidity, // percent humidity
                                    weather.getString("description"), // weather conditions
                                    weather.getString("icon") // icon name
                            ));
                        } else {
                            // La clave "weather" no está presente en el objeto "day"
                            // Puedes manejar este caso según tus necesidades.
                            // Por ejemplo, imprimir un mensaje de error o tomar una acción predeterminada.
                            Log.e("ConvertJSON", "No se encontró la clave 'weather' en el objeto JSON para el elemento " + i);
                        }
                    } else {
                        // La clave "main" o "humidity" no está presente en el objeto "day"
                        // Puedes manejar este caso según tus necesidades.
                        // Por ejemplo, imprimir un mensaje de error o tomar una acción predeterminada.
                        Log.e("ConvertJSON", "No se encontró la clave 'main' o 'humidity' en el objeto JSON para el elemento " + i);
                    }
                }
            } else {
                // La clave "list" no está presente en el objeto JSON o el pronóstico es nulo
                // Puedes manejar este caso según tus necesidades.
                // Por ejemplo, imprimir un mensaje de error o tomar una acción predeterminada.
                Log.e("ConvertJSON", "No se encontró la clave 'list' en el objeto JSON o el pronóstico es nulo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
