package com.rdtj.redditjbe.models;

import com.rdtj.redditjbe.dtos.UserRegisterReqDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private boolean enabled;
    private boolean locked;

    public User(String username, String email, String password, UserRole userRole, boolean enabled, boolean locked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.enabled = enabled;
        this.locked = locked;
    }

    public User(UserRegisterReqDTO userRegisterReqDTO, UserRole userRole) {
        username = userRegisterReqDTO.getUsername();
        password = userRegisterReqDTO.getPassword();
        email = userRegisterReqDTO.getEmail();
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
