package com.example.demozeroset.domain

import com.example.demozeroset.configEnum.Period
import com.example.demozeroset.configEnum.SortBy
import com.example.demozeroset.dto.ConfigurationRequestDto

class Configuration(
    var refreshMainBoardPeriod : Period = Period.THIRTY_MINUTE,
    var cycleSetCountPeriod : Period = Period.ONE_DAY,
    var userSetChangedSort : SortBy = SortBy.RECENT,
    var userSetRegisterSort : SortBy = SortBy.REGISTER_TIME
    )
{
    fun changeConfiguration( configurationRequestDto: ConfigurationRequestDto){
        this.refreshMainBoardPeriod = configurationRequestDto.refreshMainBoardPeriod
        this.cycleSetCountPeriod = configurationRequestDto.cycleSetCountPeriod
        this.userSetChangedSort = configurationRequestDto.userSetChangedSort
        this.userSetRegisterSort = configurationRequestDto.userSetRegisterSort
    }

}