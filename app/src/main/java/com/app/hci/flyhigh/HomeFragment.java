package com.app.hci.flyhigh;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.hci.flyhigh.R;

/**
 * Created by Gaston on 15/06/2017.
 */

public class HomeFragment extends ListFragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.home_layout, container, false);
        if (new DataManager().getSubsCount(getContext()) == 0) {
            ((TextView) view.findViewById(R.id.info)).setText(R.string.main_info);
        } else if (new DataManager().getUpdatesCount(getContext()) == 0) {
            ((TextView) view.findViewById(R.id.info)).setText(R.string.main_info2);
        } else {
            System.out.println(new DataManager().getUpdatesCount(getContext()));
            Flight[] values = new DataManager().retrieveUpdates(getContext());
            NewsAdapter adapter = new NewsAdapter(getActivity(), values);
            setListAdapter(adapter);
        }
        return view;

    }
}
