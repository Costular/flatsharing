package com.costular.flatsharing.groups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.costular.flatsharing.R;
import com.costular.flatsharing.add_group.AddGroupActivity;
import com.costular.flatsharing.data.FakeApiService;
import com.costular.flatsharing.data.Group;
import com.costular.flatsharing.data.GroupDataCached;
import com.costular.flatsharing.data.Repository;
import com.costular.flatsharing.group_detail.GroupDetailActivity;
import com.costular.flatsharing.util.AutofitRecyclerView;
import com.costular.flatsharing.util.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diego on 7/12/15.
 */
public class GroupsFragment extends Fragment implements GroupsContract.MyView {

    public static final int REQUEST_ADD_GROUP = 1;

    private GroupsPresenter groupsPresenter;
    private FloatingActionButton fabAddGroup;
    private GroupsAdapter adapter;

    @Bind(R.id.loading) ProgressBar loadingProgressBar;
    @Bind(R.id.recycler_view) AutofitRecyclerView recyclerView;
    @Bind(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_groups, container, false);
        ButterKnife.bind(this, root);
        fabAddGroup = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_group);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        groupsPresenter = new GroupsPresenter(this, Repository.getInMemoryRepoInstance(new FakeApiService(9)));

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                groupsPresenter.loadGroups(true);
            }
        });

        fabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupsPresenter.addNewGroup();
            }
        });

        int numColumns = getContext().getResources().getInteger(R.integer.num_groups_colmumns);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        recyclerView.addItemDecoration(SpacesItemDecoration.newInstance(4));
        adapter = new GroupsAdapter(new ArrayList<Group>(0), new GroupsAdapter.GroupActionListener() {
            @Override
            public void groupClicked(Group group) {
                groupsPresenter.openGroupDetail(group);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(groupsPresenter != null) {
            groupsPresenter.loadGroups(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a note was successfully added, show snackbar
        if (REQUEST_ADD_GROUP == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), getString(R.string.group_added_successfully),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {
        loadingProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        refreshLayout.setRefreshing(active);
    }

    @Override
    public void showAddGroup() {
        Intent intent = new Intent(getActivity(), AddGroupActivity.class);
        startActivityForResult(intent, REQUEST_ADD_GROUP);
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showGroupDetail(Group group) {
        Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(GroupDetailActivity.GROUP, group);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void showGroups(List<Group> groupList) {
        adapter.replaceListData(groupList);
    }

}
