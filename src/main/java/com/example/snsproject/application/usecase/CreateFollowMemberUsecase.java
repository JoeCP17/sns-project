package com.example.snsproject.application.usecase;

import com.example.snsproject.domain.follow.service.FollowWriteService;
import com.example.snsproject.domain.member.dto.MemberDto;
import com.example.snsproject.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

@Service
public class CreateFollowMemberUsecase {

  private final MemberReadService memberReadService;

  private final FollowWriteService followWriteService;

  public CreateFollowMemberUsecase(
      final MemberReadService memberReadService,
      final FollowWriteService followWriteService
  ) {
    this.memberReadService = memberReadService;
    this.followWriteService = followWriteService;
  }

  public void excute(final Long fromMemberId, final Long toMemberId) {
    /**
     * 1. 입력받은 memberId로 회원을 조회한다.
     * 2. FollowWriteService.create()
     */
    MemberDto fromMember = memberReadService.getMember(fromMemberId);
    MemberDto toMember = memberReadService.getMember(toMemberId);

    followWriteService.create(fromMember, toMember);
  }

}
