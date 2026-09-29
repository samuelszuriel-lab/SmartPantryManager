package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    EditText editIngredientName;
    EditText editQuantity;
    EditText editUnit;
    EditText editExpiryDate;
    Button btnSaveIngredient;

    DatabaseHelper databaseHelper;

    int ingredientId = -1;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);
        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        databaseHelper = new DatabaseHelper(this);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        if (ingredientId != -1) {
            isEditMode = true;
            loadIngredient();
            btnSaveIngredient.setText("Update Ingredient");
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void loadIngredient() {

        Cursor cursor = databaseHelper.getAllIngredients();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            if (id == ingredientId) {

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

                editIngredientName.setText(name);
                editQuantity.setText(String.valueOf(quantity));
                editUnit.setText(unit);

                if (expiryDate != null) {
                    editExpiryDate.setText(expiryDate);
                }

                break;
            }
        }

        cursor.close();
    }

    private void saveIngredient() {

        String name = editIngredientName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            editIngredientName.setError("Please enter an ingredient name");
            return;
        }

        if (quantityText.isEmpty()) {
            editQuantity.setError("Please enter a quantity");
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError("Please enter a unit");
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Please enter a valid number");
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than 0");
            return;
        }

        if (isEditMode) {

            boolean updated = databaseHelper.updateIngredient(
                    ingredientId,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            if (updated) {

                Toast.makeText(
                        AddEditIngredientActivity.this,
                        "Ingredient updated",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        AddEditIngredientActivity.this,
                        "Error updating ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            boolean saved = databaseHelper.addIngredient(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            if (saved) {

                Toast.makeText(
                        AddEditIngredientActivity.this,
                        "Ingredient saved: " + name,
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        AddEditIngredientActivity.this,
                        "Error saving ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}