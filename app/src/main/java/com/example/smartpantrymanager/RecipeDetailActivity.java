package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    TextView txtRecipeName;
    TextView txtRecipeIngredients;
    TextView txtRecipePreparation;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipePreparation = findViewById(R.id.txtRecipePreparation);

        databaseHelper = new DatabaseHelper(this);

        int recipeId = getIntent().getIntExtra(
                "recipe_id",
                -1
        );

        if (recipeId == -1) {

            Toast.makeText(
                    this,
                    "Recipe could not be found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        loadRecipe(recipeId);
    }

    private void loadRecipe(int recipeId) {

        Cursor cursor = databaseHelper.getAllRecipes();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            if (id == recipeId) {

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String ingredients = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredients")
                );

                String preparation = cursor.getString(
                        cursor.getColumnIndexOrThrow("preparation")
                );

                txtRecipeName.setText(name);

                txtRecipeIngredients.setText(
                        formatIngredients(ingredients)
                );

                txtRecipePreparation.setText(preparation);

                break;
            }
        }

        cursor.close();
    }

    private String formatIngredients(String ingredients) {

        String[] ingredientList =
                ingredients.split(",");

        StringBuilder formatted =
                new StringBuilder();

        for (String ingredient : ingredientList) {

            String[] parts =
                    ingredient.trim().split(":");

            if (parts.length == 3) {

                String name = parts[0].trim();
                String quantity = parts[1].trim();
                String unit = parts[2].trim();

                formatted.append("• ")
                        .append(quantity)
                        .append(" ")
                        .append(unit)
                        .append(" ")
                        .append(name)
                        .append("\n");
            }
        }

        return formatted.toString().trim();
    }
}
