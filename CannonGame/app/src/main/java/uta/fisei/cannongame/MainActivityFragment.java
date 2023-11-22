package uta.fisei.cannongame;

import android.os.Bundle;
import android.media.AudioManager;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivityFragment extends Fragment {

    // Referencia a la vista personalizada para mostrar el juego (CannonView)
    private CannonView cannonView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Llama al método onCreateView de la clase base (Fragment)
        super.onCreateView(inflater, container, savedInstanceState);

        // Infla el diseño fragment_main.xml
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Obtiene una referencia a CannonView
        cannonView = (CannonView) view.findViewById(R.id.cannonView);

        // Devuelve la vista inflada
        return view;
    }

    // Método llamado cuando la actividad se ha creado completamente
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Llama al método onActivityCreated de la clase base (Fragment)
        super.onActivityCreated(savedInstanceState);

        // Permite que los botones de volumen controlen el volumen del juego
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    // Método llamado cuando la actividad principal se pausa
    @Override
    public void onPause() {
        // Llama al método onPause de la clase base (Fragment)
        super.onPause();

        // Detiene el juego
        cannonView.stopGame();
    }

    // Método llamado cuando la actividad principal se destruye
    @Override
    public void onDestroy() {
        // Llama al método onDestroy de la clase base (Fragment)
        super.onDestroy();

        // Libera los recursos utilizados por CannonView
        cannonView.releaseResources();
    }

}