package com.valevich.clean.presentation.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Category;
import com.valevich.clean.presentation.ui.utils.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private List<Category> categories;
    private ItemClickListener<Category> listener;

    public CategoriesAdapter(List<Category> categories, ItemClickListener<Category> listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.category_item,
                parent,
                false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.textView.setText(categories.get(position).description());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void refresh(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_name)
        TextView textView;

        CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view ->
                    listener.onItemClicked(categories.get(getAdapterPosition())));
            // FIXME: 16.01.2017 LEAK???
        }
    }
}
