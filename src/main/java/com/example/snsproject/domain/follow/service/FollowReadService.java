package com.example.snsproject.domain.follow.service;

import com.example.snsproject.domain.follow.entity.Follow;
import com.example.snsproject.domain.follow.repository.FollowRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowReadService {

  private final FollowRepository followRepository;

  public FollowReadService(final FollowRepository followRepository) {
    this.followRepository = followRepository;
  }

  @Transactional(readOnly = true)
  public List<Follow> getFollowings(final Long memberId) {
    return followRepository.findAllByFromMemberId(memberId);
  }

}
