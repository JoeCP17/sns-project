package com.example.snsproject.util;

import com.example.snsproject.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {

  public static Member defualtMember() {
    EasyRandomParameters param = new EasyRandomParameters();
    return new EasyRandom(param).nextObject(Member.class);
  }

  public static Member create(final long seed) {
    EasyRandomParameters param = new EasyRandomParameters();
    param.setSeed(seed);
    return new EasyRandom(param).nextObject(Member.class);
  }

}
