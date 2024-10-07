package com.fraddy.goldenbanana.domain;


import com.fraddy.goldenbanana.domain.base.CreateModifyAwareBaseEntity;
import com.fraddy.goldenbanana.enums.GenderType;
import com.fraddy.goldenbanana.enums.Status;
import com.fraddy.goldenbanana.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Status status;
}
