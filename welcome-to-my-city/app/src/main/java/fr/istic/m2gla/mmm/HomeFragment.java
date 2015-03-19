package fr.istic.m2gla.mmm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.istic.m2gla.mmm.client.Common;

public class HomeFragment extends Fragment {


    public HomeFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        TextView text = (TextView) rootView.findViewById(R.id.textView3);
        text.setText(Common.getDisplayName());
        return rootView;
    }







}
