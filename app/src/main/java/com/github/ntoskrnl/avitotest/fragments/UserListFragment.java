package com.github.ntoskrnl.avitotest.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.ntoskrnl.avitotest.R;
import com.github.ntoskrnl.avitotest.components.UsersArrayAdapter;
import com.github.ntoskrnl.avitotest.components.UsersEndlessAdapter;
import com.github.ntoskrnl.avitotest.rest.json.User;

import java.util.ArrayList;

/**
 * Created by Anton Danshin on 17/02/15.
 */
public class UserListFragment extends Fragment {

    private static final String TAG = UserListFragment.class.getSimpleName();

    private ListView mListView;

    private ArrayAdapter<User> mArrayAdapter;
    private UsersEndlessAdapter mEndlessAdapter;
    private ArrayList<User> mUsers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and inject views
        final View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mArrayAdapter = new UsersArrayAdapter(getActivity(), mUsers);
        mEndlessAdapter = new UsersEndlessAdapter(mArrayAdapter);
        mListView.setAdapter(mEndlessAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = mArrayAdapter.getItem(position);
                if (!TextUtils.isEmpty(user.getAvatarUrl())) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(user.getHtmlUrl()));
                        startActivity(intent);
                    } catch (Exception ex) {
                        Log.w(TAG, "failed to initiate action view", ex);
                    }
                }
            }
        });
    }
}
