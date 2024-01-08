package com.example.snsproject.domain.post.service;

import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.entity.Post;
import com.example.snsproject.domain.post.repository.PostRepository;
import com.example.snsproject.util.CursorRequest;
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
  public PageCursor<Post> getCursorPostsWithMemberId(final Long memberId, final CursorRequest cursorRequest) {
    List<Post> posts = findAllBy(memberId, cursorRequest);
    long nextIdx = getNextIdx(posts);

    return new PageCursor<>(cursorRequest.next(nextIdx), posts);
  }

  @Transactional(readOnly = true)
  public PageCursor<Post> getCursorPostsWithMemberIds(final List<Long> memberIds, final CursorRequest cursorRequest) {
    List<Post> posts = findAllBy(memberIds, cursorRequest);
    long nextIdx = getNextIdx(posts);

    return new PageCursor<>(cursorRequest.next(nextIdx), posts);
  }

  private static long getNextIdx(List<Post> posts) {
    return posts
        .stream()
        .mapToLong(Post::getId).min()
        .orElse(CursorRequest.NONE_KEY);
  }


  private List<Post> findAllBy(final Long memberId, final CursorRequest cursorRequest) {
    if (cursorRequest.hasKey()) {
      return postRepository.findAllByMemberIdAndIdDescAboutCusor(cursorRequest.key(), memberId,
          cursorRequest.size());
    } else {
      return postRepository.findAllByMemberIdAndIdDescAboutCusor(memberId, cursorRequest.size());
    }
  }

  private List<Post> findAllBy(final List<Long> memberId, final CursorRequest cursorRequest) {
    if (cursorRequest.hasKey()) {
      return postRepository.findAllByMemberIdsAndIdDescAboutCusor(cursorRequest.key(), memberId,
          cursorRequest.size());
    } else {
      return postRepository.findAllByMemberIdsAndIdDescAboutCusor(memberId, cursorRequest.size());
    }
  }

}
