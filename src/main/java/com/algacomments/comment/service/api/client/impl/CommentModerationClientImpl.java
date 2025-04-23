package com.algacomments.comment.service.api.client.impl;

import com.algacomments.comment.service.api.client.CommentModerationClient;
import com.algacomments.comment.service.api.client.CommentModerationClientfactory;
import com.algacomments.comment.service.api.model.ModerationInput;
import com.algacomments.comment.service.api.model.ModerationOutput;
import org.springframework.web.client.RestClient;


public class CommentModerationClientImpl implements CommentModerationClient {

    private final RestClient restClient;

    public CommentModerationClientImpl(CommentModerationClientfactory factory) {
        this.restClient = factory.commentModerationRestClient();
    }

    @Override
    public ModerationOutput moderateComment(ModerationInput moderationInput) {
        return restClient.post()
                .uri("/api/moderate")
                .body(moderationInput)
                .retrieve()
                .body(ModerationOutput.class);

    }
}
