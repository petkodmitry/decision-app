package org.inbank.petko.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.inbank.petko.entity.SegmentEntity;
import org.inbank.petko.entity.UserDebtEntity;
import org.inbank.petko.validation.MaxMin;
import org.inbank.petko.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Credit decision attributes holder
 * This class is immutable
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Getter
public class CreditOrderDto {

    public CreditOrderDto(@NotNull Double sum, @NotNull Integer term, UserEntity user) {
        this.sum = sum;
        this.term = term;
        this.user = cloneUserEntity(user);
    }

    @Valid
    @MaxMin(min = "credit.sum.min", max = "credit.sum.max")
    @NotNull
    private final Double sum;

    @Valid
    @MaxMin(min = "credit.term.min", max = "credit.term.max")
    @NotNull
    private final Integer term;

    private final UserEntity user;

    public UserEntity getUser() {
        return cloneUserEntity(user);
    }

    private UserEntity cloneUserEntity(UserEntity originalEntity) {
        if (originalEntity == null) {
            return null;
        }
        return new UserEntity(originalEntity.getId(), originalEntity.getName(), originalEntity.getSurname(),
                cloneSegmentEntity(originalEntity.getSegment()), originalEntity.getSegmentId(),
                cloneUserDebtEntities(originalEntity.getUserDebts()));
    }
    private SegmentEntity cloneSegmentEntity(SegmentEntity originalEntity) {
        if (originalEntity == null) {
            return null;
        }
        return new SegmentEntity(originalEntity.getId(), originalEntity.getCreditModifier(), null);
    }

    private List<UserDebtEntity> cloneUserDebtEntities(List<UserDebtEntity> originalEntities) {
        if (originalEntities == null || originalEntities.isEmpty()) {
            return null;
        }
        List<UserDebtEntity> newSet = new ArrayList<>();
        originalEntities.forEach(originalEntity -> newSet
                .add(new UserDebtEntity(originalEntity.getId(), null, originalEntity.getStartDate(),
                        originalEntity.getEndDate(), originalEntity.getAmount())
                ));
        return newSet;
    }
}
