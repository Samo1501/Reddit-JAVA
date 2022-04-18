package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.Post;
import com.rdtj.redditjbe.dtos.CreatePostReqDTO;
import com.rdtj.redditjbe.dtos.PostResDTO;
import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.exception.domain.ObjectNotFoundException;
import com.rdtj.redditjbe.exception.domain.RequiredDataIncompleteException;

import java.util.List;

public interface PostService {

    PostResDTO createPost(CreatePostReqDTO createPostReqDTO, String token) throws ObjectNotFoundException, RequiredDataIncompleteException, InputWrongFormatException;
    List<Post> getAllPosts();
    List<PostResDTO> getPostsDtoList(List<Post> posts);
    Post getPostById(Long id) throws ObjectNotFoundException;

    List<Post> getAllPostsBySubreddit(String subredditName);


}
