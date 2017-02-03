package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
import com.hotix.myhotixguest.entities.ItemFactureModel;
import com.hotix.myhotixguest.entities.UserInfoModel;

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
    TextView numChb;
    String Chambre = "";
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
        numChb = (TextView) view.findViewById(R.id.numchbFact);
        numChb.setText(UserInfoModel.getInstance().getRoom());
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
        Chambre = UserInfoModel.getInstance().getRoom();

        tl.removeAllViewsInLayout();
        if (isConnected())
            new HttpRequestTask().execute();
        else {
            ShowDialogMaterialConnection();
        }
    }

    public void ShowDialogMaterialConnection() {
        MaterialDialog.Builder msgConnecting = new MaterialDialog.Builder(getActivity());

        msgConnecting.content(getResources().getString(R.string.laoding_error))
                .typeface("Roboto-Light.ttf", "Roboto.ttf")
                .theme(Theme.LIGHT)
                .positiveText(getResources().getString(R.string.ressayer));
        MaterialDialog dialog = msgConnecting.build();
        dialog.show();

    }

    public String getURL() {
        return UserInfoModel.getInstance().getURL() + "getfacture";
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
            msgConnecting.content(getResources().getString(R.string.laoding))
                    .progress(true, 0)
                    .cancelable(false)
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .progressIndeterminateStyle(false)
                    .autoDismiss(false);
            dialog = msgConnecting.build();
        } else {
            msgConnecting.content(getResources().getString(R.string.laoding_error))
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .positiveText(getResources().getString(R.string.ressayer));
            dialog = msgConnecting.build();
            dialog.show();
        }
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

    private class HttpRequestTask extends AsyncTask<Void, Void, FactureModel> {
        FactureModel isConnected = null;
        String chambre = "93";

        @Override
        protected FactureModel doInBackground(Void... params) {
            try {
                final String url = getURL() + "?chambre=" + UserInfoModel.getInstance().getRoom();
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
        protected void onPostExecute(FactureModel greeting1) {
            if (isConnected.isStatus() && (isConnected.getData().size() != 0)) {
                dialog.dismiss();


                TableRow tr = null;
                float tot = 0;
                LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

                Log.i("HttpRequestTask", String.valueOf(isConnected.getData().size()));
                for (int i = 0; i < isConnected.getData().size(); i++) {
                    List<ItemFactureModel> list = isConnected.getData();

                    tr = new TableRow(getActivity());
                    tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                    TextView t = generateTextView(list.get(i).getDateFacturation(), layoutParams);
                    t.setBackgroundResource(android.R.color.white);
                    t.setTextSize(12);
                    t.setBackgroundResource(R.drawable.fact_shape);
                    tr.addView(t);


                    TextView a = generateTextView(list.get(i).getDesignation(), layoutParams);
                    a.setTextSize(12);
                    a.setBackgroundResource(R.drawable.fact_shape);
                    tr.addView(a);


                    TextView b1 = generateTextView(list.get(i).getComment(), layoutParams);
                    b1.setBackgroundResource(R.drawable.fact_shape);
                    //b1.setElegantTextHeight(true);
                    b1.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    b1.setSingleLine(false);
                    b1.setTextSize(14);
                    tr.addView(b1);


                    TextView a1 = generateTextView(String.format("%.3f", list.get(i).getMontant()), layoutParams);
                    a1.setBackgroundColor(Color.parseColor("#582c7e"));
                    a1.setTextColor(Color.WHITE);
                    a1.setTextSize(14);
                    tr.addView(a1);


                    tot = tot + Float.parseFloat(String.valueOf(list.get(i).getMontant()));


                    tl.addView(tr, layoutParams);

                }
                totale.setText(String.format("%.3f", tot) + " TND");


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
