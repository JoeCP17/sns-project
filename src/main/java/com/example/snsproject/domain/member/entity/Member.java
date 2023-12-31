package com.example.snsproject.domain.member.entity;

import com.example.snsproject.domain.member.dto.MemberDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
  private final Long id;
  private String nickName;
  private final String email;
  private final LocalDate birthDay;

  private final LocalDateTime createdAt;

  private static long MAX_LENGTH_NICKNAME = 10;

  @Builder
  public Member(final Long id,
                final String nickName,
                final String email,
                final LocalDate birthDay,
                final LocalDateTime createdAt
  ) {
    this.id = id;
    validateNickname(nickName);
    this.nickName = Objects.requireNonNull(nickName);
    this.email = Objects.requireNonNull(email);
    this.birthDay = Objects.requireNonNull(birthDay);
    this.createdAt = createdAt == null? LocalDateTime.now() : createdAt;
  }

  public MemberDto toDto() {
    return new MemberDto(
        this.id,
        this.nickName,
        this.email,
        this.birthDay
    );
  }

  public MemberNicknameHistory toNicknameHistory() {
    return MemberNicknameHistory.builder()
        .memberId(this.id)
        .nickName(this.nickName)
        .build();
  }

  public void changeNickname(final String nickName) {
    Objects.requireNonNull(nickName);
    validateNickname(nickName);
    this.nickName = nickName;
  }

  private void validateNickname(final String nickName) {
    if (nickName.length() > MAX_LENGTH_NICKNAME) {
      throw new IllegalArgumentException("닉네임은 10자를 넘길 수 없습니다.");
    }
  }
}
