package com.example.standard.mvvm.view.ui;

import android.arch.lifecycle.*;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.standard.mvvm.R;
import com.example.standard.mvvm.service.model.Project;
import com.example.standard.mvvm.view.adapter.ProjectAdapter;
import com.example.standard.mvvm.databinding.FragmentProjectListBinding;
import com.example.standard.mvvm.view.callback.ProjectClickCallback;
import com.example.standard.mvvm.viewmodel.ProjectListViewModel;

import java.util.List;

public class ProjectListFragment extends LifecycleFragment {
    public static final String TAG = "ProjectListFragment";
    private ProjectAdapter projectAdapter;
    private FragmentProjectListBinding fragmentProjectListBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProjectListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);

        projectAdapter = new ProjectAdapter();
        fragmentProjectListBinding.projectList.setAdapter(projectAdapter);
        fragmentProjectListBinding.setIsLoading(true);
        return fragmentProjectListBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProjectListViewModel projectListViewModel = ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(projectListViewModel);
    }

    private void observeViewModel(ProjectListViewModel viewModel){
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if(projects != null){
                    fragmentProjectListBinding.setIsLoading(false);
                    projectAdapter.setProjectList(projects);
                }
            }
        });
    }

    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        public void onClick(Project project){
            if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
                ((MainActivity) getActivity()).show(project);
            }
        }
    };
}
