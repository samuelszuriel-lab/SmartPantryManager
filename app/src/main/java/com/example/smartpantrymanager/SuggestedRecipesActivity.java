package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SuggestedRecipesActivity extends AppCompatActivity {

    ListView listRecipes;
    TextView txtNoRecipes;

    DatabaseHelper databaseHelper;

    ArrayList<String> suggestedRecipes;
    ArrayList<Integer> recipeIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        listRecipes = findViewById(R.id.listRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        databaseHelper = new DatabaseHelper(this);

        suggestedRecipes = new ArrayList<>();
        recipeIds = new ArrayList<>();

        listRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    int recipeId = recipeIds.get(position);

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra("recipe_id", recipeId);

                    startActivity(intent);
                }
        );

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        suggestedRecipes.clear();
        recipeIds.clear();

        Map<String, PantryItem> pantry = getPantryIngredients();

        Cursor cursor = databaseHelper.getAllRecipes();

        while (cursor.moveToNext()) {

            int recipeId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String recipeName = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            String ingredients = cursor.getString(
                    cursor.getColumnIndexOrThrow("ingredients")
            );

            if (recipeCanBeMade(ingredients, pantry)) {

                suggestedRecipes.add(recipeName);
                recipeIds.add(recipeId);
            }
        }

        cursor.close();

        if (suggestedRecipes.isEmpty()) {

            txtNoRecipes.setText(
                    "No recipes can be made with your current pantry.\n\n" +
                            "Add the required ingredients and make sure you have " +
                            "enough of each ingredient."
            );

            txtNoRecipes.setVisibility(TextView.VISIBLE);
            listRecipes.setVisibility(ListView.GONE);

        } else {

            txtNoRecipes.setVisibility(TextView.GONE);
            listRecipes.setVisibility(ListView.VISIBLE);

            RecipeAdapter adapter =
                    new RecipeAdapter(
                            this,
                            suggestedRecipes
                    );

            listRecipes.setAdapter(adapter);
        }
    }

    private Map<String, PantryItem> getPantryIngredients() {

        Map<String, PantryItem> pantry = new HashMap<>();

        Cursor cursor = databaseHelper.getAllIngredients();

        while (cursor.moveToNext()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow("unit")
            );

            String normalisedName = normaliseIngredient(name);
            String normalisedUnit = normaliseUnit(unit);

            if (pantry.containsKey(normalisedName)) {

                PantryItem existing =
                        pantry.get(normalisedName);

                if (canConvertUnits(
                        existing.unit,
                        normalisedUnit
                )) {

                    double converted =
                            convertQuantity(
                                    quantity,
                                    normalisedUnit,
                                    existing.unit
                            );

                    existing.quantity += converted;
                }

            } else {

                pantry.put(
                        normalisedName,
                        new PantryItem(
                                quantity,
                                normalisedUnit
                        )
                );
            }
        }

        cursor.close();

        return pantry;
    }

    private boolean recipeCanBeMade(
            String ingredientsText,
            Map<String, PantryItem> pantry) {

        if (ingredientsText == null ||
                ingredientsText.trim().isEmpty()) {

            return false;
        }

        String[] requiredIngredients =
                ingredientsText.split(",");

        for (String ingredient :
                requiredIngredients) {

            String[] parts =
                    ingredient.trim().split(":");

            if (parts.length < 3) {
                return false;
            }

            String requiredName =
                    normaliseIngredient(parts[0]);

            double requiredQuantity;

            try {

                requiredQuantity =
                        Double.parseDouble(
                                parts[1].trim()
                        );

            } catch (NumberFormatException e) {

                return false;
            }

            String requiredUnit =
                    normaliseUnit(parts[2]);

            PantryItem pantryItem =
                    pantry.get(requiredName);

            if (pantryItem == null) {
                return false;
            }

            if (!canConvertUnits(
                    pantryItem.unit,
                    requiredUnit
            )) {
                return false;
            }

            double availableQuantity =
                    convertQuantity(
                            pantryItem.quantity,
                            pantryItem.unit,
                            requiredUnit
                    );

            if (availableQuantity + 0.0001 <
                    requiredQuantity) {

                return false;
            }
        }

        return true;
    }

    private String normaliseIngredient(String ingredient) {

        String value =
                ingredient
                        .trim()
                        .toLowerCase(Locale.ROOT);

        value = value.replace("-", " ");
        value = value.replace("_", " ");

        value = value.replaceAll(
                "\\s+",
                " "
        );

        if (value.equals("eggs")) {
            return "egg";
        }

        if (value.equals("tomatoes")) {
            return "tomato";
        }

        if (value.equals("potatoes")) {
            return "potato";
        }

        if (value.equals("bananas")) {
            return "banana";
        }

        if (value.endsWith("ies")) {

            return value.substring(
                    0,
                    value.length() - 3
            ) + "y";
        }

        if (value.endsWith("s")
                && !value.endsWith("ss")
                && !value.endsWith("us")) {

            return value.substring(
                    0,
                    value.length() - 1
            );
        }

        return value;
    }

    private String normaliseUnit(String unit) {

        String value =
                unit
                        .trim()
                        .toLowerCase(Locale.ROOT);

        value = value.replace(".", "");

        switch (value) {

            case "kg":
            case "kgs":
            case "kilogram":
            case "kilograms":
                return "kg";

            case "g":
            case "gram":
            case "grams":
                return "g";

            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return "l";

            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return "ml";

            case "cup":
            case "cups":
                return "cup";

            case "tbsp":
            case "tablespoon":
            case "tablespoons":
                return "tbsp";

            case "tsp":
            case "teaspoon":
            case "teaspoons":
                return "tsp";

            case "unit":
            case "units":
            case "each":
                return "unit";

            case "slice":
            case "slices":
                return "slice";

            case "clove":
            case "cloves":
                return "clove";

            case "leaf":
            case "leaves":
                return "leaf";

            default:
                return value;
        }
    }

    private boolean canConvertUnits(
            String fromUnit,
            String toUnit) {

        String from = normaliseUnit(fromUnit);
        String to = normaliseUnit(toUnit);

        if (from.equals(to)) {
            return true;
        }

        if ((from.equals("g") || from.equals("kg"))
                && (to.equals("g") || to.equals("kg"))) {

            return true;
        }

        if ((from.equals("ml") || from.equals("l"))
                && (to.equals("ml") || to.equals("l"))) {

            return true;
        }

        if ((from.equals("tbsp") || from.equals("tsp"))
                && (to.equals("tbsp") || to.equals("tsp"))) {

            return true;
        }

        return false;
    }

    private double convertQuantity(
            double quantity,
            String fromUnit,
            String toUnit) {

        String from = normaliseUnit(fromUnit);
        String to = normaliseUnit(toUnit);

        if (from.equals(to)) {
            return quantity;
        }

        if (from.equals("kg")
                && to.equals("g")) {

            return quantity * 1000;
        }

        if (from.equals("g")
                && to.equals("kg")) {

            return quantity / 1000;
        }

        if (from.equals("l")
                && to.equals("ml")) {

            return quantity * 1000;
        }

        if (from.equals("ml")
                && to.equals("l")) {

            return quantity / 1000;
        }

        if (from.equals("tbsp")
                && to.equals("tsp")) {

            return quantity * 3;
        }

        if (from.equals("tsp")
                && to.equals("tbsp")) {

            return quantity / 3;
        }

        return quantity;
    }

    private static class PantryItem {

        double quantity;
        String unit;

        PantryItem(
                double quantity,
                String unit) {

            this.quantity = quantity;
            this.unit = unit;
        }
    }
}