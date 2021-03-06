package com.valevich.umora.injection.components;

import com.valevich.umora.domain.repository.RepositoryModule;
import com.valevich.umora.injection.modules.ActivityModule;
import com.valevich.umora.injection.modules.ApplicationModule;
import com.valevich.umora.network.NetworkModule;
import com.valevich.umora.presentation.presenters.impl.BookMarksPresenter;
import com.valevich.umora.presentation.presenters.impl.CategoriesPresenter;
import com.valevich.umora.presentation.presenters.impl.SearchablePresenter;
import com.valevich.umora.presentation.presenters.impl.StoriesByCategoryPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class})
public interface ApplicationComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(CategoriesPresenter presenter);
    void inject(BookMarksPresenter presenter);
    void inject(SearchablePresenter presenter);
    void inject(StoriesByCategoryPresenter presenter);

}