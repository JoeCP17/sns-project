package com.example.snsproject.domain.member.entity;

import com.example.snsproject.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 도메인은")
class MemberTest {

  @Test
  void 닉네임을_변경할_수_있다() {
    Member member = MemberFixtureFactory.defualtMember();
    final String expected = "Joe";

    member.changeNickname(expected);

    Assertions.assertEquals(member.getNickName(), expected);
  }

  @Test
  void 만약_열글자가_넘어간다면_예외가_발생한다() {
    Member member = MemberFixtureFactory.defualtMember();
    final String expected = "JoeisVeryGoodPerson";

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> member.changeNickname(expected)
    );
  }

}
