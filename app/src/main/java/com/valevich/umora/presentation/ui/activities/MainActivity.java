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
import com.valevich.umora.presentation.ui.fragments.BookMarksFragment;
import com.valevich.umora.presentation.ui.fragments.CategoriesFragment;
import com.valevich.umora.presentation.ui.fragments.SearchableFragment;
import com.valevich.umora.presentation.ui.fragments.SettingsFragment;

public class MainActivity extends DrawerActivity
        implements FragmentManager.OnBackStackChangedListener {

    public static final int STORIES_BY_CATEGORY_REQUEST_CODE = 100;
    public static final String FRAGMENT_EXTRA = "FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        //after config change or process restart
        //SYSTEM recreates fragment by itself -> so i do nothing
        fragmentManager.addOnBackStackChangedListener(this);
        if (savedInstanceState == null)
            replaceFragment(new CategoriesFragment());
        else setTitle(title);
    }

    @Override
    public void onBackStackChanged() {
        Fragment f = fragmentManager
                .findFragmentById(R.id.main_container);

        if (f != null) {
            changeSelectedItemAndTitle(f.getClass().getName());
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

        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);

        Fragment current = fragmentManager.findFragmentById(R.id.main_container);
        String currentFragmentName = current == null ? null : current.getClass().getName();

        if (!isFragmentPopped && !backStackName.equals(currentFragmentName)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment, backStackName)
                    .addToBackStack(backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

        }
    }

    private void changeSelectedItemAndTitle(String backStackEntryName) {
        int menuItemId = R.id.drawer_categories;
        if (backStackEntryName.equals(CategoriesFragment.class.getName())) {
            title = categoriesTitle;
        } else if (backStackEntryName.equals(BookMarksFragment.class.getName())) {
            title = bookMarksTitle;
            menuItemId = R.id.drawer_bookmarks;
        } else if (backStackEntryName.equals(SettingsFragment.class.getName())) {
            title = settingsTitle;
            menuItemId = R.id.drawer_settings;
        } else if (backStackEntryName.equals(SearchableFragment.class.getName())) {
            title = searchTitle;
            menuItemId = R.id.drawer_search;
        }
        navigationView.setCheckedItem(menuItemId);
        setTitle(title);
    }
}
