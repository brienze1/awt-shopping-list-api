package org.brienze.shopping.list.api.repository;

import org.brienze.shopping.list.api.model.UserAuthorization;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserAuthorizationRepository extends CrudRepository<UserAuthorization, UUID> {
}
