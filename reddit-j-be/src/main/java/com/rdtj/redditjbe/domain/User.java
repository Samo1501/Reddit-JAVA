package com.rdtj.redditjbe.domain;

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
@Getter @Setter
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
    private List<Subreddit> subredditsAuthored = new ArrayList<>();

    public User(String username, String email, String password, Role role, boolean isEnabled, boolean isNotLocked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        authorities = role.getAuthorities();
        this.isEnabled = isEnabled;
        this.isNotLocked = isNotLocked;
    }
}
