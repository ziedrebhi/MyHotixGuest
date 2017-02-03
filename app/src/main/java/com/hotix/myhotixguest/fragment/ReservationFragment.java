package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.hotix.myhotixguest.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

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

        String[] SPINNERLIST = {"1 element", "2 elements", "3 elements", "4 elements"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, GetListChambres());

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, GetListAdultes());

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, GetListEnfants());

        chambres = (MaterialBetterSpinner) view.
                findViewById(R.id.chambre);
        chambres.setAdapter(arrayAdapter);
        adultes = (MaterialBetterSpinner) view.
                findViewById(R.id.adulte);
        adultes.setAdapter(arrayAdapter1);
        enfants = (MaterialBetterSpinner) view.
                findViewById(R.id.enfant);
        enfants.setAdapter(arrayAdapter2);


        typeChb = (MaterialBetterSpinner) view.
                findViewById(R.id.typechb);
        typeChb.setAdapter(arrayAdapter);
        arrang = (MaterialBetterSpinner) view.
                findViewById(R.id.arrang);
        arrang.setAdapter(arrayAdapter);


        enfants.setFloatingLabelText("N° Enfants ");
        adultes.setFloatingLabelText("N° Adultes ");
        chambres.setFloatingLabelText("N° Chambres ");


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
}
