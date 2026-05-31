package com.example.retrovault.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [GameEntity::class],
    version = 4,
    exportSchema = false
)
abstract class RetroVaultDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var instance: RetroVaultDatabase? = null

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add new columns with sensible defaults
                database.execSQL("ALTER TABLE games ADD COLUMN favorito INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE games ADD COLUMN zerado INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE games ADD COLUMN naListaDeDesejos INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE games ADD COLUMN dataConclusao INTEGER")
                database.execSQL("ALTER TABLE games ADD COLUMN horasJogadas INTEGER")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE games ADD COLUMN imageUri TEXT")
            }
        }

        fun getInstance(context: Context): RetroVaultDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RetroVaultDatabase::class.java,
                    "retrovault.db"
                )
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}
