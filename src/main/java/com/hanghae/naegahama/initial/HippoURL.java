package com.hanghae.naegahama.initial;

public class HippoURL
{
    public static String hippoType1 = "열심히 노력 하마";
    public static String hippoType2 = "내가 리더 하마";
    public static String hippoType3 = "하마 냄새가 나는 하마";
    public static String hippoType4 = "세상 시원시원한 하마";
    public static String hippoType5 = "나그네 하마";
    public static String hippoType6 = "스마트 하마";
    public static String hippoType7 = "스윗 하마";
    public static String hippoType8 = "센치 하마";

    // 기본
    public static String basicHippoURL = "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%87%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%84%86%E1%85%A1.svg";

    private static String[] effortHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/effort1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/effort2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/effort3circle.png",
            };

    private static String[] leaderHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/leader1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/leader2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/leader3circle.png",
            };

    private static String[] smellHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smell1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smell2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smell3circle.png",
            };

    private static String[] coolHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/cool1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/cool2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/cool3circle.png",
            };

    private static String[] travelerHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/traveler1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/traveler2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/traveler3circle.png",
            };

    private static String[] smartHippoUrl1 = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smart1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smart2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smart3circle.png",
            };

    private static String[] sweetHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/sweet1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/sweet2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/sweet3circle.png",
            };

    private static String[] sentiHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/senti1circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/senti2circle.png",
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/senti3circle.png",
            };

    public static String name(String hippoName, Integer hippoLv)
    {
        if(hippoName.equals(hippoType1))
        {
            return HippoIndex(effortHippoUrl, hippoLv);
        }
        else if(hippoName.equals(hippoType2))
        {
            return HippoIndex(leaderHippoUrl, hippoLv);
        }
        else if(hippoName.equals(hippoType3))
        {
            return HippoIndex(smellHippoUrl, hippoLv);
        }
        else if(hippoName.equals(hippoType4))
        {
            return HippoIndex(coolHippoUrl, hippoLv);
        }
        else if(hippoName.equals(hippoType5))
        {
            return HippoIndex(travelerHippoUrl, hippoLv);
        }
        else if(hippoName.equals(hippoType6))
        {
            return HippoIndex(smartHippoUrl1, hippoLv);
        }
        else if(hippoName.equals(hippoType7))
        {
            return HippoIndex(sweetHippoUrl, hippoLv);
        }
        else if(hippoName.equals(hippoType8))
        {
            return HippoIndex(sentiHippoUrl, hippoLv);
        }
        return basicHippoURL;
    }

    private static String HippoIndex(String[] hippoType, Integer hippoLv)
    {
        switch(hippoLv)
        {
            case 1:
                return hippoType[0];
            case 2:
                return hippoType[1];
            case 3:
                return hippoType[2];
            default:
                return basicHippoURL;
        }
    }
}
