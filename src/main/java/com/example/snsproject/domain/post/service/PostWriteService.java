package com.example.snsproject.domain.post.service;

import com.example.snsproject.domain.post.dto.PostCommand;
import com.example.snsproject.domain.post.entity.Post;
import com.example.snsproject.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostWriteService {

  private final PostRepository postRepository;

  public PostWriteService(final PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Long create(final PostCommand command) {
    Post post = Post.builder()
        .memberId(command.memberId())
        .contents(command.contents())
        .build();

    return postRepository.save(post).getId();
  }

}
