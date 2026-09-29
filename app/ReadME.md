# Smart Pantry Manager

## Overview

Smart Pantry Manager is an Android application developed for the Mobile App Development 700 practical assessment.

The application allows users to manage ingredients stored in their pantry and receive recipe suggestions based only on the ingredients they currently have available.

## Main Features

* Add pantry ingredients
* Edit existing ingredients
* Delete pantry ingredients
* Store ingredient quantities and units
* Store optional expiry dates
* Persistent SQLite database storage
* View available pantry ingredients
* View suggested recipes
* Strict recipe ingredient matching
* Quantity checking for recipe requirements
* Unit conversion for compatible units
* Recipe detail screen
* Settings/About screen
* Feedback when no recipes can be prepared

## Recipe Matching

The application uses strict recipe matching.

A recipe is suggested only when:

1. Every required ingredient is available in the pantry.
2. The available quantity is sufficient.
3. Compatible measurement units can be converted when necessary.

Recipes with missing ingredients or insufficient quantities are not displayed.

The application also normalises common ingredient names and units so that simple differences such as singular/plural forms can still be matched.

## Technologies Used

* Java
* Android Studio
* Android SDK
* SQLite
* Android Activities
* Intents
* ListView and Adapters
* Persistent local data storage

## Application Structure

Important components include:

* `MainActivity` - Main pantry screen
* `AddEditIngredientActivity` - Add and edit pantry ingredients
* `SuggestedRecipesActivity` - Determines and displays recipes that can be prepared
* `RecipeDetailActivity` - Displays recipe ingredients and preparation
* `SettingsActivity` - Displays application information
* `DatabaseHelper` - Handles SQLite database creation and data operations
* `IngredientAdapter` - Displays pantry ingredients
* `RecipeAdapter` - Displays suggested recipes

## Database

The application uses SQLite for persistent storage.

The database contains:

### Ingredients

Stores:

* Ingredient ID
* Ingredient name
* Quantity
* Unit
* Expiry date

### Recipes

Stores:

* Recipe ID
* Recipe name
* Required ingredients
* Preparation instructions

The application includes a collection of preloaded recipes for testing the recipe suggestion functionality.

## Testing

The following core functionality has been tested:

* Adding an ingredient
* Editing an ingredient
* Deleting an ingredient
* Storing ingredient information
* Displaying pantry ingredients
* Suggesting recipes based on available ingredients
* Preventing recipes with missing ingredients from being suggested
* Checking sufficient ingredient quantities
* Opening recipe details
* Opening the Settings screen
* Persistent storage of pantry data

## Project Purpose

The purpose of Smart Pantry Manager is to demonstrate the use of Android application development concepts including activities, layouts, intents, adapters, event handling, database persistence and application logic.

## Version

Version 1.0
