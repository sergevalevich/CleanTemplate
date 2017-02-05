package com.valevich.clean.presentation.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryHolder> {

    private List<Story> stories;
    private ItemClickListener<Story> listenter;

    public StoriesAdapter(List<Story> stories,
                          ItemClickListener<Story> listener) {
        this.stories = stories;
        this.listenter = listener;
    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.story_item,
                parent,
                false);
        return new StoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        holder.textView.setText(stories.get(position).text());
    }

    @Override
    public int getItemCount() {
        return stories.size();
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
            itemView.setOnLongClickListener(view ->
                    listenter.onItemLongClicked(stories.get(getAdapterPosition())));
            //// FIXME: 17.01.2017 Orientation, process restart
            // FIXME: 16.01.2017 LEAK???
        }
    }
}
