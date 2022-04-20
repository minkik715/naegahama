
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Frlaalsrl715%2Fnaegahama%2F&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

# 내가하마!(naegahama!)
<img src="https://user-images.githubusercontent.com/97504973/160697352-e59ea41b-cd20-4055-a965-027fc793e4e8.svg" height="300px" width="300px">
<br>
<br>

# 팀원 정보
<img src="https://user-images.githubusercontent.com/97504973/160319606-2546e616-a20a-43ed-8a24-4144a927301d.png" height="80%" width="80%">
<br>
<br>

# 프로젝트 개요(Project).🎵

### 프로젝트 소개(Introduction).
 <br>
먹방, 여행브이로그, 게임영상, 전자기기 리뷰 등 유투버들의 컨텐츠에서 대리만족을 찾고 계시나요?!<br>
저희 내가HAMA 서비스는 유투버들의 대리만족 컨텐츠들과 달리 개인이 대리만족하고싶은 것들을<br>
공개적으로 요청하고 모든 유저가 블로거이자 스트리머가 되어서 답변으로 컨텐츠를 만들어가는 서비스입니다!<br>
내가 원하는 부분을 간접경험으로 대리만족하고 다른 분들의 요청에 답변을해주면서 내가 경험한것을<br>
자랑도하고 타인을 행복하게하는 보람도 얻어가세요 :)

### 프로젝트 기간(Schedule).

2022년 2월 25일 ~ 2022년 4월 8일
<br>
<br>

# 개발환경( Development).❤️

### 와이어 프레임(Wireframe)
피그마 링크 : https://www.figma.com/file/5664OUWKkhs03kyxIg7S1l/%EC%9D%B4%EA%B1%B4%EB%82%B4%EA%B0%80HAMA?node-id=0%3A1

## API 설계(API Table)
노션 링크 : https://www.notion.so/c2c92742873b49a99e76d13c2468fc24?v=67e6eec90b79480cb5729d395656f302
<br>
<br>

#  기술 스택(Tech Stack).🙅🏻‍♂️
## 🛠️ 사용 기술 및 라이브러리

### **BackEnd**

### 백엔드

- 프레임워크 : Spring
- DB : Mysql, Redis
- DB기술: JPA(Spring Data Jpa, Querydsl)
- 배포 : EC2, AWS S3, CodeDeploy, GithubAction(CI/CD),Nginx,https
- 라이브러리

| Library | Appliance |
| --- | --- |
| ffmpeg | 동영상 인코딩 |
| stomp | 소켓 통신 |
| sentry | 오류 확인 |

## 아키텍쳐(Architecture).
![최종](https://user-images.githubusercontent.com/97504973/160698040-8326028d-500c-441a-8f16-1469ec71d98f.png)
<br>
<br>


#  핵심기능(Function).

- 동영상 쇼츠 기능
- 실시간 알림 기능
- 유저들의 이용에 따른 여러가지 보상 로직
- 요청글, 답변글, 댓글 CRUD
  
# 🤔 트러블 슈팅

## Back**End**

### JPA N+1문제

먼저 이용하지 않는 연관관계 엔티티를 조회하지 않기 위해 기본적으로 모든 연관관계의 **fetchType을 Lazy로 설정**해 두었습니다.

원인: 
JPQL은 **DB테이블이 아니라 엔티티를 대상으로 쿼리를 검색**하기 때문에 join을 하더라도 대상 엔티티의 값만 가져와 영속화하게 됩니다. 즉 따라서 **하위 엔티티를 조회할때마다 새로운 select문**이 생겨 N+1문제가 발생하게 됩니다.

해결책 :
로직에서 이용되는 **하위 엔티티들을 fetch join을 통해 하나의 select문안에서 가져와 영속화**시켜 더 이상 select 문이 나가지 않게 했습니다.
하지만 사용되는 하위엔티티가 **Collection이고 2개 이상이거나 페이징이 필요할 경우** 여러 문제점이 있어 사용하지 못하였습니다.
이 문제는 **batch_fetch_size**를 주어 hibernate에 size만큼 where에 쓰일 데이터를 모아둔후 일정batch_fetch_size에 다다르면 **in절을 통해 한꺼번에 데이터를 가져오게 하여** 문제를 해결하였습니다.

### Redis 동시성 문제



원인:

무중단 배포를 하기 떄문에 한 EC2 안에 같은 Redis에 연결된 서버가 2개가 켜져 있었고
**두 서버의 스케쥴러가 레디스에 연결되어 유저에게 알림을 중복하여 보내는 문제**가 발생하였습니다.

해결책:

**redission의 분산 락을 이용**하여 하나의 스케쥴러에서만 알람을 보낼 수 있게 변경하여 문제를 해결하였습니다.



# 💻 기술적인 도전

## **BackEnd**

### 1. **ApplicationEventPublisher를 이용한 이벤트 프로그래밍**

공통 비지니스 로직에서 일정 조건을 충족했을때 특정 이벤트가 일어나는 로직이 존재했습니다.

예를들어 댓글을 작성하면 게시글 주인에게 알람을 보내고 댓글 작성자에게는 업적 달성을 주는 로직이 있습니다. 

즉 댓글을 작성하는 로직(**댓글 작성**)과 댓글 작성이 완료되고 나서의 로직(**알람**, **업적획득**)으로 나눌 수 있습니다.

**ApplicationEventPublisher를** 사용하기 전에는 하나의 메소드에서 위 로직들이 하나의 메소드에서 일어나고 있었습니다. 즉 **단일 책임 원칙**을 위반하여 코드간에 **강한 결합**이 일어나 **객체지향적이지 못한 문제**가 발생하였습니다.

위 문제를 **ApplicationEventPublisher와 EventListner**를 통해 코드를 이벤트 기준으로 2개로 나누어 해결하였습니다. 즉 댓글작성 트랜잭션이 끝나면 새로운 트랜잭션을 만들어 알람과 업적획득 로직을 수행게 끔 바꾸어 **강한 결합을 풀어주어 좀 더 객체지향적이고 단일 책임 원칙에 알맞은 코드**로 리팩토링하였습니다.

### 2. QureyDsl  도입

JPA를 기본으로하여 Spring Data Jpa를 이용하여 쿼리를 작성하였는데 여러 단점을 맞이하였습니다.

1. 계속 반복되는 같은 쿼리를 **재사용하지 못한다**는 단점 
2. @Query를 통해 **직접 쿼리를 작성**해야 한다는 단점 
3. **컴파일 시점에 문법 오류**를 쉽게 확인할 수 없다는 점
4. **동적쿼리를 작성**이 어렵다는 점 등 

여러가지 문제점을 직면하여 QueryDsl를 도입하여 해결하였습니다.





#  데모영상 및 개발노션.♣️

Demo link : https://youtu.be/lvdoXEwSOqY

Notion link : https://harsh-peony-7f5.notion.site/HAMA-aa12dc751645442abf62c472ebd9ffb4


#  개인회고록(자유롭게 작성)💬.



