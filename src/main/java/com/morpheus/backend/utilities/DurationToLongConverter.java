package com.morpheus.backend.utilities;

import java.time.Duration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DurationToLongConverter implements AttributeConverter<Duration, Long>{

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return duration != null ? duration.getSeconds() : null;
    }

    @Override
    public Duration convertToEntityAttribute(Long seconds) {
        return seconds != null ? Duration.ofSeconds(seconds) : null;
    }

}
