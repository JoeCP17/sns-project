package com.example.snsproject.util;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import com.example.snsproject.domain.post.entity.Post;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class PostFixtureFactory {

  public static EasyRandom getPost(final Long memberId, final LocalDate firstDate, LocalDate endDate) {

    Predicate<Field> idPredicate = named("id")
        .and(ofType(Long.class))
        .and(inClass(Post.class)
        );

    Predicate<Field> memberIdPredicate = named("memberId")
        .and(ofType(Long.class))
        .and(inClass(Post.class)
        );

    EasyRandomParameters param = new EasyRandomParameters()
        .excludeField(idPredicate)
        .dateRange(firstDate, endDate)
        .randomize(memberIdPredicate, () -> memberId);

    return new EasyRandom(param);
  }

}
