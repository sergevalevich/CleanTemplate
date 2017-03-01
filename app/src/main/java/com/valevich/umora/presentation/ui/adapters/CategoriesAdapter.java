package com.valevich.umora.presentation.ui.adapters;


import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valevich.umora.R;
import com.valevich.umora.domain.model.Category;
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
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private List<Category> categories = new ArrayList<>();
    private ItemClickListener<Category> listener;

    private SharedPreferences preferences;

    @Inject
    CategoriesAdapter(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setClickListener(ItemClickListener<Category> listener) {
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

        @BindString(R.string.pref_font_key)
        String fontKey;

        @BindString(R.string.pref_font_default)
        String defaultFont;

        @BindDimen(R.dimen.primary_text_size)
        float textSize;

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
            float multiplier = Float.parseFloat(preferences.getString(fontKey, defaultFont));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * multiplier);
        }
    }
}
