package uta.fisei.weatherviewer;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Weather {
    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String iconURL;

    // constructor
    public Weather(long timeStamp, double minTemp, double maxTemp,
                   double humidity, String description, String iconName) {
        // NumberFormat to format double temperatures rounded to integers
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        // Asignar valores a las variables de instancia utilizando los parámetros del constructor
        this.dayOfWeek = convertTimeStampToDay(timeStamp);
        this.minTemp = numberFormat.format(minTemp) + "°F";
        this.maxTemp = numberFormat.format(maxTemp) + "°F";
        this.humidity = NumberFormat.getPercentInstance().format(humidity / 100.0);
        this.description = description;
        this.iconURL = "http://openweathermap.org/img/w/" + iconName + ".png";
    }

    // Método para convertir la marca de tiempo en el día de la semana
    private static String convertTimeStampToDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance(); // Crear una instancia de Calendar
        calendar.setTimeInMillis(timeStamp * 1000); // Establecer la marca de tiempo (en segundos) en el Calendar
        TimeZone tz = TimeZone.getDefault(); // Obtener la zona horaria del dispositivo

        // Ajustar la hora para la zona horaria del dispositivo
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));

        // SimpleDateFormat que devuelve el nombre del día
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE");
        return dateFormatter.format(calendar.getTime()); // Formatear la fecha y obtener el nombre del día
    }


}
