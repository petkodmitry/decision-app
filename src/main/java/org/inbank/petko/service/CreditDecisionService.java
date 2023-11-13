package org.inbank.petko.service;

import org.inbank.petko.dto.CreditOrderDto;
import org.inbank.petko.dto.DecisionDto;

/**
 * Service methods holder for Credit Decision logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
public interface CreditDecisionService {

    /**
     * Main Credit Decision logic
     * @param creditOrder all input parameters for Decision engine
     * @return input parameters with made Decision
     */
    DecisionDto performCreditDecision(CreditOrderDto creditOrder);
}
