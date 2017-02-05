package com.valevich.clean.presentation.ui.fragments;

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
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.errors.ErrorMessageFactory;
import com.valevich.clean.events.StoryChangedEvent;
import com.valevich.clean.presentation.presenters.impl.StoriesPresenter;
import com.valevich.clean.presentation.ui.adapters.StoriesAdapter;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

public abstract class StoriesFragment<P extends StoriesPresenter> extends BaseFragment<P>
        implements ItemClickListener<Story> {

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

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Timber.d("on create. bundle is %s", bundle);
        if (bundle == null) {
            getStories();
        }
        //after rotation and process restart bundle should not be null
        //after rotation we don't need to restart
        //after process restart query will restart automatically
        // TODO: 15.01.2017 test what happens after rotation / process restart
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        swipe.setColorSchemeResources(R.color.colorAccent);
        showLoading(true);
    }

    @Override
    public void onItemClicked(Story story) {
    }

    @Override
    public boolean onItemLongClicked(Story story) {
        getPresenter().onStorySelected(story);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStoryChanged(StoryChangedEvent event) {
        showLoading(true);
        getPresenter().updateStory(event.getStory());
    }

    public void onStories(List<Story> stories) {
        Timber.d("Got %d stories", stories.size());
        StoriesAdapter adapter = (StoriesAdapter) storiesList.getAdapter();
        if (adapter == null) storiesList.setAdapter(new StoriesAdapter(stories, this));
        else adapter.refresh(new ArrayList<>(stories));
        showLoading(false);
    }

    public void onStoryUpdated(boolean isBookMarked) {
        showMessage(isBookMarked ? bookedMessage : unBookedMessage);
    }

    public void onError(Throwable t) {
        showLoading(false);
        String message = ErrorMessageFactory.createErrorMessage(getActivity(), t);
        showMessage(message);
        Timber.e("Error getting stories %s", t.toString());
    }

    private void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    abstract void getStories();

    private void showLoading(boolean isLoading) {
        swipe.setRefreshing(isLoading);
    }
}
