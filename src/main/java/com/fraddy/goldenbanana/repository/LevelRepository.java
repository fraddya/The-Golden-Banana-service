package com.fraddy.goldenbanana.repository;


import com.fraddy.goldenbanana.domain.Level;
import com.fraddy.goldenbanana.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface LevelRepository extends JpaRepository<Level, Long>, QuerydslPredicateExecutor<Level> {
    Level findByName(String name);
}
