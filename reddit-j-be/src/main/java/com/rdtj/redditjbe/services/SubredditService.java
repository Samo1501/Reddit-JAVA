package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.Subreddit;
import com.rdtj.redditjbe.dtos.SubredditReqDTO;
import com.rdtj.redditjbe.dtos.SubredditResDTO;
import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.exception.domain.ObjectAlreadyExistsException;
import com.rdtj.redditjbe.exception.domain.RequiredDataIncompleteException;
import com.rdtj.redditjbe.exception.domain.ObjectNotFoundException;

import java.util.List;

public interface SubredditService {
    SubredditResDTO createSubreddit(SubredditReqDTO subredditReqDTO, String token) throws ObjectNotFoundException, RequiredDataIncompleteException, InputWrongFormatException, ObjectAlreadyExistsException;
    List<SubredditResDTO> getAllSubredditsDto();
    Subreddit getSubredditByName(String subredditName) throws ObjectNotFoundException;
}
