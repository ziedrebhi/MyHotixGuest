package com.hotix.myhotixguest.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.Theme;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.ItemRestaurantModel;
import com.hotix.myhotixguest.entities.RestaurantModel;
import com.hotix.myhotixguest.entities.UserInfoModel;
import com.hotix.myhotixguest.updater.RestaurantsViewAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantsFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String FRAG_TAG_DATE_PICKER2 = "fragment_date_picker_name2";
    private static String LOG_TAG = "RestaurantsFragment";
    MaterialDialog.Builder msgConnecting;
    MaterialDialog dialog;
    TextView msgEmpty;
    Boolean wrapInScrollView = true;
    MaterialDialog dialog2;
    EditText date, heure, comment, pour;
    MaterialBetterSpinner pax;
    private SliderLayout mDemoSlider;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private View positiveAction;
    private String txtDate, txtHeure, txtPour, txtDetails, txtPax;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantsFragment newInstance(String param1, String param2) {
        RestaurantsFragment fragment = new RestaurantsFragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        msgEmpty = (TextView) view.findViewById(R.id.emptyMsg);
        msgEmpty.setVisibility(View.GONE);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Restaurant Le Pacha", R.mipmap.res_1);
        file_maps.put("Restaurant Le Sérail", R.mipmap.res_2);
        file_maps.put("Restaurant Le Pirate", R.mipmap.res_3);
        file_maps.put("Le Sultan", R.mipmap.res_4);
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
        // Inflate the layout for this fragment
        ShowDialogMaterial(true);
        mRecyclerView.setHasFixedSize(true);
        //  mLayoutManager = new LinearLayoutManager(getActivity());

        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        } else {

            mLayoutManager = new GridLayoutManager(getActivity(), 4);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RestaurantsViewAdapter(null, getActivity());
        //  mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onResume() {
        super.onResume();

        ((RestaurantsViewAdapter) mAdapter).setOnItemClickListener(new RestaurantsViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + ((RestaurantsViewAdapter) mAdapter).getItem(position).getNom());


                MaterialDialog.Builder dialogM = new MaterialDialog.Builder(getActivity())
                        .title(R.string.resa_rest)
                        .iconRes(R.mipmap.ic_room_service_black_24dp)
                        .limitIconToDefaultSize()
                        .customView(R.layout.restaurant_custom_view, wrapInScrollView)

                        .btnStackedGravity(GravityEnum.END)
                        .stackingBehavior(StackingBehavior.ALWAYS)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.submit)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO


                            }
                        })

                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        });
                dialog2 = dialogM.build();

                pour = (EditText) dialog2.getCustomView().findViewById(R.id.pour);
                comment = (EditText) dialog2.getCustomView().findViewById(R.id.comment);
                date = (EditText) dialog2.getCustomView().findViewById(R.id.date);
                heure = (EditText) dialog2.getCustomView().findViewById(R.id.hour);
                pour.setText(UserInfoModel.getInstance().getUsers().getData().get(0).getName().toString());
                comment.setText("( Guest chambre : " + UserInfoModel.getInstance().getRoom().toString() + " )");
                positiveAction = dialog2.getActionButton(DialogAction.POSITIVE);

                date.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()

                                    .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                            Toast.makeText(getActivity(), "Date: " + year + " " + monthOfYear + 1 + " " + dayOfMonth, Toast.LENGTH_LONG).show();
                                            txtDate = String.format("%02d", year) + String.format("%02d", monthOfYear + 1) + String.format("%02d", dayOfMonth);
                                            date.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + String.valueOf(year));
                                        }
                                    }).setFirstDayOfWeek(Calendar.MONDAY).setCancelText(getResources().getString(R.string.cancel));

                            cdp.show(getChildFragmentManager(), FRAG_TAG_DATE_PICKER);
                            return true;

                        }
                        return false;
                    }
                });

                heure.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                                    .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                                            Toast.makeText(getActivity(), "Hour: " + hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
                                            heure.setText(String.valueOf(String.format("%02d", hourOfDay)) + ":" + String.format("%02d", minute));
                                            txtHeure = txtDate + String.format("%02d", hourOfDay) + String.format("%02d", minute);

                                        }
                                    })
                                    .setStartTime(10, 30).setCancelText(getResources().getString(R.string.cancel));


                            rtpd.show(getChildFragmentManager(), FRAG_TAG_DATE_PICKER2);
                            return true;

                        }
                        return false;
                    }
                });
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, GetListEnfants());

                pax = (MaterialBetterSpinner) dialog2.getCustomView().
                        findViewById(R.id.pax);
                pax.setAdapter(arrayAdapter);
                pax.setSelection(0);
                dialog2.show();
            }
        });

        if (isConnected())
            new HttpRequestTask().execute();
        else {
            ShowDialogMaterialConnection();
        }
    }

    private ArrayList<ItemRestaurantModel> getDataSet(RestaurantModel model) {

        ArrayList<ItemRestaurantModel> data = new ArrayList<>(model.getData().size());
        data.addAll(model.getData());
        return data;

    }

    public String getURL() {
        return UserInfoModel.getInstance().getURL() + "GetRestaurants";
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
                    .positiveText("Ok");
            dialog = msgConnecting.build();
            dialog.show();
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

    private String[] GetListEnfants() {
        int nbrMax = UserInfoModel.getInstance().getUsers().getData().size();
        String[] paxs = new String[nbrMax];
        paxs[0] = "1 personne";
        for (int i = 1; i < nbrMax; i++) {
            paxs[i] = String.valueOf(i + 1) + " personnes";
        }
        return paxs;
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

    private class HttpRequestTask extends AsyncTask<Void, Void, RestaurantModel> {
        RestaurantModel isConnected = null;

        @Override
        protected RestaurantModel doInBackground(Void... params) {
            try {
                final String url = getURL();
                Log.i("HttpRequestTask", url.toString());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, RestaurantModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("ActiviteFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RestaurantModel greeting1) {
            if (isConnected.isStatus() && (isConnected.getData().size() != 0)) {
                dialog.dismiss();
                if (isConnected.getData().size() != 0) {
                    Log.i("HttpRequestTask", String.valueOf(isConnected.getData().size()));
                    mAdapter = new RestaurantsViewAdapter(getDataSet(isConnected), getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    msgEmpty.setVisibility(View.VISIBLE);
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
