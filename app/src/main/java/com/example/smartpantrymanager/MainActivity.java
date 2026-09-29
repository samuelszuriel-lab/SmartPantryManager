package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    private ListView listIngredients;
    private TextView txtEmptyPantry;

    private DatabaseHelper databaseHelper;
    private IngredientAdapter ingredientAdapter;

    private ArrayList<String> ingredients;
    private ArrayList<Integer> ingredientIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        listIngredients = findViewById(R.id.listIngredients);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);

        databaseHelper = new DatabaseHelper(this);

        ingredients = new ArrayList<>();
        ingredientIds = new ArrayList<>();

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Opening Settings...",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        listIngredients.setOnItemClickListener(
                (parent, view, position, id) -> {

                    int ingredientId =
                            ingredientIds.get(position);

                    showIngredientOptions(ingredientId);
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {

        ingredients.clear();
        ingredientIds.clear();

        Cursor cursor = databaseHelper.getAllIngredients();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow("unit")
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            String ingredientText =
                    name +
                            "\nQuantity: " +
                            quantity +
                            " " +
                            unit;

            if (expiryDate != null &&
                    !expiryDate.isEmpty()) {

                ingredientText +=
                        "\nExpiry: " +
                                expiryDate;
            }

            ingredients.add(ingredientText);
            ingredientIds.add(id);
        }

        cursor.close();

        if (ingredients.isEmpty()) {

            txtEmptyPantry.setVisibility(TextView.VISIBLE);
            listIngredients.setVisibility(ListView.GONE);

        } else {

            txtEmptyPantry.setVisibility(TextView.GONE);
            listIngredients.setVisibility(ListView.VISIBLE);
        }

        ingredientAdapter =
                new IngredientAdapter(
                        this,
                        ingredients
                );

        listIngredients.setAdapter(ingredientAdapter);
    }

    private void showIngredientOptions(int ingredientId) {

        String[] options = {
                "Edit Ingredient",
                "Delete Ingredient"
        };

        new AlertDialog.Builder(this)
                .setTitle("Ingredient Options")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        editIngredient(ingredientId);
                    } else {
                        confirmDelete(ingredientId);
                    }
                })
                .show();
    }

    private void editIngredient(int ingredientId) {

        Intent intent = new Intent(
                MainActivity.this,
                AddEditIngredientActivity.class
        );

        intent.putExtra(
                "ingredient_id",
                ingredientId
        );

        startActivity(intent);
    }

    private void confirmDelete(int ingredientId) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage(
                        "Are you sure you want to delete this ingredient?"
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            boolean deleted =
                                    databaseHelper.deleteIngredient(
                                            ingredientId
                                    );

                            if (deleted) {

                                Toast.makeText(
                                        MainActivity.this,
                                        "Ingredient deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                loadIngredients();

                            } else {

                                Toast.makeText(
                                        MainActivity.this,
                                        "Could not delete ingredient",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .show();
    }
}