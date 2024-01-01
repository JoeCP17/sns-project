package com.example.snsproject.domain.post.dto;

public record PostCommand(
    Long memberId,
    String contents
) {

}
