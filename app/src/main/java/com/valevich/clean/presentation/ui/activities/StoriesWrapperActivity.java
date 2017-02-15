package com.valevich.clean.presentation.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.StoriesWrapperPresenter;
import com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment;

import nucleus.factory.RequiresPresenter;

import static com.valevich.clean.presentation.ui.activities.MainActivity.FRAGMENT_EXTRA;
import static com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment.CATEGORY_KEY;
import static com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment.OFFSET_KEY;
import static com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment.STORIES_COUNT_KEY;

@RequiresPresenter(StoriesWrapperPresenter.class)
public class StoriesWrapperActivity extends DrawerActivity<StoriesWrapperPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Category category = getIntent().getParcelableExtra(CATEGORY_KEY);
            title = category.getDescription();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, getFragment())
                    .commit();
        }
        setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        navigate(item.getItemId());
        return true;
    }

    private void navigate(int menuItemId) {
        Intent intent = new Intent();
        intent.putExtra(FRAGMENT_EXTRA, menuItemId);
        setResult(RESULT_OK, intent);
        finish();
    }

    private Fragment getFragment() {
        Intent intent = getIntent();
        return StoriesByCategoryFragment.newInstance(
                intent.getParcelableExtra(CATEGORY_KEY),
                intent.getIntExtra(STORIES_COUNT_KEY, Story.DEFAULT_COUNT),
                intent.getIntExtra(OFFSET_KEY, Story.DEFAULT_OFFSET));
    }

}
