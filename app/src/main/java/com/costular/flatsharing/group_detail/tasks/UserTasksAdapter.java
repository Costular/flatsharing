package com.costular.flatsharing.group_detail.tasks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.costular.flatsharing.R;
import com.costular.flatsharing.data.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by diego on 19/12/15.
 */
public class UserTasksAdapter extends RecyclerView.Adapter<UserTasksAdapter.UserTasksViewHolder>{

    private List<User> userList;
    private Context context;
    private TaskUserClicked listener;

    public UserTasksAdapter(TaskUserClicked listener) {
        this.userList = new ArrayList<>();
        this.listener = listener;
    }

    public UserTasksAdapter(List<User> userList, TaskUserClicked listener) {
        this(listener);
        userList.addAll(userList);
    }

    public void replaceData(List<User> userList) {
        this.userList = userList;
    }

    public void replaceAndUpdateData(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    private User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public UserTasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasks_user_list, parent, false);
        return new UserTasksViewHolder(root, listener);
    }

    @Override
    public void onBindViewHolder(UserTasksViewHolder holder, int position) {
        User user = getItem(position);

        if(user != null) {
            Picasso.with(context)
                    .load(user.getAvatarURL())
                    .placeholder(R.drawable.ic_person)
                    .fit()
                    .centerCrop()
                    .into(holder.avatarUserIcon);
            holder.userName.setText(user.getName());
        }
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserTasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.user_avatar_icon) CircleImageView avatarUserIcon;
        @Bind(R.id.user_name) TextView userName;

        private TaskUserClicked listener;


        public UserTasksViewHolder(View itemView, TaskUserClicked listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(listener != null) {
                listener.onTaskUserClicked(getItem(position));
            }
        }
    }

    public interface TaskUserClicked {
        void onTaskUserClicked(User user);
    }
}
