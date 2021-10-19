package com.lfg.miacell.database

import androidx.room.*
import com.lfg.miacell.entities.Product

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product ORDER BY id")
    fun loadAllProducts(): MutableList<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product?)

    @Update
    fun updateProduct(product: Product?)

    @Delete
    fun delete(product: Product?)

    @Query("SELECT * FROM product WHERE id = :id")
    fun loadProductById(id: Long): Product?

}