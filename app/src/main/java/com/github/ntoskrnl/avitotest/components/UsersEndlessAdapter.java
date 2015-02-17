package com.github.ntoskrnl.avitotest.components;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.github.ntoskrnl.avitotest.rest.GitHub;
import com.github.ntoskrnl.avitotest.rest.GitHubService;
import com.github.ntoskrnl.avitotest.rest.json.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Danshin on 17/02/15.
 */
public class UsersEndlessAdapter extends EndlessAdapter {

    private static final String TAG = UsersEndlessAdapter.class.getSimpleName();

    private final List<User> cachedData = new ArrayList<>();
    private final GitHubService service;

    private volatile long since = 0;

    public UsersEndlessAdapter(ArrayAdapter<User> wrapped) {
        super(wrapped);
        service = GitHub.getInstance().getService();
        setSerialized(true);
    }


    @Override
    protected boolean cacheInBackground() throws Exception {
        List<User> users = service.listUsers(since);
        if (users != null) {
            for (User user : users) {
                cachedData.add(user);
                if (user.getId() > since) {
                    since = user.getId();
                }
            }
        }
        return !cachedData.isEmpty();
    }

    public void refresh() {
        restartAppending();
        @SuppressWarnings("unchecked")
        ArrayAdapter<User> adapter = (ArrayAdapter<User>) getWrappedAdapter();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected View getPendingView(ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        ((TextView) row.findViewById(android.R.id.text1)).setText("Loading content...");
        row.setClickable(false);
        return row;
    }

    @Override
    protected boolean onException(View pendingView, Exception e) {
        ((TextView) pendingView.findViewById(android.R.id.text1)).setText("Loading error.");
        Log.w(TAG, "onException(): loading error", e);
        return super.onException(pendingView, e);
    }

    @Override
    protected void appendCachedData() {
        @SuppressWarnings("unchecked")
        ArrayAdapter<User> wrapped = (ArrayAdapter<User>) getWrappedAdapter();
        for (User item: cachedData) {
            wrapped.add(item);
        }
        cachedData.clear();
    }
}
