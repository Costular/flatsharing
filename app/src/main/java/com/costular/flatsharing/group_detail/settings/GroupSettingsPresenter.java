package com.costular.flatsharing.group_detail.settings;

import com.costular.flatsharing.data.Group;

/**
 * Created by diego on 8/02/16.
 */
public class GroupSettingsPresenter implements GroupSettingsContract.UserActionListener {

    private GroupSettingsContract.MyView view;
    private Group group;

    public GroupSettingsPresenter(GroupSettingsContract.MyView view, Group group) {
        this.view = view;
        this.group = group;
    }

    @Override
    public void loadMembers(boolean forceToUpdate) {
        view.showMembers(group.getMembers());
    }

    @Override
    public void addMember() {

    }

    @Override
    public void changeAvatarButtonClicked() {

    }

    @Override
    public void changeGroupName(String name) {

    }

    @Override
    public void deleteGroup() {

    }
}
