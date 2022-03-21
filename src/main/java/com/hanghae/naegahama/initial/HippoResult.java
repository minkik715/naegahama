package com.hanghae.naegahama.initial;

public class HippoResult {
    public static String hippoType1 = "열심히 노력 하마";
    public static String hippoType2 = "내가 리더 하마";
    public static String hippoType3 = "하마 냄새가 나는 하마";
    public static String hippoType4 = "세상 시원시원한 하마";
    public static String hippoType5 = "외유내강 하마";
    public static String hippoType6 = "스마트 하마";
    public static String hippoType7 = "스윗하마";
    public static String hippoType8 = "센치 하마";

    // 기본
    public static String basicHippoURL = "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/f0fcb249-2cef-4539-a9ff-27dc171194d6%EA%B8%B0%EB%B3%B8%20%ED%95%98%EB%A7%88.PNG";

    private static String[] effortHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/effortsurvey.png",
            "무슨 일이 있어도 그 열정이 끊이지 않는 당신은 노력파 하마\n" +
                    "안되는 일이 있다한들 무한한 계획으로 무장한 당신을 막을 수 없다"

    };

    private static String[] leaderHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/leadersurvey.png",
            "하나부터 열끝까지 모든 일들을 능숙하게 처리하는 당신은 리더십 하마\n" +
                    "이성적인 선구안으로 모두를 올바른 길로 인도하는 당신만이 우리의 대장입니다."
    };

    private static String[] smellHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smellsurvey.png",
            "다른 이들의 마음을 아무렇지않게 알아채는 당신은 누구보다 하마 냄새나는 하마!\n" +
                    "당신의 따듯하지만 확실한 배려에 언제나 주변에 하마가 끊이질않는다"
    };

    private static String[] coolHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/coolsurvey.png",
            "큰 걱정없이 언제나 모든일을 해결하는 당신은 세상 시원시원한 하마\n" +
                    "당신이 곁에만 있다면 언제나 답을 찾아줄 것을 알기에 도움이 필요한 이들의 구세주!!"
    };

    private static String[] travelerHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/travelersurvey.png",
            "나만의 시간을 가지며 내 생각을 정리하는 시간이 필요한 센치하마\n" +
                    "복잡한 일을 비교적 잘 처리하는 능력을 가지고 있어요.\n" +
                    "봉사활동을 했을 땐 혼자서 조용히 환경 미화를 하는 편입니다."
    };

    private static String[] smartHippoUrl1 = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/smartsurvey.png",
            "얌전하지만 자신의 일에 언제나 완벽함을 추구하는 당신은 스마트 하마\n" +
                    "다른 이들의 의견을 존중하더라도 아니라고 할 때는 아니라고 말 할 수 있는 당신의 \n" +
                    "자신감은 평소의 공부에 기반할 것입니다."
    };

    private static String[] sweetHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/sweetsurvey.png",
            "남몰래 상담을 요청해야 한다면 당신 스윗 하마 밖에 없습니다. \n" +
                    "입이 무겁고 마음을 잘 헤아리는 당신의 말에 위로 받는 이들 덕에 \n" +
                    "당신이 내는 소문은 없어도 당신에 대한 미담만은 잔뜩일겁니다."
    };

    private static String[] sentiHippoUrl = {
            "https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/sentisurvey.png",
            "나만의 시간을 가지며 내 생각을 정리하는 시간이 필요한 나그네하마\n" +
                    "복잡한 일을 비교적 잘 처리하는 능력을 가지고 있어요.\n" +
                    "봉사활동을 했을 땐 혼자서 조용히 환경 미화를 하는 편입니다."
    };

    public static String resultImage(String hippoName)
    {
        if(hippoName.equals(hippoType1))
        {
            return effortHippoUrl[0];
        }
        else if(hippoName.equals(hippoType2))
        {
            return leaderHippoUrl[0];
        }
        else if(hippoName.equals(hippoType3))
        {
            return smellHippoUrl[0];
        }
        else if(hippoName.equals(hippoType4))
        {
            return coolHippoUrl[0];
        }
        else if(hippoName.equals(hippoType5))
        {
            return travelerHippoUrl[0];
        }
        else if(hippoName.equals(hippoType6))
        {
            return smartHippoUrl1[0];
        }
        else if(hippoName.equals(hippoType7))
        {
            return sweetHippoUrl[0];
        }
        else if(hippoName.equals(hippoType8))
        {
            return sentiHippoUrl[0];
        }
        return basicHippoURL;
    }

    public static String resultText(String hippoName)
    {
        if(hippoName.equals(hippoType1))
        {
            return effortHippoUrl[1];
        }
        else if(hippoName.equals(hippoType2))
        {
            return leaderHippoUrl[1];
        }
        else if(hippoName.equals(hippoType3))
        {
            return smellHippoUrl[1];
        }
        else if(hippoName.equals(hippoType4))
        {
            return coolHippoUrl[1];
        }
        else if(hippoName.equals(hippoType5))
        {
            return travelerHippoUrl[1];
        }
        else if(hippoName.equals(hippoType6))
        {
            return smartHippoUrl1[1];
        }
        else if(hippoName.equals(hippoType7))
        {
            return sweetHippoUrl[1];
        }
        else if(hippoName.equals(hippoType8))
        {
            return sentiHippoUrl[1];
        }
        return basicHippoURL;
    }
}
