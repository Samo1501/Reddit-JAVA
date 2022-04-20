package com.rdtj.redditjbe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreationTimestamp
    private Timestamp dateRegistered;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String[] authorities;

    private boolean isEnabled;
    private boolean isNotLocked;


    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Subreddit> subredditsAuthored = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "users_subbed_subreddits",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subreddit_id"))
    private List<Subreddit> subredditsSubscribedTo = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    public User(String username, String email, String password, Role role, boolean isEnabled, boolean isNotLocked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        authorities = role.getAuthorities();
        this.isEnabled = isEnabled;
        this.isNotLocked = isNotLocked;
    }

    public void addAuthoredSubreddit(Subreddit subreddit){
        subredditsAuthored.add(subreddit);
    }

    public void removeAuthoredSubreddit(Subreddit subreddit){
        subredditsAuthored.remove(subreddit);
    }

    public void addSubscribedSubreddit(Subreddit subreddit){
        subredditsSubscribedTo.add(subreddit);
    }

    public void removeSubscribedSubreddit(Subreddit subreddit){
        subredditsSubscribedTo.remove(subreddit);
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public void removePost(Post post){
        posts.remove(post);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
    }
}
