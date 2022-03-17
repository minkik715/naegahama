//
//package com.hanghae.naegahama.initial;
//
//
//import com.hanghae.naegahama.alarm.Alarm;
//import com.hanghae.naegahama.alarm.AlarmRepository;
//import com.hanghae.naegahama.alarm.ReadingStatus;
//import com.hanghae.naegahama.alarm.Type;
//import com.hanghae.naegahama.domain.*;
//import com.hanghae.naegahama.dto.postlike.PostLikeRequestDto;
//import com.hanghae.naegahama.repository.*;
//
//import com.hanghae.naegahama.repository.AnswerRepository;
//import com.hanghae.naegahama.repository.PostRepository;
//import com.hanghae.naegahama.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//@RequiredArgsConstructor
//public class InitialData implements ApplicationRunner {
//
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//    private final AnswerRepository answerRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final PostFileRepository postFileRepository;
//    private final AnswerFileRepository answerFileRepository;
//
//    private final CommentRepository commentRepository;
//
//    private final PostLikeRepository postLikeRepository;
//    private final AlarmRepository alarmRepository;
//
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) throws Exception {
//
//
////        동영상은 파일 중에서도 사이즈가 크다 보니까 성능상의 이슈가 특히 중요하다고 느꼈다. 구현한 4가지 기능(압축, 썸네일, 크기, 재생시간 추출)의 작업 시간과 CPU 사용률을 대략적으로 살펴봤을 때, 특히 동영상 압축 작업이 가장 오래 걸리고 CPU 사용률이 높게 치솟았다. 이러한 점들 때문에 실제 서비스를 운영하는 환경에서는 동영상을 처리하는 별도의 서버를 두는 것도 고려해볼 수 있을 거 같다.
////
////그리고 ffmpeg과 같은 멀티미디어를 따로 설치해야 하기 때문에, AWS를 사용한다면 EC2에 별도로 ffmpeg을 설치해 AMI로 구성할지 등에 대한 고민도 필요할 거 같다.
////
////지난번 Animated GIF 썸네일 생성하기에 이어서 이번에 동영상 파일 처리를 위한 작업을 진행했는데 ffmpeg을 설치하는 것부터 시작해서 많은 삽질을 한 거 같다...
////
////그래도 원하는 결과물을 만들어낼 수 있어서 뿌듯했고 그 과정에서 동영상 처리를 위해 주의할 점을 계속적으로 고민해볼 수 있는 좋은 기회였다.
//
//
//        //유저만들기
//        List<User> userList = new ArrayList<>();
//        userList.add(new User("aaa@gmail.com", "aaa", passwordEncoder.encode("123456"), 1000));
//        userList.add(new User("bbb@gmail.com", "bbb", passwordEncoder.encode("123456"), 2000));
//        userList.add(new User("ccc@gmail.com", "ccc", passwordEncoder.encode("123456"), 3000));
//        userList.add(new User("ddd@gmail.com", "ddd", passwordEncoder.encode("123456"), 4000));
//        userList.add(new User("eee@gmail.com", "eee", passwordEncoder.encode("123456"), 5000));
//        userList.add(new User("fff@gmail.com", "fff", passwordEncoder.encode("123456"), 6000));
//        userList.add(new User("ggg@gmail.com", "ggg", passwordEncoder.encode("123456"), 7000));
//        userRepository.saveAll(userList);
//
//
//        //요청글 만들기
//        List<Post> PostList = new ArrayList<>();
//        PostList.add(new Post("라면먹어주실분", "신라면으로 야무지게 먹어주세요!", "cook", "하", userList.get(0), 1));
//        PostList.add(new Post("치킨먹어주실분", "황금올리브로 야무지게 먹어주세요!", "cook", "중", userList.get(0), 2));
//        PostList.add(new Post("자전거대신타주실분", "한강 공원 한바퀴만 돌아주세요!", "health", "상", userList.get(1), 3));
//        PostList.add(new Post("푸시업해주실분", "저대신 푸시업 20개만 해주세요!", "health", "하", userList.get(1), 4));
//        PostList.add(new Post("코딩해주실분!", "저대신 코딩좀 해주세요..", "knowledge", "중", userList.get(2), 1));
//        PostList.add(new Post("숙제풀어주실분", "숙제좀 저대신 해주세요!", "knowledge", "상", userList.get(2), 6));
//        PostList.add(new Post("레고 만들어주실분", "거북선 만들기 어렵네요", "create", "하", userList.get(3), 7));
//        PostList.add(new Post("음 음", "음 음", "create", "중", userList.get(3), 8));
//        PostList.add(new Post("한강가주실분!", "한강가주실분", "visit", "상", userList.get(4), 9));
//        PostList.add(new Post("제주도 바다 보여주세요!", "제주도 바다 보여주세요!", "visit", "하", userList.get(4), 10));
//        PostList.add(new Post("개발자 브이로그 보고싶어요!", "개발자 브이로그 보고싶어요", "job", "중", userList.get(5), 1));
//        PostList.add(new Post("백수브이로그 궁금해요", "백수브이로그 궁금해요", "job", "상", userList.get(5), 12));
//        PostList.add(new Post("고양이사진 보여주세요", "고양이사진 보여주세요", "pet", "하", userList.get(6), 13));
//        PostList.add(new Post("강아지사진 보여주세요", "신라면으로 야무지게 먹어주세요!", "pet", "중", userList.get(6), 14));
//        PostList.add(new Post("오늘 패션 짜주세요!", "오늘 패션 짜주세요!", "fashion", "상", userList.get(5), 15));
//        PostList.add(new Post("이 옷 있으신분 착용샷 보여주세요!", "이 옷 있으신분 착용샷 보여주세요!", "fashion", "하", userList.get(5), 16));
//        PostList.add(new Post("cosult1", "cosult1content", "consult", "중", userList.get(4), 1));
//        PostList.add(new Post("cosult2", "cosult2content", "consult", "상", userList.get(4), 5));
//        PostList.add(new Post("맥북 신상 후기", "맥북 신상 후기", "device", "하", userList.get(3), 4));
//        PostList.add(new Post("세탁히 신상 후기", "세탁히 신상 후기", "device", "중", userList.get(3), 3));
//        PostList.add(new Post("life1", "life1content", "life", "상", userList.get(2), 1));
//        PostList.add(new Post("life2", "life2content", "life", "하", userList.get(2), 7));
//        PostList.add(new Post("etc1", "etc1content", "etc", "중", userList.get(1), 9));
//        PostList.add(new Post("etc2", "etc1content", "etc", "상", userList.get(1), 1));
//        postRepository.saveAll(PostList);
//        PostList.get(0).getFileList().add(postFileRepository.save(new PostFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/7353f438-b3ad-41c7-84fd-109a6f299f1412345.jpg", PostList.get(0))));
//        PostList.get(0).getFileList().add(postFileRepository.save(new PostFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/247cf91e-ce0e-41af-b873-5cd12637193c12345.jpg", PostList.get(0))));
//
//        PostList.get(0).getFileList().add(postFileRepository.save(new PostFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/47fa85c8-f441-4ccc-a40f-5fa417acded2bandicam+2022-02-24+17-32-15-816.mp4", PostList.get(0))));
//        PostList.get(0).getFileList().add(postFileRepository.save(new PostFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/b86131c7-5979-4bce-8805-8f9ab9e28992bandicam+2022-01-18+14-47-03-954.mp4", PostList.get(0))));
//        PostList.get(0).getFileList().add(postFileRepository.save(new PostFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/8fe04b0c-dcb9-4380-b6f6-2dd3aa3a941dbandicam+2022-02-24+09-51-56-101.mp4", PostList.get(0))));
//        PostList.get(0).getFileList().add(postFileRepository.save(new PostFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/7648875c-60df-4211-a46c-0b1053fec5b3bandicam+2022-01-13+11-26-34-713.mp4", PostList.get(0))));
//        PostList.get(1).setStatus("false");
//
//
//        List<Answer> answerList = new ArrayList<>();
//        answerList.add(new Answer("먹어드립니다.", "내공 냠냠", PostList.get(1), userList.get(1)));
//        answerList.add(new Answer("한강 갔습니다.", "평점 말고 코딩 대신 해주면 안되나요?", PostList.get(0), userList.get(0)));
//        answerList.add(new Answer("라면 후기", "내공 냠냠", PostList.get(0), userList.get(2)));
//        answerRepository.saveAll(answerList);
//        answerList.add(answerRepository.save(new Answer("제가 신라면 잘먹습니다", 0, "라면중에는 신라면이 쵝오죠 제가 대신 먹어드릴게요", PostList.get(0), userList.get(3))));
//        answerList.add(answerRepository.save(new Answer("제가 너구리 잘먹습니다", 0, "라면중에는 너구리가 쵝오죠 제가 대신 먹어드릴게요", PostList.get(0), userList.get(4))));
//        answerList.add(answerRepository.save(new Answer("제가 진라면 잘먹습니다", 0, "라면중에는 진라면이 쵝오죠 제가 대신 먹어드릴게요", PostList.get(0), userList.get(5))));
//        answerList.add(answerRepository.save(new Answer("제가 안성탕면 잘먹습니다", 0, "라면중에는 안성탕면이 쵝오죠 제가 대신 먹어드릴게요", PostList.get(0), userList.get(6))));
//        answerList.add(answerRepository.save(new Answer("제가 팔도비빔면 잘먹습니다", 0, "라면중에는 팓로비빔면이 쵝오죠 제가 대신 먹어드릴게요", PostList.get(0), userList.get(2))));
//
//        answerList.get(0).getFileList().add(answerFileRepository.save(new AnswerFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/7353f438-b3ad-41c7-84fd-109a6f299f1412345.jpg", answerList.get(0))));
//        answerList.get(0).getFileList().add(answerFileRepository.save(new AnswerFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/247cf91e-ce0e-41af-b873-5cd12637193c12345.jpg", answerList.get(0))));
//        answerList.get(0).getFileList().add(answerFileRepository.save(new AnswerFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/47fa85c8-f441-4ccc-a40f-5fa417acded2bandicam+2022-02-24+17-32-15-816.mp4", answerList.get(0))));
//        answerList.get(1).getFileList().add(answerFileRepository.save(new AnswerFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/b86131c7-5979-4bce-8805-8f9ab9e28992bandicam+2022-01-18+14-47-03-954.mp4", answerList.get(1))));
//        answerList.get(2).getFileList().add(answerFileRepository.save(new AnswerFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/8fe04b0c-dcb9-4380-b6f6-2dd3aa3a941dbandicam+2022-02-24+09-51-56-101.mp4", answerList.get(2))));
//        answerList.get(3).getFileList().add(answerFileRepository.save(new AnswerFile("https://minki-bucket.s3.ap-northeast-2.amazonaws.com/static/7648875c-60df-4211-a46c-0b1053fec5b3bandicam+2022-01-13+11-26-34-713.mp4", answerList.get(3))));
//
//        List<Comment> commentList = new ArrayList<>();
//        commentList.add(commentRepository.save(new Comment("정말 맛있게 드셨네요!!!", null, answerList.get(0), userList.get(0))));
//        commentList.add(commentRepository.save(new Comment("우와 저도 먹고 싶어요", null, answerList.get(0), userList.get(0))));
//        commentList.add(commentRepository.save(new Comment("진짜 맛있겠다.", null, answerList.get(0), userList.get(0))));
//        commentList.add(commentRepository.save(new Comment("제 요청도 한번 봐주세요", null, answerList.get(0), userList.get(0))));
//        commentList.add(commentRepository.save(new Comment("대댓글1", 1L, answerList.get(0), userList.get(1))));
//        commentList.add(commentRepository.save(new Comment("대댓글2", 2L, answerList.get(0), userList.get(2))));
//        commentList.add(commentRepository.save(new Comment("대댓글3", 3L, answerList.get(0), userList.get(3))));
//        commentList.add(commentRepository.save(new Comment("대댓글4", 1L, answerList.get(0), userList.get(3))));
//        commentList.add(commentRepository.save(new Comment("대댓글5", 2L, answerList.get(0), userList.get(2))));
//        commentList.add(commentRepository.save(new Comment("대댓글6", 3L, answerList.get(0), userList.get(1))));
//
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(0), PostList.get(1))));
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(0), PostList.get(0))));
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(1), PostList.get(1))));
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(2), PostList.get(1))));
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(3), PostList.get(1))));
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(4), PostList.get(15))));
//        postLikeRepository.save(new PostLike(new PostLikeRequestDto(userList.get(5), PostList.get(15))));
//
//        List<Alarm> alarmList = new ArrayList<>();
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(1), userList.get(0).getNickName(), Type.answer, PostList.get(0).getId(), PostList.get(0).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(2), userList.get(1).getNickName(), Type.commet, PostList.get(1).getId(), PostList.get(1).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(3), userList.get(2).getNickName(), Type.likeA, PostList.get(2).getId(), PostList.get(2).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(4), userList.get(3).getNickName(), Type.child, PostList.get(3).getId(), PostList.get(3).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(5), userList.get(4).getNickName(), Type.rate, PostList.get(4).getId(), PostList.get(4).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(6), userList.get(5).getNickName(), Type.rated, PostList.get(5).getId(), PostList.get(5).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(7), userList.get(6).getNickName(), Type.level, PostList.get(0).getId(), PostList.get(0).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(8), userList.get(1).getNickName(), Type.likeP, PostList.get(1).getId(), PostList.get(1).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(4), userList.get(2).getNickName(), Type.level, PostList.get(2).getId(), PostList.get(2).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(2), userList.get(3).getNickName(), Type.commet, PostList.get(3).getId(), PostList.get(3).getTitle())));
//        alarmList.add(alarmRepository.save(new Alarm(userList.get(0), userList.get(4).getNickName(), Type.answer, PostList.get(4).getId(), PostList.get(4).getTitle())));
//
//
//    }
//
//}
