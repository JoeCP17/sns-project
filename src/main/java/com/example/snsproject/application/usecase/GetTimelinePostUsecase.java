package com.example.snsproject.application.usecase;

import com.example.snsproject.domain.follow.entity.Follow;
import com.example.snsproject.domain.follow.service.FollowReadService;
import com.example.snsproject.domain.post.entity.Post;
import com.example.snsproject.domain.post.service.PostReadService;
import com.example.snsproject.util.CursorRequest;
import com.example.snsproject.util.PageCursor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetTimelinePostUsecase {
  private final FollowReadService followReadService;
  private final PostReadService postReadService;

  public GetTimelinePostUsecase(
      final FollowReadService followReadService,
      final PostReadService postReadService
  ) {
    this.followReadService = followReadService;
    this.postReadService = postReadService;
  }

  public PageCursor<Post> execute(final Long memberId, final CursorRequest cursorRequest) {
    /**
     * 1. Member Id로 Follow 조회를 발생시킨다.
     * 2. id 결과로 게시물을 조회한다.
     */
    List<Follow> followings = followReadService.getFollowings(memberId);
    List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
    return postReadService.getCursorPostsWithMemberIds(followingMemberIds, cursorRequest);
  }
}
