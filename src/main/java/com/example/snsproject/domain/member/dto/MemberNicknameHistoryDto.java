package com.example.snsproject.domain.member.dto;

import java.time.LocalDateTime;

public record MemberNicknameHistoryDto(
    Long id,
    Long memberId,
    String nickName,
    LocalDateTime createdAt
) {

}
