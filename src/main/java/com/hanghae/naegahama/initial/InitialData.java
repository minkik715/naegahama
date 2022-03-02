package com.hanghae.naegahama.initial;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.repository.PostRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialData implements ApplicationRunner {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User("aaa@gmail.com", "aaa", passwordEncoder.encode("123456"), 1000));
        userList.add(new User("bbb@gmail.com", "bbb", passwordEncoder.encode("123456"), 2000));
        userList.add(new User("ccc@gmail.com", "ccc", passwordEncoder.encode("123456"), 3000));
        userList.add(new User("ddd@gmail.com", "ddd", passwordEncoder.encode("123456"), 4000));
        userList.add(new User("eee@gmail.com", "eee", passwordEncoder.encode("123456"), 5000));
        userList.add(new User("fff@gmail.com", "fff", passwordEncoder.encode("123456"), 6000));
        userList.add(new User("ggg@gmail.com", "ggg", passwordEncoder.encode("123456"), 7000));
        userRepository.saveAll(userList);

        List<Post> PostList = new ArrayList<>();
        PostList.add(new Post("라면먹어주실분", "신라면으로 야무지게 먹어주세요!", "cook","하", userList.get(0)));
        PostList.add(new Post("치킨먹어주실분", "황금올리브로 야무지게 먹어주세요!", "cook","중", userList.get(0)));
        PostList.add(new Post("자전거대신타주실분", "한강 공원 한바퀴만 돌아주세요!", "health","상", userList.get(1)));
        PostList.add(new Post("푸시업해주실분", "저대신 푸시업 20개만 해주세요!", "health","하", userList.get(1)));
        PostList.add(new Post("코딩해주실분!", "저대신 코딩좀 해주세요..", "knowledge","중", userList.get(2)));
        PostList.add(new Post("숙제풀어주실분", "숙제좀 저대신 해주세요!", "knowledge","상", userList.get(2)));
        PostList.add(new Post("레고 만들어주실분", "거북선 만들기 어렵네요", "create","하", userList.get(3)));
        PostList.add(new Post("음 음", "음 음", "create","중", userList.get(3)));
        PostList.add(new Post("한강가주실분!", "한강가주실분", "visit","상", userList.get(4)));
        PostList.add(new Post("제주도 바다 보여주세요!", "제주도 바다 보여주세요!", "visit","하", userList.get(4)));
        PostList.add(new Post("개발자 브이로그 보고싶어요!", "개발자 브이로그 보고싶어요", "job","중", userList.get(5)));
        PostList.add(new Post("백수브이로그 궁금해요", "백수브이로그 궁금해요", "job","상", userList.get(5)));
        PostList.add(new Post("고양이사진 보여주세요", "고양이사진 보여주세요", "pet","하", userList.get(6)));
        PostList.add(new Post("강아지사진 보여주세요", "신라면으로 야무지게 먹어주세요!", "pet","중", userList.get(6)));
        PostList.add(new Post("오늘 패션 짜주세요!", "오늘 패션 짜주세요!", "fashion","상", userList.get(5)));
        PostList.add(new Post("이 옷 있으신분 착용샷 보여주세요!", "이 옷 있으신분 착용샷 보여주세요!", "fashion","하", userList.get(5)));
        PostList.add(new Post("cosult1", "cosult1content", "consult","중", userList.get(4)));
        PostList.add(new Post("cosult2", "cosult2content", "consult","상", userList.get(4)));
        PostList.add(new Post("맥북 신상 후기", "맥북 신상 후기", "device","하", userList.get(3)));
        PostList.add(new Post("세탁히 신상 후기", "세탁히 신상 후기", "device","중", userList.get(3)));
        PostList.add(new Post("life1", "life1content", "life","상", userList.get(2)));
        PostList.add(new Post("life2", "life2content", "life","하", userList.get(2)));
        PostList.add(new Post("etc1", "etc1content", "etc","중", userList.get(1)));
        PostList.add(new Post("etc2", "etc1content", "etc","상", userList.get(1)));
        postRepository.saveAll(PostList);



    }

}