package org.inbank.petko;

import org.inbank.petko.dto.CreditOrder;
import org.inbank.petko.entity.SegmentEntity;
import org.inbank.petko.entity.UserDebtEntity;
import org.inbank.petko.entity.UserEntity;

public class TestFactory {

  public static UserEntity createUser(long id) {
    UserEntity entity = new UserEntity();
    entity.setId(id);
    SegmentEntity segmentEntity = new SegmentEntity();
    segmentEntity.setCreditModifier(100);
    entity.setSegment(segmentEntity);

    return entity;
  }

  public static UserDebtEntity createDebt(Long userId) {
    UserDebtEntity debtEntity = new UserDebtEntity();
    debtEntity.setAmount(100.00);
    debtEntity.setId(userId);

    return debtEntity;
  }

  public static CreditOrder populateCreditOrder(long id, double sum, int term, UserEntity user) {
    CreditOrder creditOrder = new CreditOrder();
    creditOrder.setSum(sum);
    creditOrder.setTerm(term);
    creditOrder.setUser(user);

    return creditOrder;
  }
}
