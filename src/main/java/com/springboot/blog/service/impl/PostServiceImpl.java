package com.springboot.blog.service.impl;

import com.springboot.blog.entity.post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        post Post = mapToEntity(postDto);
        post newPost= postRepository.save(Post); // return entity which is saved in new variable type post

        //convert entity to DTO
        PostDto postResponse =mapToDto(newPost);

        // return postResponse now
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts()
    {
        List<post>posts=postRepository.findAll();
        return posts.stream().map(Post -> mapToDto(Post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByID(long id)
    {
        post Post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
        return mapToDto(Post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id)
    {
        //get post by id from the database
        post Post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));

        Post.setTitle(postDto.getTitle());
        Post.setDescription(postDto.getDescription());
        Post.setContent(postDto.getContent());

        post updatedPost= postRepository.save(Post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id)
    {
        //get post by id from the database
        post Post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
        postRepository.delete(Post);
    }

    //convert entity to dto
    private PostDto mapToDto(post Post)
    {
        PostDto postDto= new PostDto();
        postDto.setId(Post.getId());
        postDto.setTitle(Post.getTitle());
        postDto.setDescription(Post.getDescription());
        postDto.setContent(Post.getContent());
        return postDto;
    }

    //convert DTO to entity

    private post mapToEntity(PostDto postDto)
    {
        post Post=new post();
        Post.setTitle(postDto.getTitle());
        Post.setDescription(postDto.getDescription());
        Post.setContent(postDto.getContent());
        return Post;
    }
}
