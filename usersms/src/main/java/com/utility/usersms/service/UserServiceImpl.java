package com.utility.usersms.service;


import com.utility.usersms.data.UserEntity;
import com.utility.usersms.repository.UsersRepository;
import com.utility.usersms.shared.UserDto;
import java.util.ArrayList;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UsersRepository usersRepository;

    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserEntity createUser(UserDto userDto) {

        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        userDto.setUserId(UUID.randomUUID().toString());

        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity  userEntity = mapper.map(userDto, UserEntity.class);
        userEntity = usersRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if(userEntity == null){
            throw  new UsernameNotFoundException(email);
        }

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException(email);
        }

        ModelMapper mapper =  new ModelMapper();
        UserDto  userDto = mapper.map(userEntity,UserDto.class);
        return userDto;

    }
}
