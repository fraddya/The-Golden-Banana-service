package com.fraddy.goldenbanana.repository;


import com.fraddy.goldenbanana.domain.UserLevelProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface UserLevelProgressRepository extends JpaRepository<UserLevelProgress, Long>, QuerydslPredicateExecutor<UserLevelProgress> {
}
