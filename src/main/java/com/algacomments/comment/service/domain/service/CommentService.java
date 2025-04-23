package com.algacomments.comment.service.domain.service;

import com.algacomments.comment.service.api.client.CommentModerationClient;
import com.algacomments.comment.service.api.model.CommentInput;
import com.algacomments.comment.service.api.model.ModerationInput;
import com.algacomments.comment.service.api.model.ModerationOutput;
import com.algacomments.comment.service.domain.model.Comment;
import com.algacomments.comment.service.domain.repository.CommentRepository;
import com.algacomments.comment.service.infrastructure.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentModerationClient commentModerationClient;

    @Transactional
    public Comment save (CommentInput commentInput) {
        UUID commentId = IdGenerator.generateTimebasedUUID();
        ModerationInput moderationInput = ModerationInput.builder()
                .text(commentInput.getText())
                .commentId(commentId.toString())
                .build();
        ModerationOutput moderationOutput = commentModerationClient
                .moderateComment(moderationInput);
        if(moderationOutput.getApproved()) {
            Comment comment = Comment.builder()
                    .id(commentId)
                    .author(commentInput.getAuthor())
                    .text(commentInput.getText())
                    .build();
            log.info("Successfully created comment with id {}.", comment.getId());
            return commentRepository.save(comment);
        } else {
            log.error("Failed to save comment: {}.", moderationOutput.getReason());
            return null;
        }

    }


}
