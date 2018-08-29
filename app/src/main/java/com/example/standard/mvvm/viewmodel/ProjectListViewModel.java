package com.example.standard.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.*;
import android.support.annotation.NonNull;

import com.example.standard.mvvm.service.model.Project;
import com.example.standard.mvvm.service.repository.ProjectRepository;

import java.util.List;

public class ProjectListViewModel extends AndroidViewModel{

    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel( Application application) {
        super(application);

        projectListObservable = ProjectRepository.getInstance().getProjectList("Google");
    }

    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }
}
