package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.constants.PostImplConstants;
import com.rdtj.redditjbe.domain.Post;
import com.rdtj.redditjbe.domain.Subreddit;
import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.dtos.CreatePostReqDTO;
import com.rdtj.redditjbe.dtos.PostResDTO;
import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.exception.domain.ObjectNotFoundException;
import com.rdtj.redditjbe.exception.domain.RequiredDataIncompleteException;
import com.rdtj.redditjbe.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rdtj.redditjbe.constants.PostImplConstants.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SubredditService subredditService;
    private final UserService userService;

    @Override
    public PostResDTO createPost(CreatePostReqDTO createPostReqDTO, String token) throws ObjectNotFoundException, RequiredDataIncompleteException, InputWrongFormatException {
        validateNewPostReqDTO(createPostReqDTO);
        Post post = new Post(createPostReqDTO.getTitle(), createPostReqDTO.getPost_type());
        post.setPosted_url(setUrlByPostType(createPostReqDTO.getPost_type(), createPostReqDTO.getPosted_url()));
        post.setDescription(setDescriptionByPostType(createPostReqDTO.getPost_type(), createPostReqDTO.getDescription()));

        Subreddit subreddit = subredditService.getSubredditByName(createPostReqDTO.getSubreddit());
        post.setSubreddit(subreddit);
        post.setUser(userService.getUserFromToken(token));
        postRepository.save(post);

        return new PostResDTO(post);
    }

    private String setUrlByPostType(String post_type, String url) {
        if (post_type.equalsIgnoreCase("text")) {
            return null;
        }
        return url;
    }

    private String setDescriptionByPostType(String post_type, String description) {
        if (post_type.equalsIgnoreCase("text")) {
            return description;
        }
        return null;
    }

    private void validateNewPostReqDTO(CreatePostReqDTO createPostReqDTO) throws RequiredDataIncompleteException, InputWrongFormatException {
        if (createPostReqDTO.getTitle() == null ||
                createPostReqDTO.getSubreddit() == null ||
                createPostReqDTO.getPost_type() == null) {
            throw new RequiredDataIncompleteException(DATA_INCOMPLETE);
        }
        if (!createPostReqDTO.getPost_type().equalsIgnoreCase("text")
                && !createPostReqDTO.getPost_type().equalsIgnoreCase("url"))
            throw new InputWrongFormatException(INVALID_FORMAT_POST_TYPE);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<PostResDTO> mapPostsToDtoList(List<Post> posts) {
        return posts.stream().map(post -> new PostResDTO(post)).collect(Collectors.toList());
    }

    @Override
    public Post getPostById(Long id) throws ObjectNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()){
            return optionalPost.get();
        }
        throw new ObjectNotFoundException(NOT_FOUND);
    }

    @Override
    public List<Post> getAllPostsBySubreddit(String subredditName) {
        return postRepository.findAllBySubreddit_Name(subredditName);
    }

    @Override
    public List<Post> getPostsDtoListByUserId(Long id) throws ObjectNotFoundException {
        User user = userService.findUserById(id);
        return postRepository.findAllByUser_Id(id);
    }
}
