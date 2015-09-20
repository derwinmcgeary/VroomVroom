package com.mcgearybros.vroomvroom;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 *
 * Activities that contain this fragment must implement the
 * {@link WebViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FRAGMENT_SUB_ITEM = "fragmentSubItem";

    // sub item which defines content of fragment
    private NavigationSubItem fragmentSubItem;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentSubItem chosen menu item for which content is to be displayed
     * @return A new instance of fragment WebViewFragment.
     */
    public static WebViewFragment newInstance(NavigationSubItem fragmentSubItem) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FRAGMENT_SUB_ITEM, fragmentSubItem);
        fragment.setArguments(args);
        return fragment;
    }

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentSubItem = getArguments().getParcelable(ARG_FRAGMENT_SUB_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);
        final HighwayCodeWebView htmlDisplay = (HighwayCodeWebView) rootView.findViewById(R.id.html_display);
        htmlDisplay.loadUrl("file:///android_asset/" + fragmentSubItem.getContentId() + ".html");
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            //when fragment is displayed, call methods in Activity to update title and track current
            //position with contentManager
            mListener.setAppbarTitle(fragmentSubItem.getSectionTitle());
            mListener.updatePositionInContentManager(fragmentSubItem.getContentId());
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * interface allowing fragment to communicate with Activity
     */
    public interface OnFragmentInteractionListener {
        void setAppbarTitle(String title);
        void updatePositionInContentManager(String contentId);
    }

}
