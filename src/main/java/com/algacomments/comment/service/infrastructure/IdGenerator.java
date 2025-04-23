package com.algacomments.comment.service.infrastructure;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator =
            Generators.timeBasedEpochRandomGenerator();

    public static UUID generateTimebasedUUID() {
        return timeBasedEpochRandomGenerator.generate();
    }
}
