package uta.fisei.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.fragment.app.DialogFragment;

// Clase para el diálogo de selección de ancho de línea
public class LineWidthDialogFragment extends DialogFragment {
    private ImageView widthImageView;

    // Crea un AlertDialog y lo devuelve
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // Crea el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View lineWidthDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_line_width, null);
        builder.setView(lineWidthDialogView); // Agrega la interfaz gráfica al diálogo

        // Establece el mensaje del AlertDialog
        builder.setTitle(R.string.title_line_width_dialog);

        // Obtiene el ImageView
        widthImageView = (ImageView) lineWidthDialogView.findViewById(R.id.widthImageView);

        // Configura el widthSeekBar
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        final SeekBar widthSeekBar = (SeekBar) lineWidthDialogView.findViewById(R.id.widthSeekBar);
        widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged);
        widthSeekBar.setProgress(doodleView.getLineWidth());

        // Agrega el botón "Set Line Width"
        builder.setPositiveButton(R.string.button_set_line_width,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doodleView.setLineWidth(widthSeekBar.getProgress());
                    }
                }
        );

        return builder.create(); // Devuelve el diálogo
    }

    // Devuelve una referencia al MainActivityFragment
    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(
                R.id.doodleFragment);
    }

    // Informa al MainActivityFragment que el diálogo está ahora visible
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    // Informa al MainActivityFragment que el diálogo ya no está visible
    @Override
    public void onDetach() {
        super.onDetach();
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }

    // OnSeekBarChangeListener para el SeekBar en el diálogo de ancho
    private final OnSeekBarChangeListener lineWidthChanged = new OnSeekBarChangeListener() {
        final Bitmap bitmap = Bitmap.createBitmap(
                400, 100, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap); // Dibuja en el bitmap

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Configura un objeto Paint para el valor actual del SeekBar
            Paint p = new Paint();
            p.setColor(
                    getDoodleFragment().getDoodleView().getDrawingColor());
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(progress);

            // Borra el bitmap y vuelve a dibujar la línea
            bitmap.eraseColor(
                    getResources().getColor(android.R.color.transparent,
                            getContext().getTheme()));
            canvas.drawLine(30, 50, 370, 50, p);
            widthImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Método requerido, no se utiliza en este caso
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Método requerido, no se utiliza en este caso
        }
    };
}
