package com.srinug.testapp.api.service;

import com.srinug.testapp.api.model.GitCommitsData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface GitClient {

    @GET("repos/{user}/{repo}/commits")
    Call<List<GitCommitsData>> getRepoCommits(@Path("user") String user, @Path("repo") String repo);

}
