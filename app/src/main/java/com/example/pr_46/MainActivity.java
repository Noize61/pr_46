package com.example.pr_46;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText editTextName, editTextType, editTextPrice;
    private TextView textViewFurnitures;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        editTextName = findViewById(R.id.editTextName);
        editTextType = findViewById(R.id.editTextType);
        editTextPrice = findViewById(R.id.editTextPrice);
        textViewFurnitures = findViewById(R.id.textViewFurnitures);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFurniture();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        displayFurnitures();
    }

    private void addFurniture() {
        new Thread(() -> {
            Furniture furniture = new Furniture();
            furniture.name = editTextName.getText().toString();
            furniture.type = editTextType.getText().toString();
            furniture.price = Double.parseDouble(editTextPrice.getText().toString());
            db.furnitureDao().insert(furniture);
            runOnUiThread(this::displayFurnitures);
        }).start();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Чтобы удалить нужную позицию, введите ID позиции");

        // Создаем EditText для ввода ID
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFurniture(input.getText().toString());
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteFurniture(String idString) {
        new Thread(() -> {
            try {
                int id = Integer.parseInt(idString);
                Furniture furniture = db.furnitureDao().getFurnitureById(id);
                if (furniture != null) {
                    db.furnitureDao().delete(furniture);
                    runOnUiThread(this::displayFurnitures);
                } else {
                    Log.e("DeleteFurniture", "Furniture not found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                Log.e("DeleteFurniture", "Invalid ID format", e);
            }
        }).start();
    }

    private void displayFurnitures() {
        new Thread(() -> {
            List<Furniture> furnitures = db.furnitureDao().getAllFurnitures();
            StringBuilder builder = new StringBuilder();
            for (Furniture f : furnitures) {
                builder.append("ID: ").append(f.id).append("\n")
                        .append("Название мебели: ").append(f.name).append("\n")
                        .append("Тип мебели: ").append(f.type).append("\n")
                        .append("Цена: ").append(f.price).append("\n")
                        .append("\n").append("\n");
            }
            runOnUiThread(() -> textViewFurnitures.setText(builder.toString()));
        }).start();
    }
}