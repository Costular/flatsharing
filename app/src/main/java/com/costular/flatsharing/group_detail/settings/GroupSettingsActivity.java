package com.costular.flatsharing.group_detail.settings;

import android.os.Bundle;
import android.widget.ImageView;

import com.costular.flatsharing.BaseActivity;
import com.costular.flatsharing.R;
import com.costular.flatsharing.data.Group;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diego on 8/02/16.
 */
public class GroupSettingsActivity extends BaseActivity {

    public static final String PARAM_GROUP = "group";

    @Bind(R.id.header_image) ImageView headerImage;

    private Group group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings);
        ButterKnife.bind(this);
        setUpToolbar(true);
        group = getIntent().getExtras().getParcelable(PARAM_GROUP);

        if (group != null) {
            setToolbarTitle(group.getTitle());
            Picasso.with(getApplicationContext())
                    .load(group.getImageURL())
                    .into(headerImage);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, GroupSettingsFragment.newInstance(group))
                    .commit();
        }
    }
}
