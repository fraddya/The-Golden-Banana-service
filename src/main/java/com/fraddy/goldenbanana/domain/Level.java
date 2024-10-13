package com.fraddy.goldenbanana.domain;


import com.fraddy.goldenbanana.domain.base.CreateModifyAwareBaseEntity;
import com.fraddy.goldenbanana.enums.LevelDifficultyType;
import com.fraddy.goldenbanana.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Level extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    private LevelDifficultyType difficulty;
    private Status status;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Set<UserLevelProgress> progress;
}
