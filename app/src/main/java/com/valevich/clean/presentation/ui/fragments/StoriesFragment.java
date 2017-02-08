package com.valevich.clean.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.errors.ErrorMessageFactory;
import com.valevich.clean.presentation.presenters.impl.StoriesPresenter;
import com.valevich.clean.presentation.ui.adapters.StoriesAdapter;
import com.valevich.clean.presentation.ui.utils.DividerItemDecoration;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import icepick.State;
import timber.log.Timber;

public abstract class StoriesFragment<P extends StoriesPresenter> extends BaseFragment<P>
        implements ItemClickListener<Story>, ActionMode.Callback {

    @BindView(R.id.root)
    CoordinatorLayout rootView;

    @BindView(R.id.stories_list)
    RecyclerView storiesList;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindString(R.string.booked)
    String bookedMessage;

    @BindString(R.string.unbooked)
    String unBookedMessage;

    @State
    Story selectedStory;

    @State
    boolean wasStoryBookMarked;

    private ActionMode actionMode;

    abstract void subscribeToUpdates();
    abstract void showLoading();
    abstract void hideLoading();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Timber.d("on create. bundle is %s", bundle);
        if (bundle == null) {
            subscribeToUpdates();
        } else {
            if (selectedStory != null) {
                startActionMode();
            }
        }
        //after rotation and process restart bundle should not be null
        //after rotation we don't need to restart
        //after process restart query will restart automatically
        // TODO: 15.01.2017 test what happens after rotation / process restart
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storiesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        storiesList.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        showLoading();
    }

    @Override
    public void onItemClicked(Story story) {
    }

    @Override
    public boolean onItemLongClicked(Story story) {
        if (actionMode == null) {
            selectedStory = story;
            wasStoryBookMarked = selectedStory.isBookMarked();
            startActionMode();
        }
        return true;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.stories_contextual_action_bar, menu);
        MenuItem menuItem = menu.getItem(1);
        menuItem.setIcon(selectedStory.isBookMarked()
                ? R.drawable.ic_menu_unbook
                : R.drawable.ic_menu_book);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bookmark:
                bookMark(item);
                return true;
            case R.id.menu_share:
                share();
                actionMode.finish();
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (selectedStory.isBookMarked() != wasStoryBookMarked) {
            showLoading();
            getPresenter().updateStory(selectedStory);
        }
        actionMode = null;
        selectedStory = null;
    }

    public void onStories(List<Story> stories) {
        Timber.d("Got %d stories", stories.size());
        StoriesAdapter adapter = (StoriesAdapter) storiesList.getAdapter();
        if (adapter == null) storiesList.setAdapter(new StoriesAdapter(stories, this));
        else adapter.refresh(new ArrayList<>(stories));
    }

    public void onStoriesUpToDate() {
        Timber.d("onStoriesUpToDate");
        hideLoading();
    }

    public void onStoryUpdated(boolean isBookMarked) {
        hideLoading();
        showMessage(isBookMarked ? bookedMessage : unBookedMessage);
    }

    public void onError(Throwable t) {
        hideLoading();
        String message = ErrorMessageFactory.createErrorMessage(getActivity(), t);
        showMessage(message);
        Timber.e("Error getting stories %s", t.toString());
    }

    private void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    private void startActionMode() {
        actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(this);
    }

    private void bookMark(MenuItem item) {
        boolean isBooked = selectedStory.isBookMarked();
        item.setIcon(isBooked
                ? R.drawable.ic_menu_book
                : R.drawable.ic_menu_unbook);
        selectedStory.setBookMarked(!isBooked);
    }

    private void share() {
        String sharedText = selectedStory.getText();
        String appSign = getActivity().getString(R.string.app_sign);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(
                Intent.EXTRA_TEXT,
                String.format(Locale.getDefault(), "%s%n%n%s", sharedText, appSign));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        getActivity().startActivity(Intent.createChooser(
                intent,
                getActivity().getString(R.string.share_action)));
    }
}
