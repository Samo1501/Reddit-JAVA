package com.rdtj.redditjbe.services;

import com.rdtj.redditjbe.domain.Subreddit;
import com.rdtj.redditjbe.domain.User;
import com.rdtj.redditjbe.dtos.SubredditReqDTO;
import com.rdtj.redditjbe.dtos.SubredditResDTO;
import com.rdtj.redditjbe.exception.domain.InputWrongFormatException;
import com.rdtj.redditjbe.exception.domain.ObjectAlreadyExistsException;
import com.rdtj.redditjbe.exception.domain.RequiredDataIncompleteException;
import com.rdtj.redditjbe.exception.domain.ObjectNotFoundException;
import com.rdtj.redditjbe.repositories.SubredditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.rdtj.redditjbe.constants.SubredditImplConstants.*;

@Service
@RequiredArgsConstructor
public class SubredditServiceImpl implements SubredditService{

    private final SubredditRepository subredditRepository;
    private final UserService userService;

    @Override
    public SubredditResDTO createSubreddit(SubredditReqDTO subredditReqDTO, String token) throws ObjectNotFoundException, RequiredDataIncompleteException, InputWrongFormatException, ObjectAlreadyExistsException {
        validateNewSubredditDTO(subredditReqDTO);

        if (subredditRepository.findSubredditByName(subredditReqDTO.getName()).isPresent()){
            throw new ObjectAlreadyExistsException(ALREADY_EXISTS);
        }
        Subreddit subreddit = new Subreddit(subredditReqDTO);
        User user = userService.getUserFromToken(token);
        subreddit.setUser(user);
        subreddit.subscribeUser(user);
        subredditRepository.save(subreddit);

        user.addAuthoredSubreddit(subreddit);
        user.addSubscribedSubreddit(subreddit);
        userService.saveUser(user);


        return new SubredditResDTO(subreddit);
    }

    @Override
    public List<SubredditResDTO> getAllSubredditsDto() {
        return subredditRepository.findAll().stream()
                .map(SubredditResDTO::new).collect(Collectors.toList());
    }

    @Override
    public Subreddit getSubredditByName(String subredditName) throws ObjectNotFoundException {
        Optional<Subreddit> optionalSubreddit = subredditRepository.findSubredditByName(subredditName);

        if (optionalSubreddit.isPresent()){
            return optionalSubreddit.get();
        }
        throw new ObjectNotFoundException(NOT_FOUND);
    }

    private void validateNewSubredditDTO(SubredditReqDTO subredditReqDTO) throws RequiredDataIncompleteException, InputWrongFormatException {
        if (subredditReqDTO.getName() == null || subredditReqDTO.getTitle() == null){
            throw new RequiredDataIncompleteException(DATA_INCOMPLETE);
        }
        validateName(subredditReqDTO.getName());
        validateTitle(subredditReqDTO.getTitle());
        validateDescription(subredditReqDTO.getDescription());
    }

    private void validateName(String name) throws InputWrongFormatException {
        Pattern patternPw = Pattern.compile("(?!.*[\\'\\.-\\_]{2,})(?!.*[\\ ]{2,})^[a-zA-Z0-9\\'\\.-\\_\\ ]{3,25}$");
        if (!patternPw.matcher(name).matches()) {
            throw new InputWrongFormatException(INVALID_FORMAT_NAME);
        }
    }

    private void validateTitle(String title) throws InputWrongFormatException {
        Pattern patternPw = Pattern.compile("(?!.*[\'\\.-\\_]{2,})(?!.*[\\ ]{2,})^[a-zA-Z0-9\\'\\.-\\_\\ ]{3,40}$");
        if (!patternPw.matcher(title).matches()) {
            throw new InputWrongFormatException(INVALID_FORMAT_TITLE);
        }
    }

    private void validateDescription(String description) throws InputWrongFormatException {
        if (description.length() > 2000) {
            throw new InputWrongFormatException(INVALID_FORMAT_DESCRIPTION);
        }
    }
}
