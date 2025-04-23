package com.algacomments.comment.service.api.controller;

import com.algacomments.comment.service.api.model.CommentInput;
import com.algacomments.comment.service.api.model.CommentOutput;
import com.algacomments.comment.service.domain.model.Comment;
import com.algacomments.comment.service.domain.repository.CommentRepository;
import com.algacomments.comment.service.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping
    public CommentOutput createComment(@RequestBody CommentInput commentInput) {
        log.info("Receiving new comment {}.", commentInput.toString());
        Comment comment = commentService.save(commentInput);
        if (comment != null) {
            return CommentOutput.builder()
                    .id(comment.getId().toString())
                    .author(comment.getAuthor())
                    .text(comment.getText())
                    .createdAt(comment.getCreatedAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping
    public Page<CommentOutput> searchComments(@PageableDefault Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(this::convertToModel);
    }

    @GetMapping("{commentId}")
    public CommentOutput getCommentById(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(comment);
    }

    private CommentOutput convertToModel(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId().toString())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }


}
