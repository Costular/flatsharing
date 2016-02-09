package com.costular.flatsharing.group_detail.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costular.flatsharing.R;
import com.costular.flatsharing.data.Group;
import com.costular.flatsharing.data.User;
import com.costular.flatsharing.group_detail.tasks.UserTasksAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diego on 8/02/16.
 */
public class GroupSettingsFragment extends Fragment implements GroupSettingsContract.MyView{

    @Bind(R.id.recycler_view_members) RecyclerView membersRecyclerView;

    private GroupSettingsPresenter presenter;
    private Group group;
    private UserTasksAdapter adapter;

    public static GroupSettingsFragment newInstance(Group group) {
        GroupSettingsFragment fragment = new GroupSettingsFragment();
        Bundle extras = new Bundle();
        extras.putParcelable(GroupSettingsActivity.PARAM_GROUP, group);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_settings, parent, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        group = getArguments().getParcelable(GroupSettingsActivity.PARAM_GROUP);
        presenter = new GroupSettingsPresenter(this, group);
        adapter = new UserTasksAdapter(new UserTasksAdapter.TaskUserClicked() {
            @Override
            public void onTaskUserClicked(User user) {
                //something
            }
        });
        membersRecyclerView.setAdapter(adapter);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        membersRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadMembers(false);
    }

    @Override
    public void setLoading(boolean isLoading) {

    }

    @Override
    public void showMembers(List<User> members) {
        adapter.replaceAndUpdateData(members);
    }

    @Override
    public void closeSettings() {

    }

    @Override
    public void showSuccessMessage(String message) {

    }

    @Override
    public void showSuccessMessageWithUndoButton(String message, String undoText) {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
