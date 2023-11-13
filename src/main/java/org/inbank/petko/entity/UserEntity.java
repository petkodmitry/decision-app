package org.inbank.petko.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

/**
 * USER table Entity, persisted in DB, for maintaining by Hibernate
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Setter @Getter
@Entity
@Table(name = "[USER]")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @JsonIgnore
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="segment_id", nullable=false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JsonIgnore
    private SegmentEntity segment;

    @Column(name = "segment_id", insertable = false, updatable = false)
    @JsonIgnore
    private Integer segmentId;

    @OneToMany(mappedBy= "user", orphanRemoval = true)
    @JsonIgnore
    private Set<UserDebtEntity> userDebts;

}
