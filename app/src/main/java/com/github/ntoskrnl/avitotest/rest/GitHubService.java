package com.github.ntoskrnl.avitotest.rest;

import com.github.ntoskrnl.avitotest.rest.json.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by Anton Danshin on 17/02/15.
 */

public interface GitHubService {

    @GET("/users")
    List<User> listUsers(@Query("since") long since);

}
