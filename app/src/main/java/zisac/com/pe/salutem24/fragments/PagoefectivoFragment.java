package zisac.com.pe.salutem24.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.entity.CipPEResponse;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagoefectivoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagoefectivoFragment extends Fragment {
    Button btn_continuar_pe;
    private View rootView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PagoefectivoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PagoefectivoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PagoefectivoFragment newInstance(String param1, String param2) {
        PagoefectivoFragment fragment = new PagoefectivoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pagoefectivo, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_continuar_pe = rootView.findViewById( R.id.btn_continuar_pe );
        addOnclickListener();
    }

    private void addOnclickListener(){
        btn_continuar_pe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.redOrWifiActivo(getActivity())) {
                    Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }
                Toast.makeText(getActivity(), "ENTRA", Toast.LENGTH_LONG).show();

            }
        });
    }
    public class GeneracionCip extends AsyncTask<String,Void, CipPEResponse> {

        @Override
        protected CipPEResponse doInBackground(String... strings) {
            return null;
        }
    }
}