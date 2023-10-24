package org.inbank.petko.service;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.inbank.petko.TestFactory;
import org.inbank.petko.dto.CreditOrder;
import org.inbank.petko.dto.DecisionDto;
import org.inbank.petko.entity.UserEntity;
import org.inbank.petko.repository.UserDebtRepository;
import org.inbank.petko.repository.UserRepository;
import org.inbank.petko.service.impl.CreditDecisionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test of the main Credit Decision logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@ExtendWith(MockitoExtension.class)
class CreditDecisionServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserDebtRepository debtRepository;
    @Mock
    HttpServletRequest request;
    private CreditOrder creditOrder;
    @InjectMocks
    CreditDecisionServiceImpl decisionService;

    @BeforeEach
    void before() throws Exception {
        FieldUtils.writeField(decisionService, "minSum", 2000.0, true);
        FieldUtils.writeField(decisionService, "maxSum",10000.0, true);
        FieldUtils.writeField(decisionService, "minTerm", 12, true);
        FieldUtils.writeField(decisionService, "maxTerm", 60, true);
        FieldUtils.writeField(decisionService, "minCreditScore", 1.0, true);
    }

    @Test
    void testCreditDecisionPositive() {
        Long uuid = LocalTime.now().toNanoOfDay();
        UserEntity entity = TestFactory.createUser(49002010976L);
        System.out.printf("Start %s%n", uuid);
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));
        CreditOrder creditOrder = TestFactory.populateCreditOrder(49002010976L, 7000, 37, entity);
        DecisionDto decisionDto = decisionService.performCreditDecision(creditOrder);
        assertEquals(DecisionDto.DecisionStatus.POSITIVE, decisionDto.getDecisionStatus());
        System.out.printf("Stop execution %s%n", uuid);
    }

    @Test
    void testCreditDecisionNegative() {
        Long uuid = LocalTime.now().toNanoOfDay();
        UserEntity entity = TestFactory.createUser(49002010976L);
        System.out.printf("Start %s%n", uuid);
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));
        CreditOrder creditOrder = TestFactory.populateCreditOrder(49002010976L, 9000.9, 12, entity);
        DecisionDto decisionDto = decisionService.performCreditDecision(creditOrder);
        assertEquals(DecisionDto.DecisionStatus.NEGATIVE, decisionDto.getDecisionStatus());
        System.out.printf("Stop execution %s%n", uuid);
    }

    @Test
    void testCreditDecisionProposeTerm() {
        Long uuid = LocalTime.now().toNanoOfDay();
        UserEntity entity = TestFactory.createUser(49002010976L);
        System.out.printf("Start %s%n", uuid);
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));
        CreditOrder creditOrder = TestFactory.populateCreditOrder(49002010976L, 2000, 12, entity);
        DecisionDto decisionDto = decisionService.performCreditDecision(creditOrder);
        assertEquals(DecisionDto.DecisionStatus.PROPOSE_TERM, decisionDto.getDecisionStatus());
        System.out.printf("Stop execution %s%n", uuid);
    }

    @Test
    void testCreditDecisionActiveLoan() {
        Long uuid = LocalTime.now().toNanoOfDay();
        UserEntity entity = TestFactory.createUser(49002010965L);
        System.out.printf("Start %s%n", uuid);
        when(debtRepository.findByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(any(), any(), any()))
                .thenReturn(List.of(TestFactory.createDebt(entity.getId())));
        CreditOrder creditOrder = TestFactory.populateCreditOrder(49002010965L, 10000, 12, entity);
        DecisionDto decisionDto = decisionService.performCreditDecision(creditOrder);
        assertEquals(DecisionDto.DecisionStatus.ACTIVE_LOAN, decisionDto.getDecisionStatus());
        System.out.printf("Stop execution %s%n", uuid);
    }

}
