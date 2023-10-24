package org.inbank.petko.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * SEGMENT table Entity, persisted in DB, for maintaining by Hibernate
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Setter @Getter
@Entity
@Table(name = "SEGMENT")
public class SegmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int creditModifier;

    @OneToMany(mappedBy= "segment")
    @JsonBackReference
    private Set<UserEntity> users;

}
