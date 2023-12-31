package com.example.snsproject.domain.member.service;

import com.example.snsproject.domain.member.dto.RegisterMemberCommand;
import com.example.snsproject.domain.member.entity.Member;
import com.example.snsproject.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.snsproject.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
  private final MemberRepository memberRepository;

  private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

  public void register(RegisterMemberCommand command) {
    /**
     * 1. 목표는 회원정보를 등록한다.
     * 회원정보 ( 이메일, 닉네임, 생년월일)을 등록한다.
     * 닉네임은 10자를 넘길 수 없다.
     * memberRegisterCommand를 파라미터로 받는다.
     * val member = Member.of(memberRegisterCommand)
     * memberRepository.save()
     */
    Member member = command.toEntity();
    memberRepository.save(member);
    memberNicknameHistoryRepository.save(member.toNicknameHistory());
  }

  public void changeNickname(final Long memberId, final String nickName) {
    /**
     * 1. 회원의 이름을 변경한다.
     * 2. 변경 내역을 저장한다.
     */
    Member member = memberRepository.findById(memberId).orElseThrow();
    member.changeNickname(nickName);
    memberRepository.save(member);

    // TODO : 변경내역 히스토리를 저장한다.
    memberNicknameHistoryRepository.save(member.toNicknameHistory());
  }

}
