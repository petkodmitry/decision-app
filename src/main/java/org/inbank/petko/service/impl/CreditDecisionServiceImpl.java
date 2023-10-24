package org.inbank.petko.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inbank.petko.dto.CreditOrder;
import org.inbank.petko.dto.DecisionDto;
import org.inbank.petko.entity.UserDebtEntity;
import org.inbank.petko.entity.UserEntity;
import org.inbank.petko.repository.UserDebtRepository;
import org.inbank.petko.repository.UserRepository;
import org.inbank.petko.service.CreditDecisionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Service methods implementations for Credit Decision logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CreditDecisionServiceImpl implements CreditDecisionService {

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final UserDebtRepository userDebtRepository;
    @Value("${credit.sum.min}")
    private Double minSum;
    @Value("${credit.sum.max}")
    private Double maxSum;
    @Value("${credit.term.min}")
    private Integer minTerm;
    @Value("${credit.term.max}")
    private Integer maxTerm;
    @Value("${credit.score.min}")
    private Double minCreditScore;

    @Override
    public DecisionDto performCreditDecision(CreditOrder creditOrder) {
        DecisionDto response;

        if (!getUserActiveDebts(creditOrder.getUser().getId()).isEmpty()) {
            response = populateDecisionDto(DecisionDto.DecisionStatus.ACTIVE_LOAN, creditOrder.getSum(), creditOrder.getTerm());
            log.info("User with ID {} already has active credits.", creditOrder.getUser().getId());
        } else {
            int modifier = getUserCreditModifier(creditOrder);
            double calculatedSum = calculateSum(modifier, minCreditScore, creditOrder.getTerm(), maxSum);
            if (calculatedSum >= minSum) {
                response = populateDecisionDto(DecisionDto.DecisionStatus.POSITIVE, calculatedSum, creditOrder.getTerm());
                log.info("User with ID {} is lucky to get a credit!", creditOrder.getUser().getId());
            } else {
                double calculatedTerm = calculateTerm(modifier, minCreditScore, creditOrder.getSum(), minTerm);
                if (calculatedTerm <= maxTerm) {
                    response = populateDecisionDto(DecisionDto.DecisionStatus.PROPOSE_TERM, creditOrder.getSum(), calculatedTerm);
                    log.info("User with ID {} may get a credit for a suggested period.", creditOrder.getUser().getId());
                } else {
                    response = populateDecisionDto(DecisionDto.DecisionStatus.NEGATIVE, creditOrder.getSum(), creditOrder.getTerm());
                    log.info("User with ID {} is a looser.", creditOrder.getUser().getId());
                }
            }
        }
        return response;
    }

    private DecisionDto populateDecisionDto(DecisionDto.DecisionStatus status, double sum, double term) {
        return new DecisionDto(status, sum, term, Optional.ofNullable(request.getLocale()).or(() -> Optional.of(Locale.getDefault())).get());
    }

    private List<UserDebtEntity> getUserActiveDebts(long userId) {
        return userDebtRepository.findByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                userId, LocalDate.now(), LocalDate.now());
    }

    private int getUserCreditModifier(CreditOrder creditOrder) {
        UserEntity user = userRepository.findById(creditOrder.getUser().getId())
                .orElseThrow(() -> {
                    log.error("Wrong User ID {}", creditOrder.getUser().getId());
                    return new EntityNotFoundException("No such User");
                });
        return user.getSegment().getCreditModifier();
    }

    private double calculateSum(double modifier, double minCreditScore, int term, double maxAllowedSum) {
        double scale = Math.pow(10, 2);
        double calculatedSum = Math.floor((modifier * term / minCreditScore) * scale) / scale;
        calculatedSum = Math.min(calculatedSum, maxAllowedSum);
        return calculatedSum;
    }

    private double calculateTerm(double modifier, double minCreditScore, double sum, double minAllowedTerm) {
        double calculatedTerm = Math.ceil(minCreditScore * sum / modifier);
        calculatedTerm = Math.max(calculatedTerm, minAllowedTerm);
        return calculatedTerm;
    }
}
