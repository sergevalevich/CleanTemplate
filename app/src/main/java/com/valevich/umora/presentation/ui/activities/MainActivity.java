package com.valevich.umora.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.valevich.umora.R;
import com.valevich.umora.presentation.presenters.impl.MainPresenter;
import com.valevich.umora.presentation.ui.fragments.BookMarksFragment;
import com.valevich.umora.presentation.ui.fragments.CategoriesFragment;
import com.valevich.umora.presentation.ui.fragments.SearchableFragment;
import com.valevich.umora.presentation.ui.fragments.SettingsFragment;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends DrawerActivity<MainPresenter>
        implements FragmentManager.OnBackStackChangedListener {

    public static final int STORIES_BY_CATEGORY_REQUEST_CODE = 100;
    public static final String FRAGMENT_EXTRA = "FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //after config change or process restart
        //the SYSTEM will recreate fragment
        //so i don't want to recreate an instance
        if (savedInstanceState == null)
            replaceFragment(new CategoriesFragment());
        else setTitle(title);
    }

    @Override
    public void onBackStackChanged() {
        Fragment f = getSupportFragmentManager()
                .findFragmentById(R.id.main_container);

        if (f != null) {
            changeSelectedItemAndTitle(f.getClass().getName());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        replaceFragment(getFragmentByMenuItemId(item.getItemId()));
        return true;
    }

    private Fragment getFragmentByMenuItemId(int id) {
        switch (id) {
            case R.id.drawer_categories:
                return new CategoriesFragment();
            case R.id.drawer_bookmarks:
                return new BookMarksFragment();
            case R.id.drawer_search:
                return new SearchableFragment();
            case R.id.drawer_settings:
                return new SettingsFragment();
        }
        throw new RuntimeException("No such menu item");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STORIES_BY_CATEGORY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    replaceFragment(
                            getFragmentByMenuItemId(data.getIntExtra(FRAGMENT_EXTRA, R.id.drawer_categories))
                    );
                }
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        boolean isFragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStackName, 0);

        if (!isFragmentPopped && getSupportFragmentManager().findFragmentByTag(backStackName) == null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, fragment, backStackName);
            transaction.addToBackStack(backStackName);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

        }
    }

    private void changeSelectedItemAndTitle(String backStackEntryName) {
        if (backStackEntryName.equals(CategoriesFragment.class.getName())) {
            setTitle(categoriesTitle);
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (backStackEntryName.equals(BookMarksFragment.class.getName())) {
            setTitle(bookMarksTitle);
            navigationView.setCheckedItem(R.id.drawer_bookmarks);
        } else if (backStackEntryName.equals(SettingsFragment.class.getName())) {
            setTitle(settingsTitle);
            navigationView.setCheckedItem(R.id.drawer_settings);
        } else if (backStackEntryName.equals(SearchableFragment.class.getName())) {
            setTitle(searchTitle);
            navigationView.setCheckedItem(R.id.drawer_search);
        }
        title = getTitle().toString();
    }
}
