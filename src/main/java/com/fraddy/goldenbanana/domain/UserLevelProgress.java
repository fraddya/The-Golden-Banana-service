package com.fraddy.goldenbanana.domain;

import com.fraddy.goldenbanana.domain.base.CreateModifyAwareBaseEntity;
import com.fraddy.goldenbanana.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table
public class UserLevelProgress extends CreateModifyAwareBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    private BigInteger marks;
    private BigInteger timeInSeconds;
    private Status status;
}
