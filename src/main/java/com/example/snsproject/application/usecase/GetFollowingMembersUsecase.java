package com.example.snsproject.application.usecase;

import com.example.snsproject.domain.follow.entity.Follow;
import com.example.snsproject.domain.follow.service.FollowReadService;
import com.example.snsproject.domain.member.dto.MemberDto;
import com.example.snsproject.domain.member.service.MemberReadService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetFollowingMembersUsecase {

  private final MemberReadService memberReadService;

  private final FollowReadService followReadService;

  public GetFollowingMembersUsecase(
      final MemberReadService memberReadService,
      final FollowReadService followReadService
  ) {
    this.memberReadService = memberReadService;
    this.followReadService = followReadService;
  }

  public List<MemberDto> execute(final Long memberId) {
    /**
     * 1. fromMemberId = memberId -> Follow list
     * 2. 1번을 순회하면서 회원정보를 찾으면 된다.
     */
    List<Follow> followings = followReadService.getFollowings(memberId);
    List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
    return memberReadService.getMembers(followingMemberIds);
  }
}
