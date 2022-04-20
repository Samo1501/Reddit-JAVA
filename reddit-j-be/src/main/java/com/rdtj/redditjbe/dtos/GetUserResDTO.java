package com.rdtj.redditjbe.dtos;

import com.rdtj.redditjbe.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetUserResDTO {

    private Long id;
    private String username;
    private String date_created;

    public GetUserResDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        date_created = user.getDateRegistered().toString();
    }
}
