package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //Get all Post RestApi
    @GetMapping
    public List<PostDto> getAllPost()
    {
        return postService.getAllPosts();
    }
}
