package org.brienze.shopping.list.api.repository;

import org.brienze.shopping.list.api.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
