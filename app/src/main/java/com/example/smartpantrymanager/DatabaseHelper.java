package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 5;

    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TABLE_RECIPES = "recipes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "expiry_date TEXT" +
                        ")";

        db.execSQL(createIngredientsTable);

        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "ingredients TEXT NOT NULL, " +
                        "preparation TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipesTable);

        loadInitialRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);

        onCreate(db);
    }

    private void loadInitialRecipes(SQLiteDatabase db) {

        addInitialRecipe(
                db,
                "Pancakes",
                "flour:2:cup, eggs:2:unit, milk:1:cup, sugar:1:tbsp",
                "Mix the flour, eggs, milk and sugar into a smooth batter. Heat a pan and cook the pancakes on both sides until golden."
        );

        addInitialRecipe(
                db,
                "Scrambled Eggs",
                "eggs:2:unit, milk:1:cup, butter:1:tbsp, salt:1:tsp",
                "Whisk the eggs with milk and salt. Melt butter in a pan and cook the mixture while stirring until set."
        );

        addInitialRecipe(
                db,
                "French Toast",
                "bread:2:slices, eggs:2:unit, milk:1:cup, sugar:1:tbsp",
                "Whisk eggs, milk and sugar together. Dip the bread into the mixture and fry both sides until golden."
        );

        addInitialRecipe(
                db,
                "Spaghetti Bolognese",
                "spaghetti:200:g, beef:200:g, tomato:2:unit, onion:1:unit, garlic:2:cloves",
                "Cook the spaghetti. Fry the beef with onion and garlic, add tomato and simmer. Serve the sauce over the spaghetti."
        );

        addInitialRecipe(
                db,
                "Chicken Pasta",
                "pasta:200:g, chicken:200:g, milk:1:cup, cheese:100:g, garlic:2:cloves",
                "Cook the pasta. Fry the chicken and garlic, add milk and cheese, then mix with the cooked pasta."
        );

        addInitialRecipe(
                db,
                "Chicken Sandwich",
                "bread:2:slices, chicken:150:g, lettuce:2:leaves, tomato:1:unit, mayonnaise:1:tbsp",
                "Cook the chicken and slice it. Place chicken, lettuce and tomato between slices of bread with mayonnaise."
        );

        addInitialRecipe(
                db,
                "Grilled Cheese Sandwich",
                "bread:2:slices, cheese:100:g, butter:1:tbsp",
                "Butter the bread and place cheese between the slices. Grill both sides until the bread is golden and the cheese melts."
        );

        addInitialRecipe(
                db,
                "Omelette",
                "eggs:3:unit, cheese:50:g, onion:1:unit, tomato:1:unit, butter:1:tbsp",
                "Beat the eggs. Fry the onion and tomato in butter, add the eggs and cheese, then fold the omelette when cooked."
        );

        addInitialRecipe(
                db,
                "Tomato Pasta",
                "pasta:200:g, tomato:2:unit, onion:1:unit, garlic:2:cloves, olive oil:1:tbsp",
                "Cook the pasta. Fry onion and garlic in olive oil, add tomato and simmer. Mix with the pasta."
        );

        addInitialRecipe(
                db,
                "Chicken Curry",
                "chicken:250:g, onion:1:unit, garlic:2:cloves, tomato:2:unit, curry powder:2:tbsp",
                "Fry onion and garlic. Add chicken and curry powder, then add tomato and simmer until the chicken is fully cooked."
        );

        addInitialRecipe(
                db,
                "Beef Stir Fry",
                "beef:200:g, onion:1:unit, pepper:1:unit, garlic:2:cloves, soy sauce:2:tbsp",
                "Slice the beef and vegetables. Stir-fry the beef with onion, pepper and garlic, then add soy sauce."
        );

        addInitialRecipe(
                db,
                "Rice and Chicken",
                "rice:200:g, chicken:200:g, onion:1:unit, garlic:2:cloves",
                "Cook the rice. Fry the chicken with onion and garlic until fully cooked, then serve with the rice."
        );

        addInitialRecipe(
                db,
                "Egg Fried Rice",
                "rice:200:g, eggs:2:unit, onion:1:unit, soy sauce:1:tbsp, oil:1:tbsp",
                "Cook the rice. Fry onion, add eggs and scramble them, then add rice and soy sauce and stir-fry."
        );

        addInitialRecipe(
                db,
                "Mashed Potatoes",
                "potatoes:300:g, milk:1:cup, butter:1:tbsp, salt:1:tsp",
                "Boil the potatoes until soft. Mash them with milk, butter and salt until smooth."
        );

        addInitialRecipe(
                db,
                "Potato Omelette",
                "potatoes:200:g, eggs:3:unit, onion:1:unit, oil:1:tbsp",
                "Cook sliced potatoes and onion in oil. Add beaten eggs and cook until the omelette is set."
        );

        addInitialRecipe(
                db,
                "Garlic Bread",
                "bread:4:slices, butter:2:tbsp, garlic:2:cloves",
                "Mix butter with crushed garlic. Spread over the bread and bake until golden."
        );

        addInitialRecipe(
                db,
                "Banana Pancakes",
                "banana:1:unit, eggs:2:unit, flour:1:cup, milk:1:cup",
                "Mash the banana and mix with eggs, flour and milk. Cook portions of the batter in a heated pan."
        );

        addInitialRecipe(
                db,
                "Chocolate Milkshake",
                "milk:2:cup, banana:1:unit, sugar:1:tbsp, cocoa powder:2:tbsp",
                "Blend the milk, banana, sugar and cocoa powder until smooth and creamy."
        );
    }

    private void addInitialRecipe(
            SQLiteDatabase db,
            String name,
            String ingredients,
            String preparation) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("preparation", preparation);

        db.insert(TABLE_RECIPES, null, values);
    }

    public boolean addIngredient(
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        long result = db.insert(
                TABLE_INGREDIENTS,
                null,
                values
        );

        return result != -1;
    }

    public Cursor getAllIngredients() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );
    }

    public boolean updateIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        int result = db.update(
                TABLE_INGREDIENTS,
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    public boolean deleteIngredient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_INGREDIENTS,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    public boolean addRecipe(
            String name,
            String ingredients,
            String preparation) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("preparation", preparation);

        long result = db.insert(
                TABLE_RECIPES,
                null,
                values
        );

        return result != -1;
    }

    public Cursor getAllRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );
    }
}