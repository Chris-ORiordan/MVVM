package com.example.standard.mvvm.service.repository;

import com.example.standard.mvvm.service.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface GithubService {
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Call<Project> getProjectDetails(@Path("user") String user, @Path("reponame") String projectName);

}
