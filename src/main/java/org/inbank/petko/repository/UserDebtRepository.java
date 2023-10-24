package org.inbank.petko.repository;

import org.inbank.petko.entity.UserDebtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Jpa Repository class for {@link UserDebtEntity}
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Repository
public interface UserDebtRepository extends JpaRepository<UserDebtEntity, Long> {

    /**
     * finds all Active User's loans.
     * @param userId id of the User to check
     * @param loanStartDate  loan Start Date
     * @param loanEndDate    loan End Date
     * @return List of all Active User's loans.
     */
    List<UserDebtEntity> findByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long userId, LocalDate loanStartDate, LocalDate loanEndDate);
}
