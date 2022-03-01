
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.message.MessageRequestDto;
import com.hanghae.naegahama.handler.ex.RoomNotFoundException;
import com.hanghae.naegahama.handler.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.MessageRepository;
import com.hanghae.naegahama.repository.RoomRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;


    public void messageResolver(MessageRequestDto messageRequestDto, String token) {
        Long userId = Long.parseLong(jwtAuthenticationProvider.getuserId(token));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("해당 유저는 존재하지 않습니다.")
        );
        Room room = roomRepository.findById(messageRequestDto.getRoomId()).orElseThrow(
                () -> new RoomNotFoundException("해당 방은 존재하지 않습니다.")
        );
        String dateResult = getTime();
        Message message = new Message(messageRequestDto,user,room,dateResult);


        sendMessage(message);
        messageRepository.save(message);

    }

    private void sendMessage(Message message) {
        if(message.getMessageType().equals(MessageType.ENTER)){
            for (UserEnterRoom userEnterRoom : message.getUser().getUserEnterRoomList()) {
                if(userEnterRoom.getUser().getId().equals(message.getUser().getId())){
                    if(userEnterRoom.getRoomUserStatus() == RoomUserStatus.ENTER){
                        message.setMessage(message.getUser().getNickName()+"님이 입장하셨습니다.");
                        userEnterRoom.setRoomUserStatus(RoomUserStatus.CHAT);
                    }
                }

            }

        }
    }

    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        return dateResult;
    }
}

