package com.utility.usersms.controllers;


import com.utility.usersms.data.UserEntity;
import com.utility.usersms.models.UserDetails;
import com.utility.usersms.models.UserResponseModel;
import com.utility.usersms.service.UserService;
import com.utility.usersms.shared.UserDto;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    UserService userService;
    @Autowired
    private Environment environment;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String status() {
        return "token is " + environment.getProperty("token.secret");
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserDetails userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(userDetails, UserDto.class);

        UserEntity userEntity = userService.createUser(userDto);
        UserResponseModel responseModel = mapper.map(userEntity, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }


}
