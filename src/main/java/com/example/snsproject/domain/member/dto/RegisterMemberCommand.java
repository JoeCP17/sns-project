package com.example.snsproject.domain.member.dto;

import com.example.snsproject.domain.member.entity.Member;
import java.time.LocalDate;

public record RegisterMemberCommand(
    String email,
    String nickName,
    LocalDate birthDay
) {

  public Member toEntity() {
    return Member.builder()
        .email(email())
        .nickName(nickName())
        .birthDay(birthDay())
        .build();
  }

}
