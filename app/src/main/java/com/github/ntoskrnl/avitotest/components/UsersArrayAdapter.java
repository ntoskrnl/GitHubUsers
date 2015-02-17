package com.github.ntoskrnl.avitotest.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.github.ntoskrnl.avitotest.R;
import com.github.ntoskrnl.avitotest.rest.json.User;

import java.util.List;

public class UsersArrayAdapter extends ArrayAdapter<User> {

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