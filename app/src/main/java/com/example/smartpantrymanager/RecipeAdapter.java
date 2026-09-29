package com.example.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<String> {

    public RecipeAdapter(Context context, ArrayList<String> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public @NonNull View getView(
            int position,
            View convertView,
            @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    android.R.layout.simple_list_item_1,
                    parent,
                    false
            );
        }

        TextView textView = convertView.findViewById(
                android.R.id.text1
        );

        String recipe = getItem(position);

        textView.setText(recipe);

        return convertView;
    }
}