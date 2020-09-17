package com.xlsparser.services;

import com.xlsparser.models.User;
import com.xlsparser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public boolean addUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepository.save(user);
            return true;
        } else return new BCryptPasswordEncoder().matches(password, optionalUser.get().getPassword());
    }

    public User findUserByName(String name) {
        Optional<User> optionalUser = userRepository.findByUsername(name);
        return optionalUser.orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUserByName(s);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(s);
            builder.password(user.getPassword());
            builder.roles("user");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

}
