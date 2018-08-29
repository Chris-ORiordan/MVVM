package com.example.standard.mvvm.service.repository;

import android.arch.lifecycle.*;

import com.example.standard.mvvm.service.model.Project;

import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {
    private GithubService githubService;
    private static ProjectRepository projectRepository;

    private ProjectRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        githubService = retrofit.create(GithubService.class);
    }

    public synchronized static ProjectRepository getInstance(){
        if(projectRepository == null){
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }

    public LiveData<List<Project>> getProjectList(String userId){
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

        githubService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                simulateDelay();
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    private void simulateDelay() {
        try{
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
