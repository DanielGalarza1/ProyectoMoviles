package uta.fisei.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.DialogFragment;

public class ColorDialogFragment extends DialogFragment {
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View colorView;
    private int color;
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // crea el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_color, null);
        builder.setView(colorDialogView);

        // establece el mensaje del AlertDialog
        builder.setTitle(R.string.title_color_dialog);

        // obtén los SeekBars de color y establece sus listeners de cambio
        alphaSeekBar = (SeekBar) colorDialogView.findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) colorDialogView.findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) colorDialogView.findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) colorDialogView.findViewById(R.id.blueSeekBar);
        colorView = colorDialogView.findViewById(R.id.colorView);

        // registra los listeners de eventos de SeekBar
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        // usa el color de dibujo actual para establecer los valores de SeekBar
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        color = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        // Configura el colorView con el color actual
        updateColorView();
        
        // agrega el botón Set Color
        builder.setPositiveButton(R.string.button_set_color,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doodleView.setDrawingColor(color);
                    }
                });

        return builder.create(); // devuelve el diálogo
    }

    private void updateColorView() {
        colorView.setBackgroundColor(color);
    }

    // Obtiene una referencia al MainActivityFragment
    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.doodleFragment);
    }

    // Indica a MainActivityFragment que el diálogo ahora está siendo mostrado
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    // Indica a MainActivityFragment que el diálogo ya no está siendo mostrado
    @Override
    public void onDetach() {
        super.onDetach();
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }


    // OnSeekBarChangeListener para los SeekBars en el diálogo de color
    private final SeekBar.OnSeekBarChangeListener colorChangedListener = new SeekBar.OnSeekBarChangeListener() {
        // Método llamado cuando el usuario cambia el progreso del SeekBar
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                // Actualiza el color según los valores de los SeekBars
                color = Color.argb(alphaSeekBar.getProgress(),
                        redSeekBar.getProgress(),
                        greenSeekBar.getProgress(),
                        blueSeekBar.getProgress());

                // Actualiza el color de la vista de previsualización
                colorView.setBackgroundColor(color);
            }
        }

        // Método llamado cuando se toca el SeekBar (no utilizado en este caso)
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Método requerido, no se utiliza en este caso
        }

        // Método llamado cuando se deja de tocar el SeekBar (no utilizado en este caso)
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Método requerido, no se utiliza en este caso
        }
    };

}
