package com.example.pr_46;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "furnitures")
public class Furniture {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name; // Название мебели

    @ColumnInfo(name = "type")
    public String type; // Тип мебели

    @ColumnInfo(name = "price")
    public double price; // Цена мебели
}
