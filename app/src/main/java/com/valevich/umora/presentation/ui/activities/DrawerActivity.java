package com.valevich.umora.presentation.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.valevich.umora.R;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import icepick.State;


public abstract class DrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindString(R.string.nav_drawer_categories)
    String categoriesTitle;

    @BindString(R.string.nav_drawer_bookmarks)
    String bookMarksTitle;

    @BindString(R.string.nav_drawer_settings)
    String settingsTitle;

    @BindString(R.string.nav_drawer_search)
    String searchTitle;

    @State
    String title;

    @Inject
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    int getLayoutRes() {
        return R.layout.activity_drawer;
    }

    private void setupViews() {
        setupActionBar();
        setupDrawerLayout();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        setupNavigation();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout
                , toolbar
                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    private void setupNavigation() {
        navigationView.setNavigationItemSelectedListener(this);
    }
}
