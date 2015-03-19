package fr.istic.m2gla.mmm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.istic.m2gla.mmm.map_activities.MainActivityMap;

public class CommunityFragment extends Fragment {
	
	public CommunityFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        Intent intent = new Intent(this.getActivity(), MainActivityMap.class);
        getActivity().startActivity(intent);
         
        return rootView;
    }
}
