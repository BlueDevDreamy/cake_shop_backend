package com.andjela.diplomski.common;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity implements Serializable {
//    @Version
//    @Builder.Default
//    @ToString.Exclude
//    protected Long version = 1L;

//    @ToString.Exclude
//    @CreatedDate
    //   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @Column(name = "created_at", updatable = false)

    @CreatedDate
    @Column(updatable = false, nullable = false, name = "created_at")
    protected LocalDateTime createdAt;

    @ToString.Exclude
    @LastModifiedDate
    //   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "updated_at", updatable = true, insertable = false)
    protected LocalDateTime updatedAt;

    @ToString.Exclude
    //   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @ToString.Exclude
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", updatable = true)
    @ToString.Exclude
    private String updatedBy;

}
