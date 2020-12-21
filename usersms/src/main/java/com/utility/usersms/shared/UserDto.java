package com.utility.usersms.shared;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto implements Serializable {


    private static final long serialVersionUID = -7486543683246871717L;
    private String firstName;


    private String lastName;


    private String email;


    private String password;

    private String userId;

    private String encryptedPassword;
}
