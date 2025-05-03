package com.jadesoft.javhub.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.db.dto.SearchHistoryEntity

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

    @Query("DELETE FROM movieentity WHERE code IN (:list)")
    suspend fun deleteMovies(list: List<String>)

    @Query("SELECT EXISTS(SELECT 1 FROM movieentity WHERE code = :code LIMIT 1)")
    suspend fun isMovieExists(code: String): Boolean

    @Query("""
        UPDATE MovieEntity 
        SET tag = 
          CASE 
            WHEN TRIM(
              REPLACE(
                REPLACE(
                  REPLACE(
                    REPLACE(tag, ',' || :targetTag, ''),
                    :targetTag || ',', ''
                  ),
                  :targetTag, '默认'
                ),
                ',,', ','
              )
            ) = '' 
            THEN '默认'
            ELSE TRIM(
              REPLACE(
                REPLACE(
                  REPLACE(
                    REPLACE(tag, ',' || :targetTag, ''),
                    :targetTag || ',', ''
                  ),
                  :targetTag, '默认'
                ),
                ',,', ','
              ),
              ','
            )
          END
        WHERE tag LIKE '%' || :targetTag || '%'
    """)
    suspend fun replaceTag(targetTag: String)


    /* ---------- HistoryEntity ---------- */
    @Query("SELECT * FROM historyentity")
    suspend fun getHistories(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(movie: HistoryEntity)

    @Query("DELETE FROM historyentity WHERE code = :code")
    suspend fun deleteHistory(code: String)

    @Query("DELETE FROM historyentity")
    suspend fun deleteAllHistory()


    /* ---------- SearchHistoryEntity ---------- */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(history: SearchHistoryEntity)

    @Query("SELECT * FROM searchhistoryentity")
    suspend fun getSearchHistories(): List<SearchHistoryEntity>

    @Query("DELETE FROM searchhistoryentity WHERE `query` = :query")
    suspend fun deleteSearchHistoryByQuery(query: String)

    @Query("DELETE FROM searchhistoryentity")
    suspend fun deleteSearchHistories()

    @Query("DELETE FROM searchhistoryentity WHERE `query` = (SELECT `query` FROM searchhistoryentity ORDER BY timestamp ASC LIMIT 1)")
    suspend fun deleteFirstSearchHistory()
}