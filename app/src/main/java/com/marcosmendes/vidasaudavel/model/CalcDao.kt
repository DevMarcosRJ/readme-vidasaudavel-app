package com.marcosmendes.vidasaudavel.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalcDao {

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * FROM CALC WHERE type = :type")
    fun getRegisterByType(type: String) : List<Calc>
}