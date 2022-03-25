package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
    public enum AlarmType {
        comment, child, answer, rate, rated, level, likeA, likeP, pointR, pointAL, pointPL,pointA, answerC, pointRD,
    @JsonProperty("achieve")
    achieve
    }
