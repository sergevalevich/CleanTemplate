package com.valevich.clean.presentation.ui.adapters;


import android.content.Context;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.ui.utils.AttributesHelper;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;
import com.valevich.clean.presentation.ui.utils.StateListDrawableHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryHolder> {

    private List<Story> stories = new ArrayList<>();
    private ItemClickListener<Story> itemClickListener;

    public StoriesAdapter(ItemClickListener<Story> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public StoriesAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.story_item,
                parent,
                false);
        return new StoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoriesAdapter.StoryHolder holder, int position) {
        holder.textView.setText(stories.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void refresh(List<Story> stories) {
        this.stories = Collections.unmodifiableList(new ArrayList<>(stories));
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

        private void setColorStateDrawable() {
            int pressedColor = AttributesHelper.getColorAttribute(itemView.getContext(), R.attr.colorPressed);
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
}
