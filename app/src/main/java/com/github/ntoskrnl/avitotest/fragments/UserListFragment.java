package com.github.ntoskrnl.avitotest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.ntoskrnl.avitotest.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anton Danshin on 17/02/15.
 */
public class UserListFragment extends Fragment {

    @InjectView(R.id.list_view)
    protected ListView mListView;

    private ArrayAdapter<String> mArrayAdapter;
    private ArrayList<String> mUserList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and inject views
        final View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mUserList);
        mListView.setAdapter(new UsersEndlessAdapter(mArrayAdapter));
    }
}
