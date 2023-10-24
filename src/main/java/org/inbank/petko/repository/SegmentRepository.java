package org.inbank.petko.repository;

import org.inbank.petko.entity.SegmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa Repository class for {@link SegmentEntity}
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Repository
public interface SegmentRepository extends JpaRepository<SegmentEntity, Long> {
}
