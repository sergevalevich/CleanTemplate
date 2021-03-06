package com.valevich.umora.presentation.ui.fragments;

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
import android.widget.ProgressBar;

import com.valevich.umora.R;
import com.valevich.umora.UmoraApplication;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.errors.IErrorMessageFactory;
import com.valevich.umora.presentation.presenters.impl.CategoriesPresenter;
import com.valevich.umora.presentation.ui.activities.MainActivity;
import com.valevich.umora.presentation.ui.activities.StoriesByCategoryActivity;
import com.valevich.umora.presentation.ui.adapters.CategoriesAdapter;
import com.valevich.umora.presentation.ui.utils.AttributesHelper;
import com.valevich.umora.presentation.ui.utils.DividerItemDecoration;
import com.valevich.umora.presentation.ui.utils.ItemClickListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;
import timber.log.Timber;

import static com.valevich.umora.presentation.ui.fragments.StoriesByCategoryFragment.CATEGORY_KEY;

@RequiresPresenter(CategoriesPresenter.class)
public class CategoriesFragment extends BaseFragment<CategoriesPresenter>
        implements ItemClickListener<Category> {

    @BindView(R.id.root)
    CoordinatorLayout rootView;

    @BindView(R.id.list)
    RecyclerView categoriesList;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    CategoriesAdapter adapter;

    @Inject
    IErrorMessageFactory errorMessageFactory;

    @Override
    public void onCreate(Bundle bundle) {
        ((MainActivity) getActivity()).getActivityComponent().inject(this);
        super.onCreate(bundle);
        if (bundle == null) {
            getCachedCategories();
            refreshCategories();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_updated, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesList.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        adapter.setClickListener(this);
        categoriesList.setAdapter(adapter);
        swipe.setColorSchemeColors(AttributesHelper.getColorAttribute(getActivity(),R.attr.colorPrimary));
        swipe.setOnRefreshListener(this::refreshCategories);
        progressBar.getIndeterminateDrawable().setColorFilter(
                AttributesHelper.getColorAttribute(getActivity(),R.attr.colorPrimary),
                android.graphics.PorterDuff.Mode.SRC_IN);
        toggleProgressBar(true);
        toggleSwipe(true);
    }

    @Override
    public void onItemClicked(Category category) {
        Intent intent = new Intent(getActivity(), StoriesByCategoryActivity.class);
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
    void injectPresenter(CategoriesPresenter presenter) {
        ((UmoraApplication) getContext().getApplicationContext())
                .getAppComponent()
                .inject(presenter);
    }

    public void onCategories(List<Category> categories) {
        Timber.d("Got %d categories",categories.size());
        toggleProgressBar(false);
        adapter.refresh(categories);
    }

    public void onSourcesUpToDate() {
        toggleSwipe(false);
    }

    public void onError(Throwable t) {
        toggleProgressBar(false);
        toggleSwipe(false);
        String message = errorMessageFactory.createErrorMessage(t);
        showMessage(message);
        Timber.e("Error getting categories %s",message);
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

    private void toggleSwipe(boolean isVisible) {
        swipe.setRefreshing(isVisible);
    }

    private void toggleProgressBar(boolean isVisible) {
        if (isVisible) {
            categoriesList.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            categoriesList.setVisibility(View.VISIBLE);
        }
    }
}
