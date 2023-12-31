package com.example.snsproject.domain.member.entity;

import com.example.snsproject.domain.member.dto.MemberNicknameHistoryDto;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberNicknameHistory {

  private final Long id;

  private final Long memberId;

  private final String nickName;

  private final LocalDateTime createdAt;

  @Builder
  public MemberNicknameHistory(
      final Long id,
      final Long memberId,
      final String nickName,
      final LocalDateTime createdAt
  ) {
    this.id = id;
    this.memberId = Objects.requireNonNull(memberId);
    this.nickName = Objects.requireNonNull(nickName);
    this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
  }

  public MemberNicknameHistoryDto toDto() {
    return new MemberNicknameHistoryDto(
        id,
        memberId,
        nickName,
        createdAt
    );
  }

}
