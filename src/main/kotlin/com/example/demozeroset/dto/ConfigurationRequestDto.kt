package com.example.demozeroset.dto

import com.example.demozeroset.configEnum.Period
import com.example.demozeroset.configEnum.SortBy
import org.bson.types.ObjectId

data class ConfigurationRequestDto(
    val refreshMainBoardPeriod : Period,
    val cycleSetCountPeriod : Period,
    val userSetChangedSort : SortBy,
    val userSetRegisterSort : SortBy
)
