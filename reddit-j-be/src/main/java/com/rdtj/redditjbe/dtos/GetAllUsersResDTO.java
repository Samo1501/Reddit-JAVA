package com.rdtj.redditjbe.dtos;

import com.rdtj.redditjbe.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetAllUsersResDTO {

    private Long id;
    private String username;
    private String date_created;
    private List<String> comments = new ArrayList<>();
    private List<String> posts = new ArrayList<>();


    public GetAllUsersResDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        date_created = user.getDateRegistered().toString();
    }
}
