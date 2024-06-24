package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Entity(name = "tokens_refresh_jwt")
public class RefreshJwtToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "varchar(2048)")
    private String token;

    @Column(nullable = false, unique = true)
    private Integer tokenHashCode;

    @Column(nullable = false)
    private ZonedDateTime expiryDate;

    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)", nullable = false)
    private UUID uuid;

    public RefreshJwtToken() {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public RefreshJwtToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public RefreshJwtToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private ZonedDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return ZonedDateTime.now().plusMinutes(expiryTimeInMinutes);
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

}
