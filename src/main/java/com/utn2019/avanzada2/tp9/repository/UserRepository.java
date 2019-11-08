package com.utn2019.avanzada2.tp9.repository;

import com.utn2019.avanzada2.tp9.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
