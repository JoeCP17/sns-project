package com.example.snsproject.domain.member.service;

import com.example.snsproject.domain.member.dto.MemberDto;
import com.example.snsproject.domain.member.dto.MemberNicknameHistoryDto;
import com.example.snsproject.domain.member.entity.Member;
import com.example.snsproject.domain.member.entity.MemberNicknameHistory;
import com.example.snsproject.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.snsproject.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberReadService {

  private final MemberRepository memberRepository;

  private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

  public MemberReadService(
      final MemberRepository memberRepository,
      final MemberNicknameHistoryRepository memberNicknameHistoryRepository
  ) {
    this.memberRepository = memberRepository;
    this.memberNicknameHistoryRepository = memberNicknameHistoryRepository;
  }

  public MemberDto getMember(final Long id) {
    Member member = memberRepository.findById(id).orElseThrow();
    return member.toDto();
  }

  public List<MemberDto> getMembers(final List<Long> ids) {
    List<Member> members = memberRepository.findAllByIdIn(ids);
    return members.stream()
        .map(Member::toDto)
        .collect(Collectors.toList());
  }

  public List<MemberNicknameHistoryDto> getMemberNicknameHistory(final Long id) {
    List<MemberNicknameHistory> histories = memberNicknameHistoryRepository.findAllByMemberId(id);

    return histories.stream()
        .map(MemberNicknameHistory::toDto)
        .collect(Collectors.toList());
  }

}
