package com.jadesoft.javhub.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.model.Movie

@Dao
interface JavHubDao {

    /* ---------- MovieEntity ---------- */
    @Query("SELECT * FROM movieentity")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query(
        """
        SELECT * FROM movieentity
        WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchMovieByCode(query: String): List<MovieEntity>

    @Query(
        """
        SELECT * FROM movieentity
        WHERE LOWER(code) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchMovieByTitle(query: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM movieentity WHERE code = :code")
    suspend fun deleteMovieByCode(code: String)

    @Query("SELECT EXISTS(SELECT 1 FROM movieentity WHERE code = :code LIMIT 1)")
    suspend fun isMovieExists(code: String): Boolean


    /* ---------- HistoryEntity ---------- */
    @Query("SELECT * FROM historyentity")
    suspend fun getHistories(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(movie: HistoryEntity)

    @Query("DELETE FROM historyentity WHERE code = :code")
    suspend fun deleteHistory(code: String)
}