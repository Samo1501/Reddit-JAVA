package com.rdtj.redditjbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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
    private String password;

    @CreationTimestamp
    private Timestamp dateRegistered;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String[] authorities;

    private boolean isEnabled;
    private boolean isNotLocked;

    public User(String username, String email, String password, Role role, boolean isEnabled, boolean isNotLocked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isEnabled = isEnabled;
        this.isNotLocked = isNotLocked;
    }

}
