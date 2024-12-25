package com.example.pr_46;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FurnitureDao {
    @Insert
    void insert(Furniture furniture);

    @Query("SELECT * FROM furnitures")
    List<Furniture> getAllFurnitures();

    @Query("SELECT * FROM furnitures WHERE id = :furnitureId")
    Furniture getFurnitureById(int furnitureId);

    @Delete
    void delete(Furniture furniture);
}