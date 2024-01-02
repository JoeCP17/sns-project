package com.example.snsproject.domain.post;

import com.example.snsproject.domain.post.entity.Post;
import com.example.snsproject.domain.post.repository.PostRepository;
import com.example.snsproject.util.PostFixtureFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class PostBulkInsertTest {

  @Autowired
  private PostRepository postRepository;

  @Test
  void bulkInsert() {
    EasyRandom easyRandom = PostFixtureFactory.getPost(4L,
        LocalDate.of(1970, 1, 1),
        LocalDate.of(2022, 2, 1)
    );

    StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    List<Post> posts = IntStream.range(0, 20000 * 100)
        .parallel()
        .mapToObj(i -> easyRandom.nextObject(Post.class))
        .toList();

    stopWatch.stop();
    System.out.println("객체 생성시간 : " + stopWatch.getTotalTimeSeconds());

    stopWatch.start();
    postRepository.bulkInsert(posts);
    stopWatch.stop();

    System.out.println("쿼리 실행시간 : " + stopWatch.getTotalTimeSeconds());
  }

}
