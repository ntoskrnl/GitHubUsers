package com.github.ntoskrnl.avitotest.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.github.ntoskrnl.avitotest.R;
import com.github.ntoskrnl.avitotest.rest.json.User;

import java.util.ArrayList;
import java.util.List;

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

    public static class UsersArrayAdapter extends ArrayAdapter<User> {

        private ImageLoader mImageLoader;

        public UsersArrayAdapter(Context context, List<User> objects) {
            super(context, R.layout.user_list_item, objects);
            mImageLoader = new ImageLoader(Volley.newRequestQueue(context), new LruBitmapCache(context));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                // create item view
                LayoutInflater inflater = LayoutInflater.from(getContext());
                itemView = inflater.inflate(R.layout.user_list_item, parent, false);
            }

            User user = getItem(position);

            TextView loginView = (TextView) itemView.findViewById(R.id.login);
            TextView profileUrlView = (TextView) itemView.findViewById(R.id.profile_url);
            NetworkImageView avatarView = (NetworkImageView) itemView.findViewById(R.id.avatar);


            loginView.setText(user.getLogin());
            profileUrlView.setText(user.getHtmlUrl());
            avatarView.setDefaultImageResId(R.drawable.github_octocat);
            avatarView.setImageUrl(user.getAvatarUrl(), mImageLoader);

            return itemView;
        }
    }

    public static class LruBitmapCache extends LruCache<String, Bitmap>
            implements ImageLoader.ImageCache {

        public LruBitmapCache(int maxSize) {
            super(maxSize);
        }

        public LruBitmapCache(Context ctx) {
            this(getCacheSize(ctx));
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        // Returns a cache size equal to approximately five screens worth of images.
        public static int getCacheSize(Context ctx) {
            final DisplayMetrics displayMetrics = ctx.getResources().
                    getDisplayMetrics();
            final int screenWidth = displayMetrics.widthPixels;
            final int screenHeight = displayMetrics.heightPixels;
            // 4 bytes per pixel
            final int screenBytes = screenWidth * screenHeight * 4;

            return screenBytes * 5;
        }
    }
}
