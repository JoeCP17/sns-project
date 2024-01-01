package com.example.snsproject.controller;

import com.example.snsproject.domain.post.dto.DailyPostCount;
import com.example.snsproject.domain.post.dto.DailyPostCountRequest;
import com.example.snsproject.domain.post.dto.PostCommand;
import com.example.snsproject.domain.post.service.PostReadService;
import com.example.snsproject.domain.post.service.PostWriteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostWriteService postWriteService;
  private final PostReadService postReadService;

  public PostController(PostWriteService postWriteService, PostReadService postReadService) {
    this.postWriteService = postWriteService;
    this.postReadService = postReadService;
  }

  @GetMapping("/daily-post-counts")
  public List<DailyPostCount> getDailyPostCounts(final DailyPostCountRequest request) {
    return postReadService.getDailyPostCount(request);
  }

  @PostMapping("")
  public Long create(final PostCommand command) {
    return postWriteService.create(command);
  }

}
