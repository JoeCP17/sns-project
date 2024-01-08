package com.example.snsproject.domain.follow.service;

import com.example.snsproject.domain.follow.entity.Follow;
import com.example.snsproject.domain.follow.repository.FollowRepository;
import com.example.snsproject.domain.member.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class FollowWriteService {

  private final FollowRepository followRepository;

  public FollowWriteService(final FollowRepository followRepository) {
    this.followRepository = followRepository;
  }

  public void create(final MemberDto fromMember, final MemberDto toMember) {
    /**
     * from, to 회원정보를 받는다.
     * from <-> to validate를 추가한다.
     */
    Assert.isTrue(!fromMember.id().equals(toMember.id()), "자기 자신을 팔로우 할 수 없습니다.");

    Follow follow = Follow.builder()
        .fromMemberId(fromMember.id())
        .toMemberId(toMember.id())
        .build();

    followRepository.save(follow);
  }

}
