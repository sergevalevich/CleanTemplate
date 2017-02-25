package com.valevich.clean.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.errors.ErrorMessageFactory;
import com.valevich.clean.presentation.presenters.impl.CategoriesPresenter;
import com.valevich.clean.presentation.ui.activities.MainActivity;
import com.valevich.clean.presentation.ui.activities.StoriesWrapperActivity;
import com.valevich.clean.presentation.ui.adapters.CategoriesAdapter;
import com.valevich.clean.presentation.ui.utils.AttributesHelper;
import com.valevich.clean.presentation.ui.utils.DividerItemDecoration;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;
import timber.log.Timber;

import static com.valevich.clean.presentation.ui.fragments.StoriesByCategoryFragment.CATEGORY_KEY;

@RequiresPresenter(CategoriesPresenter.class)
public class CategoriesFragment extends BaseFragment<CategoriesPresenter>
        implements ItemClickListener<Category> {

    @BindView(R.id.root)
    CoordinatorLayout rootView;

    @BindView(R.id.categories_list)
    RecyclerView categoriesList;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private CategoriesAdapter adapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Timber.d("on create. bundle is %s", bundle);
        if (bundle == null) {
            getCachedCategories();
            refreshCategories();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesList.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        adapter = new CategoriesAdapter(this);//to avoid skipping layout and swipe unavailability when adapter is empty
        categoriesList.setAdapter(adapter);
        swipe.setColorSchemeColors(AttributesHelper.getColorAttribute(getActivity(),R.attr.colorPrimary));
        swipe.setOnRefreshListener(this::refreshCategories);
        showLoading(true);
    }

    @Override
    public void onItemClicked(Category category) {
        Intent intent = new Intent(getActivity(), StoriesWrapperActivity.class);
        intent.putExtra(CATEGORY_KEY, category);
        getActivity().startActivityForResult(intent, MainActivity.STORIES_BY_CATEGORY_REQUEST_CODE);
        /*
        startActivityForResult because:
        it's one task - killing wrapper activity

        if starting MainActivity from scratch, we have:
        kill main -> kill Wrapper -> start main
         */
    }

    @Override
    public boolean onItemLongClicked(Category category) {
        return true;
    }

    @Override
    PresenterFactory<CategoriesPresenter> createPresenterFactory() {
        return () -> new CategoriesPresenter(getActivity().getApplicationContext());
    }

    public void onCategories(List<Category> categories) {
        Timber.d("onCategories %d",categories.size());
        adapter.refresh(categories);
//        CategoriesAdapter adapter = (CategoriesAdapter) categoriesList.getAdapter();
//        if (adapter == null) categoriesList.setAdapter(new CategoriesAdapter(categories, this));
//        else adapter.refresh(new ArrayList<>(categories));
    }

    public void onSourcesUpToDate() {
        showLoading(false);
    }

    public void onError(Throwable t) {
        showLoading(false);
        String message = ErrorMessageFactory.createErrorMessage(getActivity(), t);
        showMessage(message);
        Timber.e(message);
    }

    private void getCachedCategories() {
        getPresenter().getAllCategories();
    }

    private void refreshCategories() {
        getPresenter().refreshCategories();
    }

    private void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    private void showLoading(boolean isLoading) {
        swipe.setRefreshing(isLoading);
    }
}
