package com.valevich.umora.presentation.ui.adapters;


import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valevich.umora.R;
import com.valevich.umora.domain.model.Story;
import com.valevich.umora.injection.PerActivity;
import com.valevich.umora.presentation.ui.utils.AttributesHelper;
import com.valevich.umora.presentation.ui.utils.ItemClickListener;
import com.valevich.umora.presentation.ui.utils.StateListDrawableHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryHolder> {

    private List<Story> stories = new ArrayList<>();
    private ItemClickListener<Story> itemClickListener;
    private SharedPreferences preferences;

    @Inject
    StoriesAdapter(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setClickListener(ItemClickListener<Story> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public StoriesAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item,
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

        @BindView(R.id.text)
        TextView textView;

        @BindString(R.string.pref_font_key)
        String fontKey;

        @BindString(R.string.pref_font_default)
        String defaultFont;

        @BindDimen(R.dimen.primary_text_size)
        float textSize;

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
            float multiplier = Float.parseFloat(preferences.getString(fontKey, defaultFont));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * multiplier);
        }
    }
}
