package com.example.snsproject.controller;

import com.example.snsproject.application.usecase.CreateFollowMemberUsecase;
import com.example.snsproject.application.usecase.GetFollowingMembersUsecase;
import com.example.snsproject.domain.member.dto.MemberDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {

  private final CreateFollowMemberUsecase createFollowMemberUsecase;

  private final GetFollowingMembersUsecase getFollowingMembersUsecase;

  public FollowController(
      final CreateFollowMemberUsecase createFollowMemberUsecase,
      final GetFollowingMembersUsecase getFollowingMembersUsecase
  ) {
    this.createFollowMemberUsecase = createFollowMemberUsecase;
    this.getFollowingMembersUsecase = getFollowingMembersUsecase;
  }

  @PostMapping("/{fromId}/{toId}")
  public void create(@PathVariable final Long fromId, @PathVariable final Long toId) {
    createFollowMemberUsecase.excute(fromId, toId);
  }

  @GetMapping("/members/{fromId}")
  public List<MemberDto> getFollowings(@PathVariable final Long fromId) {
    return getFollowingMembersUsecase.execute(fromId);
  }

}
