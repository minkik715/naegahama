//package com.hanghae.naegahama.alarm;
//
//import com.hanghae.naegahama.domain.Timestamped;
//import com.hanghae.naegahama.domain.User;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//    @Entity
//    @Getter
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    @AllArgsConstructor(access = AccessLevel.PRIVATE)
//    public class Message extends Timestamped {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "message_id")
//        private Long id;
//
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "receiver_id")
//        private User receiver;
//
//        @Lob
//        private String message;
//
//        @Enumerated(EnumType.STRING)
//        private ReadingStatus readingStatus;
//
//        public Message (User receiver, String message) {
//            this.receiver = receiver;
//            this.message = message;
//            this.readingStatus = ReadingStatus.N;
//        }
//
//        public void changeReadingStatus(ReadingStatus readingStatus) {
//            this.readingStatus = readingStatus;
//        }
//
//    }
