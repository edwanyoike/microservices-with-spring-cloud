package com.utility.users.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

    @NotNull(message = "firstname cannot be null")
    @Size(min = 2, message = "not less than 2 chars")
    private String firstName;

    @NotNull(message = "secondname cannot be null")
    @Size(min = 2, message = "not less than 2 chars")

    private String lastName;

    @NotNull(message = "email cannot be null")
    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 6,max = 16, message = "not less than 6 chars")

    private String password;



}
