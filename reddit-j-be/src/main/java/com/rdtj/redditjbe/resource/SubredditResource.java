package com.rdtj.redditjbe.resource;

import com.rdtj.redditjbe.dtos.SubredditReqDTO;
import com.rdtj.redditjbe.dtos.SubredditResDTO;
import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.exception.domain.ObjectAlreadyExistsException;
import com.rdtj.redditjbe.exception.domain.RequiredDataIncompleteException;
import com.rdtj.redditjbe.exception.domain.ObjectNotFoundException;
import com.rdtj.redditjbe.services.SubredditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping({"/", "/api/v1"})
public class SubredditResource {

    private final SubredditService subredditService;

    @PostMapping("/subreddits/create")
    public ResponseEntity<SubredditResDTO>createSubreddit(@RequestBody
    SubredditReqDTO subredditReqDTO, @RequestHeader("Authorization") String token) throws ObjectNotFoundException, RequiredDataIncompleteException, InputWrongFormatException, ObjectAlreadyExistsException {
        SubredditResDTO subredditResDTO = subredditService.createSubreddit(subredditReqDTO, token);
        return new ResponseEntity<>(subredditResDTO, HttpStatus.OK);
    }

    @GetMapping("/subreddits")
    public ResponseEntity<List<SubredditResDTO>> getAllSubreddits(){
        List<SubredditResDTO> subredditResDTOList = subredditService.getAllSubredditsDto();
        return new ResponseEntity<>(subredditResDTOList, HttpStatus.OK);
    }

}
