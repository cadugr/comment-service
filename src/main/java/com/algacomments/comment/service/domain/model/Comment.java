package com.algacomments.comment.service.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    private UUID id;
    private String text;
    private String author;
    @CreationTimestamp
    private OffsetDateTime createdAt;

}
