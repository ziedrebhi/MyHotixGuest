package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.ActiviteModel;
import com.hotix.myhotixguest.entities.ItemActiviteModel;
import com.hotix.myhotixguest.entities.UserInfoModel;
import com.hotix.myhotixguest.updater.ActivitesViewAdapter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActiviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActiviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiviteFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialDialog.Builder msgConnecting;
    MaterialDialog dialog;
    TextView msgEmpty;
    RelativeLayout lay;
    private SliderLayout mDemoSlider;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;

    public ActiviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiviteFragment newInstance(String param1, String param2) {
        ActiviteFragment fragment = new ActiviteFragment();
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

        View view = inflater.inflate(R.layout.fragment_activites, container, false);
        msgEmpty = (TextView) view.findViewById(R.id.emptyMsg);
        lay = (RelativeLayout) view.findViewById(R.id.lay);
        msgEmpty.setVisibility(View.GONE);

        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Activité 1", R.mipmap.act_1);
        file_maps.put("Activité 2", R.mipmap.act_2);
        file_maps.put("Activité 3", R.mipmap.act_3);
        file_maps.put("Activité 4", R.mipmap.act_4);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_activite);

        ShowDialogMaterial(true);
        mRecyclerView.setHasFixedSize(true);
        //  mLayoutManager = new LinearLayoutManager(getActivity());

        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            mLayoutManager = new LinearLayoutManager(getActivity());
        } else {

            mLayoutManager = new LinearLayoutManager(getActivity());
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ActivitesViewAdapter(null, getActivity());
        //mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActivitesViewAdapter) mAdapter).setOnItemClickListener(new ActivitesViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("RestaurantsFragments", " Clicked on Item " + position);
            }
        });
        if (isConnected())
            new HttpRequestTask().execute();
        else {
            ShowDialogMaterialConnection();
        }
    }

    private ArrayList<ItemActiviteModel> getDataSet(ActiviteModel model) {

        ArrayList<ItemActiviteModel> data = new ArrayList<>(model.getData().size());
        data.addAll(model.getData());
        return data;

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
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public String getURL() {
        return UserInfoModel.getInstance().getURL() + "GetActivites";
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

    private class HttpRequestTask extends AsyncTask<Void, Void, ActiviteModel> {
        ActiviteModel isConnected = null;

        @Override
        protected ActiviteModel doInBackground(Void... params) {
            try {
                final String url = getURL();
                Log.i("HttpRequestTask", url.toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, ActiviteModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("ActiviteFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ActiviteModel greeting1) {
            if (isConnected.isStatus() && (isConnected.getData().size() != 0)) {
                dialog.dismiss();
                if (isConnected.getData().size() != 0) {
                    Log.i("HttpRequestTask", String.valueOf(isConnected.getData().size()));
                    mAdapter = new ActivitesViewAdapter(getDataSet(isConnected), getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    msgEmpty.setVisibility(View.VISIBLE);
                    lay.setVisibility(View.GONE);

                }
            } else {
                dialog.dismiss();
                //ShowDialogMaterial(false);
                msgEmpty.setVisibility(View.VISIBLE);
                lay.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

    }
}
