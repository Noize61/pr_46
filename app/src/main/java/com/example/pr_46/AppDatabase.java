package com.example.pr_46;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Furniture.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FurnitureDao furnitureDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "furnitures-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}