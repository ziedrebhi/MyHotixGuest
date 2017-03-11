package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.Arrangement;
import com.hotix.myhotixguest.entities.DataReservationModel;
import com.hotix.myhotixguest.entities.Tarif;
import com.hotix.myhotixguest.entities.TypeProduit;
import com.hotix.myhotixguest.entities.UserInfoModel;
import com.hotix.myhotixguest.updater.SpinTarifAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReservationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String FRAG_TAG_DATE_PICKER2 = "fragment_date_picker_name2";
    EditText dateArr, dateDep;
    MaterialBetterSpinner chambres, adultes, enfants, arrang, typeChb;
    MaterialDialog.Builder msgConnecting;
    MaterialDialog dialog;
    TextView msgEmpty;
    Hashtable listTarif = new Hashtable();
    Hashtable listTypeProduit = new Hashtable();
    Hashtable listArrangement = new Hashtable();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private SpinTarifAdapter adapterTarif;

    public ReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        Button button = (Button) view.findViewById(R.id.dispo);

        dateArr = (EditText) view.findViewById(R.id.dateArr);
        dateDep = (EditText) view.findViewById(R.id.dateDep);
        ShowDialogMaterial(true);
        dateArr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()

                            .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                                @Override
                                public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                    Toast.makeText(getActivity(), "Date: " + year + " " + monthOfYear + 1 + " " + dayOfMonth, Toast.LENGTH_LONG).show();
                                    dateArr.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
                                }
                            }).setFirstDayOfWeek(Calendar.MONDAY);

                    cdp.show(getChildFragmentManager(), FRAG_TAG_DATE_PICKER);
                    return true;

                }
                return false;
            }
        });
        dateDep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()

                            .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                                @Override
                                public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                    Toast.makeText(getActivity(), "Date: " + year + " " + monthOfYear + 1 + " " + dayOfMonth, Toast.LENGTH_LONG).show();
                                    dateDep.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
                                }
                            }).setFirstDayOfWeek(Calendar.MONDAY);
                    cdp.show(getChildFragmentManager(), FRAG_TAG_DATE_PICKER2);
                    return true;
                }

                return false;
            }
        });


        chambres = (MaterialBetterSpinner) view.
                findViewById(R.id.chambre);

        adultes = (MaterialBetterSpinner) view.
                findViewById(R.id.adulte);

        enfants = (MaterialBetterSpinner) view.
                findViewById(R.id.enfant);

        typeChb = (MaterialBetterSpinner) view.
                findViewById(R.id.typechb);

        arrang = (MaterialBetterSpinner) view.
                findViewById(R.id.arrang);


        enfants.setFloatingLabelText("N° Enfants ");
        adultes.setFloatingLabelText("N° Adultes ");
        chambres.setFloatingLabelText("Tarif");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enfants.setError("Ok");
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     /*   if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private String[] GetListChambres() {
        return new String[]{"1 Chambre", "2 Chambres", "3 Chambres", "4 Chambres"};
    }

    private String[] GetListAdultes() {
        return new String[]{"1 Adulte", "2 Adultes", "3 Adultes", "4 Adultes", "5 Adultes"};
    }

    private String[] GetListEnfants() {
        return new String[]{"0 Enfant", "1 Enfant", "2 Enfants", "3 Enfants", "4 Enfants"};
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isConnected())
            new HttpRequestTask().execute();
        else {
            ShowDialogMaterialConnection();
        }
    }

    public void ShowDialogMaterialConnection() {
        MaterialDialog.Builder msgConnecting = new MaterialDialog.Builder(getActivity());

        msgConnecting.content(getResources().getString(R.string.laoding_error))

                .theme(Theme.LIGHT)
                .positiveText(getResources().getString(R.string.ressayer));
        MaterialDialog dialog = msgConnecting.build();
        dialog.show();

    }

    public void ShowDialogMaterial(boolean isOk) {
        msgConnecting = new MaterialDialog.Builder(getActivity());
        if (isOk) {
            msgConnecting.content(getResources().getString(R.string.laoding))
                    .progress(true, 0)
                    .cancelable(false)

                    .theme(Theme.LIGHT)
                    .progressIndeterminateStyle(false)
                    .autoDismiss(false);
            dialog = msgConnecting.build();
        } else {
            msgConnecting.content(getResources().getString(R.string.laoding_error))

                    .theme(Theme.LIGHT)
                    .positiveText(getResources().getString(R.string.ressayer));
            dialog = msgConnecting.build();
            dialog.show();
        }
    }

    public String getURL() {
        return UserInfoModel.getInstance().getURL() + "GetDataReservation";
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, DataReservationModel> {
        DataReservationModel isConnected = null;

        @Override
        protected DataReservationModel doInBackground(Void... params) {
            try {
                final String url = getURL();

                Log.i("HttpRequestTask", url.toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, DataReservationModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("ReservationFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(DataReservationModel greeting1) {
            if (isConnected.isStatus()) {
                dialog.dismiss();
                if (isConnected.getData().getContrat() == null) {
                    Log.i("HttpTask", "No Data");
                } else {

                    // Put data into hashtable
                    List<Arrangement> arr = isConnected.getData().getArrangements();
                    for (Arrangement arg :
                            arr) {
                        listArrangement.put(arg.getName(), arg.getId());
                    }
                    List<Tarif> tar = isConnected.getData().getTarifs();
                    for (Tarif arg :
                            tar) {
                        listTarif.put(arg.getName(), arg.getId());
                    }
                    List<TypeProduit> tprod = isConnected.getData().getTypeProduits();
                    for (TypeProduit arg :
                            tprod) {
                        listTypeProduit.put(arg.getName(), arg.getId());
                    }

                    // set Tables Data
                    String[] arrTarif = Arrays.copyOf(listTarif.keySet().toArray(), listTarif.keySet().toArray().length
                            , String[].class);

                    String[] arrArrange = Arrays.copyOf(listArrangement.keySet().toArray(), listArrangement.keySet().toArray().length
                            , String[].class);

                    String[] arrTypeProduit = Arrays.copyOf(listTypeProduit.keySet().toArray(), listTypeProduit.keySet().toArray().length
                            , String[].class);


                    // initiate Adapter
                    ArrayAdapter<String> arrayAdapterArrang = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, arrArrange);
                    ArrayAdapter<String> arrayAdapterTARIF = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, arrTarif);
                    ArrayAdapter<String> arrayAdapterTypeProd = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, arrTypeProduit);

                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, GetListAdultes());
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, GetListEnfants());

                    // set Adapter
                    arrang.setAdapter(arrayAdapterArrang);
                    chambres.setAdapter(arrayAdapterTARIF);
                    typeChb.setAdapter(arrayAdapterTypeProd);

                    adultes.setAdapter(arrayAdapter1);
                    enfants.setAdapter(arrayAdapter2);


                    Log.i("HttpTask", isConnected.getData().toString());
                }
            } else {
                dialog.dismiss();
                ShowDialogMaterial(false);
            }
            ShowDialogMaterial(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

    }

}
