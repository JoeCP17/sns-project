package com.example.snsproject.domain.member.service;

import com.example.snsproject.domain.member.dto.MemberDto;
import com.example.snsproject.domain.member.dto.MemberNicknameHistoryDto;
import com.example.snsproject.domain.member.entity.Member;
import com.example.snsproject.domain.member.entity.MemberNicknameHistory;
import com.example.snsproject.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.snsproject.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

  private final MemberRepository memberRepository;

  private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

  public MemberDto getMember(final Long id) {
    Member member = memberRepository.findById(id).orElseThrow();
    return member.toDto();
  }

  public List<MemberNicknameHistoryDto> getMemberNicknameHistory(final Long id) {
    List<MemberNicknameHistory> histories = memberNicknameHistoryRepository.findAllByMemberId(id);

    return histories.stream()
        .map(MemberNicknameHistory::toDto)
        .collect(Collectors.toList());
  }

}
