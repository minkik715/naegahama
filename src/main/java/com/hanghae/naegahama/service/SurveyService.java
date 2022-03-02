//package com.hanghae.naegahama.service;
//
//import java.util.Scanner;
//
//public class SurveyService {
//
//            Scanner sc = new Scanner(System.in);
//            int count = 0;
//
//            System.out.println("탄수화물 중독 자가진단 테스트 : Y, N 으로 작성해주세요 (대소문자 구별)");
//            //문항
//            System.out.println("아침을 배불리 먹은 후 점심시간전에 배가 고프다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("밥, 빵, 과자 등 음식을 먹기 시작하면 끝이 없다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("음식을 금방먹은 후에도만족스럽지 못하고 더 먹는다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("정말 배고프지 않더라도 먹을 때가 있다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("저녁을 먹고 간식을 먹지 않으면 잠이 오지 않는다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("스트레스를 받으면 자꾸 먹고 싶어진다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("책상이나 식탁 위에 항상 과자, 초콜릿 등이 놓여있다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("오후 5시가 되면 피곤함과 배고픔을 느끼고 일이 손에 안 잡힌다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("과자, 초콜릿 등 단음식은 상상만해도 먹고 싶어진다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//            System.out.println("다이어트를 위해 식이조절을 하는데 3일도 못간다");
//            if(sc.nextLine().equals("Y")){
//                count++;
//            }
//
//
//            //결과 출력
//            if(count>=7){
//                System.out.println("중독!");
//                System.out.println("전문의 상담이 필요함");
//            }else if(4 <= count && count <= 6){
//                System.out.println("위험!");
//                System.out.println("탄수화물 섭취 줄이기 위한 식습관 개선이 필요함");
//            }else if(count<=3){
//                System.out.println("주의!");
//                System.out.println("위험한 수준은 아니지만 관리 필요");
//            }else{
//                System.out.println("정상");
//            }
//        }
