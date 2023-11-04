package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.brienze.shopping.list.api.utils.JwtUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_authorization", schema = "shopping_list")
public class UserAuthorization {

    public UserAuthorization() {
    }

    public UserAuthorization(UUID userCredentialId, String username, SecretKey secretKey) {
        this.userCredentialId = userCredentialId;
        this.issuedAt = new Date(System.currentTimeMillis());
        this.expiresAt = new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis());
        this.token = JwtUtils.generate(username, secretKey, this.issuedAt, this.expiresAt);
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonIgnore
    private UUID id;

    @Column(name = "user_credential_id")
    @JsonIgnore
    private UUID userCredentialId;

    @Column(name = "token")
    @JsonProperty("token")
    private String token;

    @Column(name = "issued_at")
    @JsonProperty("issued_at")
    private Date issuedAt;

    @Column(name = "expires_at")
    @JsonProperty("expires_at")
    private Date expiresAt;

}
