package com.utn2019.avanzada2.tp9.service;

import static com.utn2019.avanzada2.tp9.util.ValidationUtil.validateObject;
import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;

import com.utn2019.avanzada2.tp9.domain.User;
import com.utn2019.avanzada2.tp9.exception.ObjectAlreadyExistsException;
import com.utn2019.avanzada2.tp9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(User user) {
        validateObject(user);
        ofNullable(userRepository.existsByEmail(user.getEmail()))
                .filter(FALSE::equals)
                .orElseThrow(() -> new ObjectAlreadyExistsException(user.getClass().getSimpleName(), "email", user.getEmail()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean existsByEmail(String email) {
        return ofNullable(email)
                .map(userRepository::existsByEmail)
                .orElse(false);
    }
}
