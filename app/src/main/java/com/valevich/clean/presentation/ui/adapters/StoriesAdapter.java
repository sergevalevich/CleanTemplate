package com.valevich.clean.presentation.ui.adapters;


import android.content.Context;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.ui.utils.AttributesHelper;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;
import com.valevich.clean.presentation.ui.utils.PageRequestListener;
import com.valevich.clean.presentation.ui.utils.StateListDrawableHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int STORY_ITEM_TYPE = 0;
    private static final int BTN_ITEM_TYPE = 1;

    private List<Story> stories;
    private ItemClickListener<Story> itemClickListener;
    private PageRequestListener pageRequestListener;

    public StoriesAdapter(List<Story> stories,
                          ItemClickListener<Story> itemClickListener,
                          PageRequestListener pageRequestListener) {
        this.stories = stories;
        this.itemClickListener = itemClickListener;
        this.pageRequestListener = pageRequestListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == stories.size() ? BTN_ITEM_TYPE : STORY_ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case STORY_ITEM_TYPE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.story_item,
                        parent,
                        false);
                return new StoryHolder(itemView);

            case BTN_ITEM_TYPE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.paging_layout,
                        parent,
                        false);
                return new ButtonHolder(itemView);
        }
        throw new RuntimeException("No such viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case STORY_ITEM_TYPE:
                ((StoryHolder) holder).bindContent(stories.get(position));
                break;
            case BTN_ITEM_TYPE:
                //((ButtonHolder) holder).bindContent(_bus);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return stories.size() + 1;
    }

    public void refresh(List<Story> stories) {
        this.stories.clear();
        this.stories.addAll(stories);
        notifyDataSetChanged();
    }

    class StoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.story_text)
        TextView textView;

        StoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setColorStateDrawable();
            setOnLongClickListener();
            setTextSize();
        }

        void bindContent(Story story) {
            textView.setText(story.getText());
        }

        private void setColorStateDrawable() {
            int pressedColor = AttributesHelper.getColorAttribute(itemView.getContext(), R.attr.colorMenu);
            int normalColor = AttributesHelper.getColorAttribute(itemView.getContext(), R.attr.colorBack);
            itemView.setBackground(StateListDrawableHelper.getDrawable(pressedColor, normalColor));
        }

        private void setOnLongClickListener() {
            itemView.setOnLongClickListener(view ->
                    itemClickListener.onItemLongClicked(stories.get(getAdapterPosition())));
        }

        private void setTextSize() {
            Context context = textView.getContext();
            float multiplier = Float.parseFloat(PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .getString(context.getString(R.string.pref_font_key), context.getResources().getString(R.string.pref_font_default)));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.primary_text_size) * multiplier);
        }
    }

    class ButtonHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loadMoreButton)
        Button loadMoreButton;

        ButtonHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnClickListener();
        }

        private void setOnClickListener() {
            itemView.setOnClickListener(view -> pageRequestListener.onPageRequested());
        }

    }
}
