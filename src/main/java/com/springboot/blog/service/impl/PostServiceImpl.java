package com.springboot.blog.service.impl;

import com.springboot.blog.entity.post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService
{
    private PostRepository postRepository;
    //omit @Autowired annotation as it has only one constructor
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto)
    {
        // convert DTO to entity
        post Post = new post();
        Post.setTitle(postDto.getTitle());
        Post.setDescription(postDto.getDescription());
        Post.setContent(postDto.getContent());

        post newPost= postRepository.save(Post); // return entity which is saved in new variable type post

        //convert entity to DTO
        PostDto postResponse =new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        // return postResponse now
        return postResponse;
    }
}
