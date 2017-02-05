package com.valevich.clean.actionmode;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.events.StoryChangedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import timber.log.Timber;

public class StoriesActionModeHelper implements IActionModeHelper<Story>, ActionMode.Callback {

    private Context context;
    private ActionMode actionMode;
    private Story selectedStory;
    private boolean isBooked;

    public StoriesActionModeHelper(Context context) {
        this.context = context;
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
        Timber.d("onDestroyActionMode");
        if (selectedStory.isBookMarked() != isBooked)
            EventBus.getDefault().post(new StoryChangedEvent(getNewStory()));
        actionMode = null;
    }

    @Override
    public void start(Story story) {
        selectedStory = story;
        isBooked = selectedStory.isBookMarked();
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) context).startSupportActionMode(this);
        }
    }

    private void bookMark(MenuItem item) {
        item.setIcon(isBooked
                ? R.drawable.ic_menu_book
                : R.drawable.ic_menu_unbook);
        isBooked = !isBooked;
    }

    private void share() {
        String sharedText = selectedStory.text();
        String appSign = context.getString(R.string.app_sign);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(
                Intent.EXTRA_TEXT,
                String.format(Locale.getDefault(), "%s%n%n%s", sharedText, appSign));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(Intent.createChooser(
                intent,
                context.getString(R.string.share_action)));
    }

    // FIXME: 05.02.2017
    private Story getNewStory() {
        return Story.create(selectedStory.text(),
                selectedStory.site(),
                selectedStory.categoryName(),
                isBooked);
    }

}
