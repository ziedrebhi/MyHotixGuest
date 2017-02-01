package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.ItemReclamationModel;
import com.hotix.myhotixguest.entities.ReclamationModel;
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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;

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
        ((ReclamationsViewAdapter) mAdapter).setOnItemClickListener(new ReclamationsViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
        new HttpRequestTask().execute();
    }

    private ArrayList<ItemReclamationModel> getDataSet(ReclamationModel model) {

        ArrayList<ItemReclamationModel> data = new ArrayList<>(model.getData().size());
        data.addAll(model.getData());
        return data;

    }

    public String getURL() {
        return "http://41.228.14.111/HNGAPI/api/myhotixguest/GetReclamations";
    }

    public void ShowDialogMaterial(boolean isOk) {
        msgConnecting = new MaterialDialog.Builder(getActivity());
        if (isOk) {
            msgConnecting.content(getResources().getString(R.string.laoding))
                    .progress(true, 0)
                    .cancelable(true)
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .progressIndeterminateStyle(false)
                    .autoDismiss(false);
            dialog = msgConnecting.build();
        } else {
            msgConnecting.content(getResources().getString(R.string.laoding_error))
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
                }
            } else {
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
