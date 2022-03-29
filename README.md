# 내가하마!(naegahama!)
![logo_final](https://user-images.githubusercontent.com/97504973/160319283-2c994fce-9f94-46e1-b3c3-d8df6b1b5d4b.svg)
<br>
<br>
<br>
# 팀원 정보
![팀원](https://user-images.githubusercontent.com/97504973/160319606-2546e616-a20a-43ed-8a24-4144a927301d.png)
<br>
<br>
# 1. 프로젝트 개요(Project).🎵
<br>
### 프로젝트 소개(Introduction).
 <br>
먹방, 여행브이로그, 게임영상, 전자기기 리뷰 등<br>
유투버들의 컨텐츠에서 대리만족을 찾고 계시나요?!<br>
저희 내가HAMA 서비스는 유투버들의 대리만족<br>
컨텐츠들과 달리 개인이 대리만족하고싶은 것들을<br>
공개적으로 요청하고 모든 유저가 블로거이자<br>
스트리머가 되어서 답변으로 컨텐츠를 만들어가는 서비스입니다!<br>
내가 원하는 부분을 간접경험으로 대리만족하고<br>
다른 분들의 요청에 답변을해주면서 내가 경험한것을<br>
자랑도하고 타인을 행복하게하는 보람도 얻어가세요 :)

### 프로젝트 기간(Schedule).

2022년 2월 25일 ~ 2022년 4월 8일


# 2. 개발환경( Development).❤️

### 와이어 프레임(Wireframe)
피그마 링크 : https://www.figma.com/file/5664OUWKkhs03kyxIg7S1l/%EC%9D%B4%EA%B1%B4%EB%82%B4%EA%B0%80HAMA?node-id=0%3A1

## API 설계(API Table)

노션 링크 : https://www.notion.so/c2c92742873b49a99e76d13c2468fc24?v=67e6eec90b79480cb5729d395656f302



# 3. 기술 스택(Tech Stack).🙅🏻‍♂️

## 핵심기능(Function).

- 로그인, 회원가입
    - JWT를 이용하여 로그인과 회원가입을 구현하였습니다.
    - 아이디는 3글자 이상 10글자 숫자,영문자 소/대문자로 구성해야합니다.
    - 비밀번호는 4글자 이상으로 구성해야 합니다.
    - 유저프로필 등록할 수 있습니다.
    - 닉네임을 이미 사용 중이면 회원가입이 불가능합니다.
- 메인 페이지
    - 전체 게시글을 조회 합니다.
    - 마이페이지로 이동할 수 있습니다.
    - 로그아웃을 구현했습니다.
    - 게시글을 눌러 상세페이지로 이동할 수 있습니다.
    - 등록한 이미지목록이 보이도록 구현했습니다.
    - 페이지 우측 하단에 버튼을 눌러 게시글 작성 페이지로 이동합니다.
- 마이페이지.
    - 본인이 작성한 글만 조회 합니다.
    - 로그아웃을 구현했습니다.
    - 게시글을 눌러 상세페이지로 이동할 수 있습니다.
    - 페이지 우측 하단에 버튼을 눌러 게시글 작성 페이지로 이동합니다.
    - 등록한 이미지목록이 보이도록 구현했습니다.
- 상세페이지(CRUD)
    - 로그아웃을 구현했습니다.
    - 마이페이지로 이동할 수 있습니다.
    - 페이지 우측 하단에 버튼을 눌러 게시글 작성 페이지로 이동합니다.
    - JWT를 이용하여 삭제 기능을 구현하였습니다. 삭제 후 메인페이지로 이동합니다.
    - 본인 글 수정버튼을 눌러 수정페이지로 이동합니다.
    - 댓글 작성 기능을 구현하였습니다.
    - 댓글 삭제 기능을 구현하였습니다.
- 수정페이지
    - 로그아웃을 구현했습니다.
    - 게시글을 눌러 상세페이지로 이동할 수 있습니다.
    - 페이지 우측 하단에 버튼을 눌러 게시글 작성 페이지로 이동합니다.
    - JWT를 이용하여 본인 글 수정버튼을 눌러 수정페이지로 이동합니다.
- 글작성 페이지
    - 로그아웃을 구현했습니다.
    - 마이페이지로 이동할 수 있습니다.
    - 이모티콘기능을 구현했습니다.
    - 태그기능을 구현했습니다.
    - 게시글을 공개 / 비공배(마이페이지)로 체크하는 기능을 구현했습니다.
    - 작성기능을 구현했습니다.  작성후 메인페이지로 이동합니다.

## 개발도구(Tools).

![기술](https://user-images.githubusercontent.com/97504973/154630912-63128979-7275-444f-af34-583de69a407c.png)

# 4. 데모영상 및 개발노션.♣️

Demo link : https://youtu.be/ey0leGmB97Y

Notion link : www.notion.so/Dayily-Diary-e3d8f48497bf4eb28d533bdbbdd2704f

# 5. 트러블 슛팅(Trouble Shooting).🚶🏻‍♂️

1. CORS 정책으로 인한 접속문제.
- (1) 에러 내용
    
    ![https://user-images.githubusercontent.com/87135478/145666395-7f840620-48a8-43a9-b371-ca1b0a26fee7.png](https://user-images.githubusercontent.com/87135478/145666395-7f840620-48a8-43a9-b371-ca1b0a26fee7.png)
    
    (2) 해결
    
    [//WebSecureConfig.java](https://websecureconfig.java/) 파일 내, CORS 관련 설정 추가
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
    .cors()
    .and()
    .csrf()
    .disable();
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    
    ```
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.addExposedHeader("Authorization");
    configuration.setAllowCredentials(true); // 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있도록 함
    configuration.addAllowedOriginPattern("*");
    
    source.registerCorsConfiguration("/**", configuration);
    return source;
    }


2. 완벽하지 API 설계**

3. Entity에 ImageUrl List 추가하는 문제**
    - 바로 삽입할 수 가 없어서 ImageUrl table을 따로 만들고 Diary와 연관관계를 맺어 controller와 service에서 다시 request값을 리스트화 시켜 저장하는 방법을 사용하였다.

4. 용량이 큰 이미지 파일은 업로드 되지 않은 문제.**

# 6. 개인회고록(자유롭게 작성)💬.
