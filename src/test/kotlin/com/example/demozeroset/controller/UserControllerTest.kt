package com.example.demozeroset.controller

import com.example.demozeroset.configEnum.Period
import com.example.demozeroset.configEnum.SortBy
import com.example.demozeroset.domain.User
import com.example.demozeroset.dto.ConfigurationRequestDto
import com.example.demozeroset.dto.UserRequestDto
import com.example.demozeroset.repository.UserRepository
import com.example.demozeroset.service.ConfigurationService
import com.example.demozeroset.service.UserService
import org.assertj.core.api.Assertions.*
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
internal class UserControllerTest @Autowired constructor(val userService: UserService, val configurationService: ConfigurationService ){


   /* @Test
    @DisplayName("저장이 잘 되는지, 캐쉬가 되는지")
    fun saveWell() {
        //given
        val saveUser = userService.join(UserRequestDto("minki", 20))

        //then
        val findUser = userService.findUserById(saveUser?.userId!!)
        assertThat(findUser == saveUser)
        assertThat(findUser?.userId == saveUser.userId)
    }

   @Test
    @DisplayName("유저마다 설정 정보가 잘 바뀌는지")
    fun configurationChangedWell(){
        //given
        var objectId : ObjectId = ObjectId("6270b6a5fe4e1f4ce0939ce3")

        //when
        var changedConfigurationInfo : ConfigurationRequestDto = ConfigurationRequestDto(Period.ONE_QUARTER,Period.ONE_YEAR,SortBy.REGISTER_TIME,SortBy.REGISTER_TIME)
        val findUser = userService.findUserById(objectId);
        configurationService.changeConfiguration(objectId, changedConfigurationInfo)

        val findUserAfterChange = userService.findUserById(objectId);
        //then
        assertThat(findUser?.configuration?.cycleSetCountPeriod).isNotEqualTo(findUserAfterChange?.configuration?.cycleSetCountPeriod)

    }*/


}