package zisac.com.pe.salutem24.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import zisac.com.pe.salutem24.utils.Constantes;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private String tipoFecha;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener, String tipoFecha) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener, tipoFecha);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener, String tipoFecha) {
        this.listener = listener;
        this.tipoFecha = tipoFecha;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        c.getTimeInMillis();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        if(tipoFecha.equals(Constantes.FECHA_FILTRO_RESERVAR_CONSULTA)) datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        if(tipoFecha.equals(Constantes.FECHA_NACIMIENTO_AGREGAR_PACIENTE)) datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        if(tipoFecha.equals(Constantes.FECHA_PROGRAMADA)) datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return datePickerDialog;
    }
}
