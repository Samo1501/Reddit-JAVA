package com.rdtj.redditjbe.domain;

import com.rdtj.redditjbe.dtos.SubredditReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String title;

    @CreationTimestamp
    private Date date_created;

    private String description;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "subreddit")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "subredditsSubscribedTo")
    private List<User> userCount = new ArrayList<>();

    public Subreddit(SubredditReqDTO subredditReqDTO) {
        name = subredditReqDTO.getName();
        title = subredditReqDTO.getTitle();
        description = subredditReqDTO.getDescription();
    }

    public void subscribeUser(User user){
        userCount.add(user);
    }

    public void unSubscribeUser(User user){
        userCount.remove(user);
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public void remove(Post post){
        posts.remove(post);
    }
}
