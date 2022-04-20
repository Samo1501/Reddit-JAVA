package com.rdtj.redditjbe.dtos;

import com.rdtj.redditjbe.domain.Comment;
import com.rdtj.redditjbe.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PostResDTO {

    private Long id;
    private String title;
    private Date date_created;
    private String subreddit;
    private String author;
    private int commentCount;
    private String post_type;
    private String posted_url;
    private String description;
    private List<Comment> comments = new ArrayList<>();

    public PostResDTO(Post post) {
        id = post.getId();
        title = post.getTitle();
        date_created = post.getDate_created();
        subreddit = post.getSubreddit().getName();
        author = post.getUser().getUsername();
        commentCount = post.getComments().size();
        post_type = post.getPostType().name();
        posted_url = post.getPosted_url();
        description = post.getDescription();
        comments = post.getComments();
    }
}
