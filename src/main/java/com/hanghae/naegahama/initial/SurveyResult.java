package com.hanghae.naegahama.initial;

import java.util.HashMap;
import java.util.Map;

public class SurveyResult {

    public static Map<String, String> hippoMap = new HashMap<String, String>(){{
        put("감성계획외향", HippoResult.effortHippo);
        put("감성계획내향", HippoResult.nagneHippo);
        put("감성직관외향", HippoResult.smellHippo);
        put("감성직관내향", HippoResult.sweetHippo);
        put("이성계획외향", HippoResult.leaderHippo);
        put("이성계획내향", HippoResult.smartHippo);
        put("이성직관외향", HippoResult.coolHippo);
        put("이성직관내향", HippoResult.sentiHippo);
    }};
}
