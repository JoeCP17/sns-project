package com.example.snsproject.controller;

import com.example.snsproject.application.usecase.GetTimelinePostUsecase;
import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.dto.PostCommand;
import com.example.snsproject.domain.post.entity.Post;
import com.example.snsproject.domain.post.service.PostReadService;
import com.example.snsproject.domain.post.service.PostWriteService;
import com.example.snsproject.util.CursorRequest;
import com.example.snsproject.util.PageCursor;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostWriteService postWriteService;
  private final PostReadService postReadService;

  private final GetTimelinePostUsecase getTimelinePostUsecase;

  public PostController(
      final PostWriteService postWriteService,
      final PostReadService postReadService,
      final GetTimelinePostUsecase getTimelinePostUsecase
  ) {
    this.postWriteService = postWriteService;
    this.postReadService = postReadService;
    this.getTimelinePostUsecase = getTimelinePostUsecase;
  }

  @GetMapping("/daily-post-counts")
  public List<DailyPostCount> getDailyPostCounts(final DailyPostCountRequest request) {
    return postReadService.getDailyPostCount(request);
  }

  @GetMapping("/members/{memberId}")
  public Page<Post> getPosts(
      @PathVariable final Long memberId,
      Pageable pageable
  ) {
    return postReadService.getPosts(memberId, pageable);
  }

  @GetMapping("/members/{memberId}/cursor")
  public PageCursor<Post> getPostsByCursor(
      @PathVariable final Long memberId,
      CursorRequest cursorRequest
  ) {
    return postReadService.getCursorPostsWithMemberId(memberId, cursorRequest);
  }

  @GetMapping("member/{memberId}/timeline")
  public PageCursor<Post> getTimelinePosts(
      @PathVariable final Long memberId,
      CursorRequest cursorRequest
  ) {
    return getTimelinePostUsecase.execute(memberId, cursorRequest);
  }

  @PostMapping
  public Long create(final PostCommand command) {
    return postWriteService.create(command);
  }

}
