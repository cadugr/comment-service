package com.algacomments.comment.service.api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentInput {
    private String text;
    private String author;
}
