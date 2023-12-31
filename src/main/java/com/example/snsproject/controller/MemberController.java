package com.example.snsproject.controller;

import com.example.snsproject.domain.member.dto.MemberDto;
import com.example.snsproject.domain.member.dto.MemberNicknameHistoryDto;
import com.example.snsproject.domain.member.dto.RegisterMemberCommand;
import com.example.snsproject.domain.member.service.MemberReadService;
import com.example.snsproject.domain.member.service.MemberWriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberWriteService memberWriteService;

  private final MemberReadService memberReadService;

  @PostMapping("/members")
  public void register(@RequestBody final RegisterMemberCommand command) {
    memberWriteService.register(command);
  }

  @GetMapping("/members/{id}")
  public MemberDto getMember(@PathVariable("id") final Long id) {
    return memberReadService.getMember(id);
  }

  @PostMapping("{id}/nickname")
  public MemberDto changeNickname(@PathVariable("id") final Long id, @RequestBody final String nickName) {
    memberWriteService.changeNickname(id, nickName);
    return memberReadService.getMember(id);
  }

  @GetMapping("/{memberId}/nickname-histories")
  public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable final Long memberId) {
    return memberReadService.getMemberNicknameHistory(memberId);
  }

}
