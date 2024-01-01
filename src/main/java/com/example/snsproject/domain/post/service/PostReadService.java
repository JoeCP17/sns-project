package com.example.snsproject.domain.post.service;

import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.repository.PostRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostReadService {

  private final PostRepository postRepository;

  public PostReadService(final PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<DailyPostCount> getDailyPostCount(final DailyPostCountRequest dailyPostCountRequest) {
    /**
     * 반환 값 -> 리스트 [ 작성일자, 작성회원, 작성 게시물 개수 ]
     * select createDate, memberId, count(id)
     * from Post where memberId = ? and createdAt between firstDate and endDate
     * group by createDate memberId;
     */
    return postRepository.groupByCreatedDate(dailyPostCountRequest);
  }

}
