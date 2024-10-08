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
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class User extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String firstName;
    private String lastName;
    private String contactNo;
    private LocalDate dateJoin;
    private GenderType genderType;
    private String image;
    private String email;
    private LocalDateTime userLogging;
    private String passWord;
    private UserType role;
    private Status status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserLevelProgress> progress;
}
