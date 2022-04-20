package com.rdtj.redditjbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CreatePostReqDTO {

    private String title;
    private String subreddit;
    private String post_type;
    private String posted_url;
    private String description;
}
