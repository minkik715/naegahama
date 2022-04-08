package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class SurveyRequestDto {
    @NotNull(message = "테스트 값은 필수입니다.")
    private int[] result;

    private static int[] emotion = new int[]{0, 4, 6};
    private static int[] plan = new int[]{1, 2, 7};
    private static int[] action = new int[]{3, 5, 8};

    private static String[] emotions = new String[]{"감성", "이성"};
    private static String[] plans = new String[]{"계획", "직관"};
    private static String[] actions = new String[]{"외향", "내향"};

    public String isEmotion(){
        return emotions[checkCharacter(emotion)];
    }

    public String isPlan(){
        return plans[checkCharacter(plan)];
    }

    public String isAction(){
        return actions[checkCharacter(action)];
    }


    private int checkCharacter( int[] character) {
        int sum = 0;
        for (int i : character) {
            if(this.result[i] == 1){
                sum++;
            }
        }
        return sum >= 2 ? 0 : 1;
    }


}
