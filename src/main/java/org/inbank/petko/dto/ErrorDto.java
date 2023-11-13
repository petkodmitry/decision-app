package org.inbank.petko.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Error information to be responded for User
 * This class is immutable
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorDto {
    private final String errorMsg;
}
