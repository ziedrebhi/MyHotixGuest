package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.FactureModel;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FactureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FactureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FactureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialDialog.Builder msgConnecting;
    MaterialDialog dialog;
    TableLayout tl;
    TextView totale;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public FactureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FactureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FactureFragment newInstance(String param1, String param2) {
        FactureFragment fragment = new FactureFragment();
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
        View view = inflater.inflate(R.layout.fragment_facture, container, false);
        tl = (TableLayout) view.findViewById(R.id.table1);
        totale = (TextView) view.findViewById(R.id.totalFacture);
        ShowDialogMaterial(true);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        new HttpRequestTask().execute();
    }

    public String getURL() {
        return "http://41.228.14.111/HNGAPI/api/myhotixguest/getfacture";
    }

    public TextView generateTextView(String texte, LayoutParams ly) {
        TextView result = new TextView(getActivity());
        result.setBackgroundColor(Color.LTGRAY);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(22);
        result.setLayoutParams(ly);
        return result;
    }

    public void ShowDialogMaterial(boolean isOk) {
        msgConnecting = new MaterialDialog.Builder(getActivity());
        if (isOk) {
            msgConnecting.content("Loading. Please wait...")
                    .progress(true, 0)
                    .cancelable(true)
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .progressIndeterminateStyle(false)
                    .autoDismiss(false);
            dialog = msgConnecting.build();
        } else {
            msgConnecting.content("Erreur de Connexion")
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .positiveText("Ok");
            dialog = msgConnecting.build();
            dialog.show();
        }
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

    private class HttpRequestTask extends AsyncTask<Void, Void, FactureModel> {
        FactureModel isConnected = null;
        String chambre = "93";

        @Override
        protected FactureModel doInBackground(Void... params) {
            try {
                final String url = getURL() + "?chambre=" + chambre;
                Log.i("HttpRequestTask", url.toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, FactureModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("FactureFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(FactureModel greeting) {
            if (isConnected.getStatus()) {
                dialog.dismiss();


                TableRow tr = null;
                float tot = 0;
                LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

                Log.i("HttpRequestTask", String.valueOf(greeting.getData().size()));
                for (int i = 0; i < greeting.getData().size(); i++) {
                    List<FactureModel.FactureItemModel> list = greeting.getData();

                    tr = new TableRow(getActivity());
                    tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                    TextView t = generateTextView(list.get(i).getDateFacturation(), layoutParams);
                    t.setBackgroundResource(android.R.color.white);
                    t.setTextSize(10);
                    tr.addView(t);
                    t.setBackgroundResource(R.drawable.fact_shape);

                    TextView a = generateTextView(list.get(i).getDesignation(), layoutParams);
                    a.setTextSize(10);
                    tr.addView(a);
                    a.setBackgroundResource(R.drawable.fact_shape);

                    TextView b1 = generateTextView(list.get(i).getComment(), layoutParams);
                    b1.setBackgroundResource(R.drawable.fact_shape);
                    tr.addView(b1);
                    b1.setTextSize(12);

                    TextView a1 = generateTextView(String.valueOf(list.get(i).getMontant()), layoutParams);
                    a1.setBackgroundColor(Color.parseColor("#582c7e"));
                    tr.addView(a1);
                    a1.setTextSize(10);

                    tot = tot + Float.parseFloat(String.valueOf(list.get(i).getMontant()).replaceAll(",", ""));


                    tl.addView(tr, layoutParams);

                }
                tot = tot / 1000;

                totale.setText(Float.toString(tot) + " DT");


            } else

            {
                dialog.dismiss();
                ShowDialogMaterial(false);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

    }
}
