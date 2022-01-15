package com.springboot.blog.service.impl;

import com.springboot.blog.entity.post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir)
    {
        //Create Pageable instance
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
        Page<post>posts=postRepository.findAll(pageable);

        //get content for page object
        List<post>listOfPosts=posts.getContent();

        //return listOfPosts.stream().map(Post -> mapToDto(Post)).collect(Collectors.toList());
        //get everything in a list of postdto class
        List<PostDto>content= listOfPosts.stream().map(Post -> mapToDto(Post)).collect(Collectors.toList());
        //create instance of PostResponse dto
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
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
