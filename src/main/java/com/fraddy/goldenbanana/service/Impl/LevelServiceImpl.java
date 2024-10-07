package com.fraddy.goldenbanana.service.Impl;


import com.fraddy.goldenbanana.domain.Level;
import com.fraddy.goldenbanana.domain.QUser;
import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.base.ComplexValidationException;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import com.fraddy.goldenbanana.enums.Status;
import com.fraddy.goldenbanana.enums.UserType;
import com.fraddy.goldenbanana.repository.LevelRepository;
import com.fraddy.goldenbanana.repository.UserRepository;
import com.fraddy.goldenbanana.service.LevelService;
import com.fraddy.goldenbanana.service.UserService;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;


    @Transactional
    @Override
    public Level save(Level level) {
        Level persistedLevel = levelRepository.findByName(level.getName());
        if (persistedLevel != null) {
            throw new ComplexValidationException("Level registration", "this Level is already added");
        }
        level.setStatus(Status.ACTIVE);
        return levelRepository.save(level);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Level> search(Level level) {

        List<Level> levelPersisted = levelRepository.findAll();
        if (levelPersisted == null) {
            throw new ComplexValidationException("Level retrieval","Leve (%s) not found  ");
        }
        return levelPersisted;
    }

    @Transactional(readOnly = true)
    @Override
    public Level retrieve(Long id) {
        Optional<Level> levelPersisted = levelRepository.findById(id);
        if (!levelPersisted.isPresent()) {
            throw new ComplexValidationException("Level retrieval","Level (%s) not found  ");
        }
        return levelPersisted.get();
    }


    @Transactional
    @Override
    public Level update(Level level) {
        Optional<Level> levelPersisted = levelRepository.findById(level.getId());
        if (!levelPersisted.isPresent()) {
            throw new ComplexValidationException("Leve retrieval", "Cannot find any Leve ");
        } else {
            Level levelDb = levelPersisted.get();
            if (level.getName() != null) levelDb.setName(level.getName());
            if (level.getDescription() != null) levelDb.setDescription(level.getDescription());
            return levelRepository.save(levelDb);
        }
    }


    @Transactional
    @Override
    public Level delete(Long id) {
        Level level = levelRepository.getReferenceById(id);
        if (level != null) {
            level.setStatus(Status.DELETED);
            return levelRepository.save(level);
        }else {
            throw new ComplexValidationException("Level retrieval", "Cannot find any Level ");
        }
    }


}
