package com.fraddy.goldenbanana.service.Impl;


import com.fraddy.goldenbanana.domain.QUser;
import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.UserLevelProgress;
import com.fraddy.goldenbanana.domain.base.ComplexValidationException;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import com.fraddy.goldenbanana.domain.criteria.UserLevelProgressCriteria;
import com.fraddy.goldenbanana.enums.Status;
import com.fraddy.goldenbanana.enums.UserType;
import com.fraddy.goldenbanana.repository.UserLevelProgressRepository;
import com.fraddy.goldenbanana.repository.UserRepository;
import com.fraddy.goldenbanana.service.UserLevelProgressService;
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
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserLevelProgressServiceImpl implements UserLevelProgressService {

    @Autowired
    private UserLevelProgressRepository userLevelProgressRepository;


    @Transactional
    @Override
    public UserLevelProgress save(UserLevelProgress userLevelProgress) {
        userLevelProgress.setStatus(Status.ACTIVE);
        return userLevelProgressRepository.save(userLevelProgress);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserLevelProgress> search(UserLevelProgressCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.by(Sort.Direction.fromOptionalString(criteria.getSortDirection()).orElse(Sort.Direction.DESC),
                        criteria.getSortProperty()));

        Page<UserLevelProgress> userLevelProgresses = null;

        /*BooleanBuilder booleanBuilder = new BooleanBuilder(QUser.user.status.ne(Status.DELETED));

        if (StringUtils.isNotBlank(criteria.getEmail())) {
            booleanBuilder.and(QUser.user.email.containsIgnoreCase(criteria.getEmail()));
        }
        if (criteria.getRole() != null ) {
            booleanBuilder.and(QUser.user.role.eq(UserType.valueOf(String.valueOf(criteria.getRole()))));
        }

        if (booleanBuilder.hasValue()){
            userLevelProgresses = userLevelProgressRepository.findAll(booleanBuilder, page);
        } else {
            userLevelProgresses = userLevelProgressRepository.findAll(page);
        }*/
        return userLevelProgresses;
    }

    @Transactional(readOnly = true)
    @Override
    public UserLevelProgress retrieve(Long id) {
        Optional<UserLevelProgress> userLevelProgress = userLevelProgressRepository.findById(id);
        if (!userLevelProgress.isPresent()) {
            throw new ComplexValidationException("user level progress retrieval","User level progress (%s) not found  ");
        }
        return userLevelProgress.get();
    }


    @Transactional
    @Override
    public UserLevelProgress update(UserLevelProgress userLevelProgress) {
        Optional<UserLevelProgress> userLevelProgressPersisted = userLevelProgressRepository.findById(userLevelProgress.getId());
        if (!userLevelProgressPersisted.isPresent()) {
            throw new ComplexValidationException("user level progress retrieval","User level progress (%s) not found  ");
        } else {
            UserLevelProgress userLevelProgressDb = userLevelProgressPersisted.get();
            updateFields(userLevelProgress, userLevelProgressDb);
            return userLevelProgressRepository.save(userLevelProgressDb);
        }
    }

    private void updateFields(UserLevelProgress userLevelProgress, UserLevelProgress userLevelProgressDb) {
        if (userLevelProgress.getUser() != null) userLevelProgressDb.setUser(userLevelProgress.getUser());
        if (userLevelProgress.getLevel() != null) userLevelProgressDb.setLevel(userLevelProgress.getLevel());
        if (userLevelProgress.getMarks() != null) userLevelProgressDb.setMarks(userLevelProgress.getMarks());
        if (userLevelProgress.getTimeInSeconds() != null) userLevelProgressDb.setTimeInSeconds(userLevelProgress.getTimeInSeconds());

    }

    @Transactional
    @Override
    public UserLevelProgress delete(Long id) {
        UserLevelProgress userLevelProgress = userLevelProgressRepository.getReferenceById(id);
        if (userLevelProgress != null) {
            userLevelProgress.setStatus(Status.DELETED);
            return userLevelProgressRepository.save(userLevelProgress);
        }else {
            throw new ComplexValidationException("user level progress retrieval","User level progress (%s) not found  ");
        }
    }

}
