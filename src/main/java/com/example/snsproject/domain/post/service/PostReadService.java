package com.example.snsproject.domain.post.service;

import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.entity.Post;
import com.example.snsproject.domain.post.repository.PostRepository;
import com.example.snsproject.util.CusorRequest;
import com.example.snsproject.util.PageCursor;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  public Page<Post> getPosts(final Long memberId, final Pageable pageable) {
    return postRepository.findAllByMemberId(memberId, pageable);
  }

  @Transactional(readOnly = true)
  public PageCursor<Post> getCursorPosts(final Long memberId, final CusorRequest cusorRequest) {
    List<Post> posts = findAllBy(memberId, cusorRequest);
    long nextIdx = posts
        .stream()
        .mapToLong(Post::getId).min()
        .orElse(CusorRequest.NONE_KEY);

    return new PageCursor<>(cusorRequest.next(nextIdx), posts);
  }


  private List<Post> findAllBy(final Long memberId, final CusorRequest cusorRequest) {
    if (cusorRequest.hasKey()) {
      return postRepository.findAllByMemberIdAndIdDescAboutCusor(cusorRequest.key(), memberId,
          cusorRequest.size());
    } else {
      return postRepository.findAllByMemberIdAndIdDescAboutCusor(memberId, cusorRequest.size());
    }
  }

}
