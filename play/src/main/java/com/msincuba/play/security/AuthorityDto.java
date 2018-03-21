package com.msincuba.play.security;

import com.msincuba.play.security.domain.AuthorityName;
import com.msincuba.play.security.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {

    private Long id;
    private AuthorityName name;
    private List<User> users;
}
