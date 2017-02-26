package com.valevich.umora.injection.components;

import com.valevich.umora.database.DbModule;
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
@Component(modules = {ApplicationModule.class, NetworkModule.class, DbModule.class})
public interface ApplicationComponent {

//    @ApplicationContext
//    Context context();
//
//    Application application();

    ActivityComponent plus(ActivityModule activityModule);

    void inject(CategoriesPresenter presenter);
    void inject(BookMarksPresenter presenter);
    void inject(SearchablePresenter presenter);
    void inject(StoriesByCategoryPresenter presenter);

}