package com.douglasrondini.tanalista.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deletItem(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Query("SELECT * FROM item_table WHERE category = :category")
    fun getItemsByCategory(category: String): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item_table")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("DELETE FROM item_table")
    suspend fun deleteAllItems()
}