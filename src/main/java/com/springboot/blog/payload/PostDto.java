package com.springboot.blog.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto
{
    private Long id;
    private String title;
    private String description;
    private String Content;
    private Set<CommentDto> comments;
}
