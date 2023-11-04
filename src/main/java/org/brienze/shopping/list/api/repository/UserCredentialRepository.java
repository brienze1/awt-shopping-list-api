package org.brienze.shopping.list.api.repository;

import org.brienze.shopping.list.api.model.UserCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialRepository extends CrudRepository<UserCredential, UUID> {
    Optional<UserCredential> findByUsername(String username);
}
