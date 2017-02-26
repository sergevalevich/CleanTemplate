package com.valevich.umora.presentation.ui.adapters;


import android.content.Context;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valevich.umora.R;
import com.valevich.umora.domain.model.Category;
import com.valevich.umora.presentation.ui.utils.AttributesHelper;
import com.valevich.umora.presentation.ui.utils.ItemClickListener;
import com.valevich.umora.presentation.ui.utils.StateListDrawableHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private List<Category> categories = new ArrayList<>();
    private ItemClickListener<Category> listener;

    public CategoriesAdapter(ItemClickListener<Category> listener) {
        this.listener = listener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item,
                parent,
                false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.textView.setText(categories.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void refresh(List<Category> categories) {
        this.categories = Collections.unmodifiableList(new ArrayList<>(categories));
        notifyDataSetChanged();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView textView;

        CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setColorStateDrawable();
            setOnClickListener();
            setTextSize();
        }

        private void setColorStateDrawable() {
            int pressedColor = AttributesHelper.getColorAttribute(itemView.getContext(), R.attr.colorPressed);
            int normalColor = AttributesHelper.getColorAttribute(itemView.getContext(), R.attr.colorBack);
            itemView.setBackground(StateListDrawableHelper.getDrawable(pressedColor, normalColor));
        }

        private void setOnClickListener() {
            itemView.setOnClickListener(view ->
                    listener.onItemClicked(categories.get(getAdapterPosition())));
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
