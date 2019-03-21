package com.osdb.test.service.impl;

import com.osdb.test.entity.jpa.User;
import com.osdb.test.exception.NotFoundException;
import com.osdb.test.repository.UserRepository;
import com.osdb.test.util.EmailUtil;
import com.osdb.test.util.RandomPasswordGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserDetailsService {

    private final static String SUBJECT = "New Password";

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    EmailUtil emailUtil;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(s)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void resetPassword(String email) {
        User user = (User) loadUserByUsername(email);

        String newPassword = RandomPasswordGenerator.generatePassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        emailUtil.send(email, SUBJECT, newPassword);
    }
}
