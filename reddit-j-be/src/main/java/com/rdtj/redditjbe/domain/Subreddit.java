package com.rdtj.redditjbe.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
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
}
