package org.inbank.petko.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Error information to be responded for User
 * This class is immutable
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ErrorDto {
    private String infoMsg;
    private final String errorMsg;
}
