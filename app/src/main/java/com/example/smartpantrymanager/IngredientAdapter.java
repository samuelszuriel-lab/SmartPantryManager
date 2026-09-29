
package com.example.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<String> {

    public IngredientAdapter(Context context, ArrayList<String> ingredients) {
        super(context, 0, ingredients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_ingredient,
                    parent,
                    false
            );
        }

        TextView txtIngredient = convertView.findViewById(R.id.txtIngredient);

        String ingredient = getItem(position);

        txtIngredient.setText(ingredient);

        return convertView;
    }
}