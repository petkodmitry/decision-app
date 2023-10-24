package org.inbank.petko.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Error information to be responded for User
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Setter @Getter
public class ErrorDto {
    private String errorMsg;
}
