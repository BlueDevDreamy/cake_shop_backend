package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authority")
public class Authority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

//    @GeneratedValue(strategy = GenerationType.UUID)
//    @UuidGenerator
//    @JdbcTypeCode(SqlTypes.CHAR)
//    @Column(columnDefinition = "char(36)", nullable = false)
//    private UUID uuid;
}
