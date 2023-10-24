package org.inbank.petko.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.inbank.petko.validation.MaxMin;
import org.inbank.petko.entity.UserEntity;

/**
 * Main Credit decision attributes holder
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Setter @Getter
public class CreditOrder {

    @Valid
    @MaxMin(min = "credit.sum.min", max = "credit.sum.max")
    @NotNull
    private Double sum;

    @Valid
    @MaxMin(min = "credit.term.min", max = "credit.term.max")
    @NotNull
    private Integer term;

    private UserEntity user;

}
