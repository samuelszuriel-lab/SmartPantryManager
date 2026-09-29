package com.example.smartpantrymanager;

public class RecipeData {

    public static void loadRecipes(DatabaseHelper databaseHelper) {

        databaseHelper.addRecipe(
                "Pancakes",
                "flour, eggs, milk, sugar",
                "Mix the flour, eggs, milk and sugar into a smooth batter. Heat a pan and cook the pancakes on both sides until golden."
        );

        databaseHelper.addRecipe(
                "Scrambled Eggs",
                "eggs, milk, butter, salt",
                "Whisk the eggs with milk and salt. Melt butter in a pan and cook the mixture while stirring until set."
        );

        databaseHelper.addRecipe(
                "French Toast",
                "bread, eggs, milk, sugar",
                "Whisk eggs, milk and sugar together. Dip the bread into the mixture and fry both sides until golden."
        );

        databaseHelper.addRecipe(
                "Spaghetti Bolognese",
                "spaghetti, beef, tomato, onion, garlic",
                "Cook the spaghetti. Fry the beef with onion and garlic, add tomato and simmer. Serve the sauce over the spaghetti."
        );

        databaseHelper.addRecipe(
                "Chicken Pasta",
                "pasta, chicken, milk, cheese, garlic",
                "Cook the pasta. Fry the chicken and garlic, add milk and cheese, then mix with the cooked pasta."
        );

        databaseHelper.addRecipe(
                "Chicken Sandwich",
                "bread, chicken, lettuce, tomato, mayonnaise",
                "Cook the chicken and slice it. Place chicken, lettuce and tomato between slices of bread with mayonnaise."
        );

        databaseHelper.addRecipe(
                "Grilled Cheese Sandwich",
                "bread, cheese, butter",
                "Butter the bread and place cheese between the slices. Grill both sides until the bread is golden and the cheese melts."
        );

        databaseHelper.addRecipe(
                "Omelette",
                "eggs, cheese, onion, tomato, butter",
                "Beat the eggs. Fry the onion and tomato in butter, add the eggs and cheese, then fold the omelette when cooked."
        );

        databaseHelper.addRecipe(
                "Tomato Pasta",
                "pasta, tomato, onion, garlic, olive oil",
                "Cook the pasta. Fry onion and garlic in olive oil, add tomato and simmer. Mix with the pasta."
        );

        databaseHelper.addRecipe(
                "Chicken Curry",
                "chicken, onion, garlic, tomato, curry powder",
                "Fry onion and garlic. Add chicken and curry powder, then add tomato and simmer until the chicken is fully cooked."
        );

        databaseHelper.addRecipe(
                "Beef Stir Fry",
                "beef, onion, pepper, garlic, soy sauce",
                "Slice the beef and vegetables. Stir-fry the beef with onion, pepper and garlic, then add soy sauce."
        );

        databaseHelper.addRecipe(
                "Rice and Chicken",
                "rice, chicken, onion, garlic",
                "Cook the rice. Fry the chicken with onion and garlic until fully cooked, then serve with the rice."
        );

        databaseHelper.addRecipe(
                "Egg Fried Rice",
                "rice, eggs, onion, soy sauce, oil",
                "Cook the rice. Fry onion, add eggs and scramble them, then add rice and soy sauce and stir-fry."
        );

        databaseHelper.addRecipe(
                "Mashed Potatoes",
                "potatoes, milk, butter, salt",
                "Boil the potatoes until soft. Mash them with milk, butter and salt until smooth."
        );

        databaseHelper.addRecipe(
                "Potato Omelette",
                "potatoes, eggs, onion, oil",
                "Cook sliced potatoes and onion in oil. Add beaten eggs and cook until the omelette is set."
        );

        databaseHelper.addRecipe(
                "Garlic Bread",
                "bread, butter, garlic",
                "Mix butter with crushed garlic. Spread over the bread and bake until golden."
        );

        databaseHelper.addRecipe(
                "Banana Pancakes",
                "banana, eggs, flour, milk",
                "Mash the banana and mix with eggs, flour and milk. Cook portions of the batter in a heated pan."
        );

        databaseHelper.addRecipe(
                "Chocolate Milkshake",
                "milk, banana, sugar, cocoa powder",
                "Blend the milk, banana, sugar and cocoa powder until smooth and creamy."
        );
    }
}
