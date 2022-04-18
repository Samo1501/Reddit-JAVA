package com.rdtj.redditjbe.resource;

import com.rdtj.redditjbe.domain.Post;
import com.rdtj.redditjbe.dtos.CreatePostReqDTO;
import com.rdtj.redditjbe.dtos.PostResDTO;
import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.exception.domain.ObjectNotFoundException;
import com.rdtj.redditjbe.exception.domain.RequiredDataIncompleteException;
import com.rdtj.redditjbe.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping({"/", "/api/v1"})
public class PostResource {

    private final PostService postService;

    @GetMapping("/subreddits/posts/feed")
    public ResponseEntity<List<PostResDTO>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(postService.getPostsDtoList(posts), HttpStatus.OK);
    }

    @GetMapping("/subreddits/posts/feed/r/{subredditName}")
    public ResponseEntity<List<PostResDTO>> getAllPostsBySubreddit(@PathVariable String subredditName){
        List<Post> posts = postService.getAllPostsBySubreddit(subredditName);
        return new ResponseEntity<>(postService.getPostsDtoList(posts), HttpStatus.OK);
    }

    @PostMapping("/subreddits/posts/create")
    public ResponseEntity<PostResDTO> createPost(@RequestBody CreatePostReqDTO createPostReqDTO,
                                                 @RequestHeader("Authorization") String token) throws ObjectNotFoundException, RequiredDataIncompleteException, InputWrongFormatException {
        PostResDTO postResDTO = postService.createPost(createPostReqDTO, token);
        return new ResponseEntity<>(postResDTO, HttpStatus.OK);

    }

    @GetMapping("/subreddits/posts/{id}")
    public ResponseEntity<PostResDTO> getPostById(@PathVariable Long id) throws ObjectNotFoundException {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(new PostResDTO(post), HttpStatus.OK);
    }

}
