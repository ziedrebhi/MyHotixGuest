package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.ItemReclamationModel;
import com.hotix.myhotixguest.entities.ReclamationModel;
import com.hotix.myhotixguest.entities.ResponseModel;
import com.hotix.myhotixguest.entities.UserInfoModel;
import com.hotix.myhotixguest.updater.ReclamationsViewAdapter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReclamationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReclamationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReclamationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String LOG_TAG = "ReclamationFragment";
    MaterialDialog.Builder msgConnecting;
    MaterialDialog dialog;
    TextView msgEmpty;
    boolean wrapInScrollView;
    EditText object, value;
    MaterialDialog dialog2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fab;
    private View positiveAction;
    private String ObjetReclamation;
    private String DetailsReclamation;
    public ReclamationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReclamationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReclamationFragment newInstance(String param1, String param2) {
        ReclamationFragment fragment = new ReclamationFragment();
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

        View view = inflater.inflate(R.layout.fragment_reclamation, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_viewReclam);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ShowDialogMaterial(true);
        mAdapter = new ReclamationsViewAdapter(null, getActivity());
        //  mRecyclerView.setAdapter(mAdapter);
        msgEmpty = (TextView) view.findViewById(R.id.emptyMsg);
        msgEmpty.setVisibility(View.GONE);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        wrapInScrollView = true;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                final View view2 = view;

                MaterialDialog.Builder dialogM = new MaterialDialog.Builder(getActivity())
                        .title(R.string.reclamation)
                        .iconRes(R.mipmap.ic_announcement_black_24dp)
                        .limitIconToDefaultSize()
                        .customView(R.layout.reclamation_custom_view, wrapInScrollView)
                        .typeface("Roboto.ttf", "Roboto.ttf")
                        .btnStackedGravity(GravityEnum.END)
                        .stackingBehavior(StackingBehavior.ALWAYS)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.submit)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                                if (isEmpty()) {
                                    if (isConnected()) {
                                        ObjetReclamation = object.getText().toString();
                                        DetailsReclamation = value.getText().toString();
                                        new HttpRequestTaskSend().execute();
                                    } else {
                                        ShowDialogMaterialConnection();
                                    }
                                }


                            }
                        })

                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        });
                dialog2 = dialogM.build();
                object = (EditText) dialog2.getCustomView().findViewById(R.id.obj);
                value = (EditText) dialog2.getCustomView().findViewById(R.id.rec);
                positiveAction = dialog2.getActionButton(DialogAction.POSITIVE);

                object.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (s.toString().trim().length() == 0) {
                            object.setError(getResources().getString(R.string.obl));
                        }
                        if (isEmpty())
                            positiveAction.setEnabled(true);
                        else {
                            positiveAction.setEnabled(false);
                        }

                    }
                });
                value.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().trim().length() == 0) {
                            value.setError(getResources().getString(R.string.obl));
                        }
                        if (isEmpty())
                            positiveAction.setEnabled(true);
                        else {
                            positiveAction.setEnabled(false);
                        }
                    }
                });
                positiveAction.setEnabled(false);
                dialog2.show();

            }
        });
        return view;
    }

    public boolean isEmpty() {

        if ((object.getText().toString().isEmpty()) && (value.getText().toString().isEmpty())) {
            object.setError("Obligatoire");
            value.setError("Obligatoire");
            Log.i("HotixDev", "Both Empty");
            return false;
        } else if (object.getText().toString().isEmpty()) {
            object.setError("Obligatoire");
            Log.i("HotixDev", "password Empty");
            return false;
        } else if (value.getText().toString().isEmpty()) {
            Log.i("HotixDev", "login Empty");
            value.setError("Obligatoire");
            return false;
        }
        return true;
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
        ((ReclamationsViewAdapter) mAdapter).setOnItemClickListener(new ReclamationsViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
        if (isConnected())
            new HttpRequestTask().execute();
        else {
            ShowDialogMaterialConnection();
        }
    }

    private ArrayList<ItemReclamationModel> getDataSet(ReclamationModel model) {

        ArrayList<ItemReclamationModel> data = new ArrayList<>(model.getData().size());
        data.addAll(model.getData());
        return data;

    }

    public String getURL() {
        return UserInfoModel.getInstance().getURL() + "GetReclamations";
    }

    public String getURLSend() {
        return UserInfoModel.getInstance().getURL() + "SendReclamation";
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

    public void ShowDialogMaterialConnection() {
        MaterialDialog.Builder msgConnecting = new MaterialDialog.Builder(getActivity());

        msgConnecting.content(getResources().getString(R.string.laoding_error))
                .typeface("Roboto-Light.ttf", "Roboto.ttf")
                .theme(Theme.LIGHT)
                .positiveText(getResources().getString(R.string.ressayer));
        MaterialDialog dialog = msgConnecting.build();
        dialog.show();

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

    private class HttpRequestTask extends AsyncTask<Void, Void, ReclamationModel> {
        ReclamationModel isConnected = null;

        @Override
        protected ReclamationModel doInBackground(Void... params) {
            try {
                final String url = getURL() + "?chambre=" + UserInfoModel.getInstance().getRoom();
                ;
                Log.i("HttpRequestTask", url.toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, ReclamationModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("ReclamationFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ReclamationModel greeting1) {
            if (isConnected.isStatus()) {
                dialog.dismiss();
                Log.i("HttpRequestTask", String.valueOf(isConnected.getData().size()));
                if (isConnected.getData().size() == 0) {
                    msgEmpty.setVisibility(View.VISIBLE);
                } else {
                    mAdapter = new ReclamationsViewAdapter(getDataSet(isConnected), getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                    msgEmpty.setVisibility(View.GONE);
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

    private class HttpRequestTaskSend extends AsyncTask<Void, Void, ResponseModel> {
        ResponseModel isConnected = null;

        @Override
        protected ResponseModel doInBackground(Void... params) {
            try {
                final String url = getURLSend() + "?hotelId=1&numChambre=" + UserInfoModel.getInstance().getRoom() + "&objectRec=" + ObjetReclamation
                        + "&detail=" + DetailsReclamation;
                ;
                Log.i("HttpRequestTask", url.toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, ResponseModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("ReclamationFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseModel greeting1) {
            if (isConnected.isStatus()) {
                dialog.dismiss();

                ShowDialogMaterial(true);
                if (isConnected())
                    new HttpRequestTask().execute();
                else {
                    ShowDialogMaterialConnection();
                }
            } else {
                dialog.dismiss();
                ShowDialogMaterial(false);
            }
            // ShowDialogMaterial(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

    }

}
