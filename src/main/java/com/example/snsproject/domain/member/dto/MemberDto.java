package com.example.snsproject.domain.member.dto;

import java.time.LocalDate;

public record MemberDto(
    Long id,
    String nickName,
    String email,
    LocalDate birthDay
) {

}
