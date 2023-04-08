package com.example.SamiMahsaGotProf_COMP304Sec003.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.SamiMahsaGotProf_COMP304Sec003.model.Landmark
import kotlinx.coroutines.flow.Flow

@Dao
interface LandmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLandmark(landmark: Landmark)

    @Query("SELECT * FROM tbl_landmark")
    fun getAllLandmarks(): Flow<List<Landmark>>

    @Query("SELECT * FROM tbl_landmark WHERE type = :type")
    fun getAllLandmarksByType(type: String): Flow<List<Landmark>>
}