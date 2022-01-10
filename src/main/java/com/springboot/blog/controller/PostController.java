package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")

public class PostController
{
    private PostService postService;// here we're injecting interface not class, so we're making a loose coupling here
    // @Autowired omit this as it has only one constructor
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    //Create blog post RestApi
    @PostMapping
    public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
}