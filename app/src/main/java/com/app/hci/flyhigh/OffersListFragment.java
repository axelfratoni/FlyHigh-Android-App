package com.app.hci.flyhigh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class OffersListFragment extends ListFragment {

    View rootView;

    List<Offer> offers;

    public OffersListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }

        rootView = inflater.inflate(R.layout.offerslistfragment_layout, container, false);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        offers = ((OffersFragment)OffersListFragment.this.getParentFragment()).getOffers();

        OfferAdapter adapter = new OfferAdapter(getActivity(), offers);
        setListAdapter(adapter);
    }

}

