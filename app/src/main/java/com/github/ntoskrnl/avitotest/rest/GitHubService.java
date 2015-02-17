package com.github.ntoskrnl.avitotest.rest;

import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by Anton Danshin on 17/02/15.
 */

public interface GitHubService {

    @GET("/users")
    retrofit.client.Response listUsers(@Query("since") long since);

}
