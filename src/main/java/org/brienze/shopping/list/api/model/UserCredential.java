package org.brienze.shopping.list.api.model;

import io.jsonwebtoken.security.Keys;
import jakarta.persistence.*;
import org.brienze.shopping.list.api.exception.InvalidToken;
import org.brienze.shopping.list.api.utils.JwtUtils;
import org.brienze.shopping.list.api.utils.PasswordUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_credential", schema = "shopping_list")
public class UserCredential {

    public UserCredential() {
    }

    public UserCredential(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.secret = UUID.randomUUID();
        this.password = PasswordUtils.hash(username, password, this.salt());
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "secret")
    private UUID secret;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_credential_id")
    private Set<UserAuthorization> userAuthorization;

    public SecretKey signature() {
        return Keys.hmacShaKeyFor(secret.toString().getBytes(StandardCharsets.UTF_8));
    }

    public byte[] salt() {
        return secret.toString().substring(0, 16).getBytes(StandardCharsets.UTF_8);
    }

    public boolean allows(String hashedPassword) {
        return hashedPassword.equals(password);
    }

    public void validate(String token) {
        if (Optional.of(this.userAuthorization)
                    .orElse(new HashSet<>())
                    .stream()
                    .map(UserAuthorization::getToken)
                    .noneMatch(savedToken -> savedToken.equals(token)) || !JwtUtils.isValid(token)) {
            throw new InvalidToken();
        }
    }

    public UserAuthorization authorization() {
        return new UserAuthorization(this.id, this.username, signature());
    }
}
