package com.github.ntoskrnl.avitotest.rest;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Anton Danshin on 17/02/15.
 */
public class GitHub {

    private static volatile GitHub instance = null;

    private GitHubService service;

    private GitHub() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com");

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addHeader("Authorization","Bearer ee5e0ba0b760515b786f96013def2f6bf8dfab9f");
            }
        });

        service = builder.build().create(GitHubService.class);
    }

    public static GitHub getInstance() {
        if (instance == null) {
            synchronized (GitHub.class) {
                if (instance == null) {
                    instance = new GitHub();
                }
            }
        }
        return instance;
    }

    public GitHubService getService() {
        return service;
    }
}
