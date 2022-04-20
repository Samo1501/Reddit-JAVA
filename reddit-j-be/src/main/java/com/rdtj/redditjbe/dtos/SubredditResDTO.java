package com.rdtj.redditjbe.dtos;

import com.rdtj.redditjbe.domain.Subreddit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SubredditResDTO {

    private String name;
    private String title;
    private Date date_created;
    private String description;
    private String author;
    private int userCount;

    public SubredditResDTO(Subreddit subreddit) {
        name = subreddit.getName();
        title = subreddit.getTitle();
        date_created = subreddit.getDate_created();
        description = subreddit.getDescription();
        author = subreddit.getUser().getUsername();
        userCount = subreddit.getUserCount().size();
    }
}
