package com.valevich.umora.injection.components;

import com.valevich.umora.injection.PerActivity;
import com.valevich.umora.injection.modules.ActivityModule;
import com.valevich.umora.presentation.ui.activities.MainActivity;
import com.valevich.umora.presentation.ui.activities.StoriesByCategoryActivity;
import com.valevich.umora.presentation.ui.fragments.BookMarksFragment;
import com.valevich.umora.presentation.ui.fragments.CategoriesFragment;
import com.valevich.umora.presentation.ui.fragments.SearchableFragment;
import com.valevich.umora.presentation.ui.fragments.SettingsFragment;
import com.valevich.umora.presentation.ui.fragments.StoriesByCategoryFragment;

import dagger.Subcomponent;

//knows AppComponent dependencies
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(StoriesByCategoryActivity activity);

    void inject(CategoriesFragment fragment);
    void inject(BookMarksFragment fragment);
    void inject(SearchableFragment fragment);
    void inject(SettingsFragment fragment);
    void inject(StoriesByCategoryFragment fragment);


}