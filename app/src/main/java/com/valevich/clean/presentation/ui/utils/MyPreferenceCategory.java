package com.valevich.clean.presentation.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.valevich.clean.R;


public class MyPreferenceCategory extends PreferenceCategory {

    private Context context;

    public MyPreferenceCategory(Context context) {
        super(context);
        this.context = context;
    }

    public MyPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyPreferenceCategory(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        TextView titleView = (TextView) holder.findViewById(android.R.id.title);

        titleView.setTextColor(ContextCompat.getColor(
                context,
                AttributesHelper.getColorAttribute(context,R.attr.colorAccent)));

        View root = holder.itemView;
        int padding_in_px = DpToPixConverter.dpToPix(15, context.getResources().getDisplayMetrics().density);
        root.setPadding(padding_in_px, padding_in_px, padding_in_px, 0);

    }
}
