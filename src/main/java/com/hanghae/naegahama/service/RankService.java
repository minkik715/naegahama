package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Rank;
import com.hanghae.naegahama.domain.RankStatus;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.rank.RankResponseDto;
import com.hanghae.naegahama.repository.RankRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    //이전 top 5에 존재하지 않을 경우 무조건 Status는 Up
    //이전 top 5에 존재하고 같은 자리일 경우 Status는 -
    //이전 top 5에 존재하고 더 높은 자리일 경우 status는 up
    //이전 top 5에 존재하고 더 낮은 자리일 경우 status는 down
    public List<RankResponseDto> getTop5Rank() {
        //과거 5개 가져오기
        List<Rank> previousTop5Rank = rankRepository.findAllByOrderByRankAsc();
        List<User> previousUserRankList = new ArrayList<>();
        List<User> top5ByOrderByPointDesc = userRepository.findTop5ByOrderByPointDesc();
        List<RankResponseDto> rankResponseDtoList = new ArrayList<>();
        if (previousTop5Rank == null)
        {
            for (int i = 0; i < top5ByOrderByPointDesc.size(); i++) {
                checkRank(top5ByOrderByPointDesc, rankResponseDtoList, i, RankStatus.up, true);
            }
            return rankResponseDtoList;
        }
        for (Rank rank : previousTop5Rank)
        {
            previousUserRankList.add(rank.getUser());
        }
        //과거 5개 삭제
        rankRepository.deleteAll();
        //현재 랭크 5위 까지 가져오기


        //랭크 변동 확인하기
        for (int i = 0; i < top5ByOrderByPointDesc.size(); i++) {
            // 과거에 존재하지 않았다면
            if (!previousUserRankList.contains(top5ByOrderByPointDesc.get(i))) {
                checkRank(top5ByOrderByPointDesc, rankResponseDtoList, i, RankStatus.up,true);
            } else {
                //자리가 같다면 PERSIST
                if (previousUserRankList.indexOf(top5ByOrderByPointDesc.get(i)) == i) {
                    checkRank(top5ByOrderByPointDesc, rankResponseDtoList, i, RankStatus.stay,false);
                }
                //과거의 번호가 더 높을떄 -> 순위가 올라 간것
                else if (previousUserRankList.indexOf(top5ByOrderByPointDesc.get(i)) >= i) {
                    checkRank(top5ByOrderByPointDesc, rankResponseDtoList, i, RankStatus.up,true);
                }
                //과거의 번호가 더 아래에 있을떄 -> 순위가 내려간것
                else {
                    checkRank(top5ByOrderByPointDesc, rankResponseDtoList, i, RankStatus.down,true);
                }
            }
        }


        return rankResponseDtoList;
    }

    private void checkRank(List<User> top5ByOrderByPointDesc, List<RankResponseDto> rankResponseDtoList, int i, RankStatus down, Boolean is_changed) {
        RankResponseDto rankResponseDto = new RankResponseDto(top5ByOrderByPointDesc.get(i), i + 1, down, is_changed);
        rankRepository.save(new Rank(top5ByOrderByPointDesc.get(i), i+1));
        rankResponseDtoList.add(rankResponseDto);
    }

}
