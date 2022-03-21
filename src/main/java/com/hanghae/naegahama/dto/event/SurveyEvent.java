package com.hanghae.naegahama.dto.event;

import com.hanghae.naegahama.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SurveyEvent {
    private User user;
}
