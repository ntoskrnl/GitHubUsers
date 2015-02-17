package com.github.ntoskrnl.avitotest.rest;

import retrofit.RestAdapter;

/**
 * Created by Anton Danshin on 17/02/15.
 */
public class GitHub {

    private static volatile GitHub instance = null;

    private GitHubService service;

    private GitHub() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        service = restAdapter.create(GitHubService.class);
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
